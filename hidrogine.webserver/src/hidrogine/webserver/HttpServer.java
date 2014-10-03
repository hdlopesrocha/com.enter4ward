package hidrogine.webserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.DelayQueue;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The Class HttpServer.
 */
public class HttpServer {

    /** The Constant VERSION. */
    public static final String VERSION = "1.4.5";

    /** The port. */
    private int port;

    /** The session. */
    private long session = 0;

    /** The sessions. */
    private HashMap<String, Session> sessions;

    /** The cookie queue. */
    private DelayQueue<Session> cookieQueue;

    /** The resources. */
    private TreeMap<String, Controller> resources;

    /** The config. */
    private JSONObject config = null;

    /** The index. */
    private Node root;

    /** The Constant random. */
    private static final Random RANDOM = new Random();

    /** The Constant chars. */
    private static final String CHARS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    /**
     * Gets the session.
     *
     * @param sid
     *            the sid
     * @return the session
     */
    public final Session getSession(final String sid) {
        return sessions.get(sid);
    }

    /**
     * Random string.
     *
     * @param size
     *            the size
     * @return the string
     */
    private String randomString(final int size) {
        String str = "";
        int charsLen = CHARS.length();
        for (int i = 0; i < size; i++) {
            str += CHARS.charAt(RANDOM.nextInt(charsLen));
        }
        return str;
    }

    /**
     * Generate session.
     *
     * @return the session
     */
    public final Session generateSession() {
        Session cookie = null;

        long expireTime = System.currentTimeMillis() + session;

        while (cookie == null) {
            String s = randomString(32);
            while (sessions.get(s) != null) {
                s = randomString(32);
            }
            cookie = new Session(s, expireTime);

            Session replacedCookie = sessions.put(s, cookie);
            if (replacedCookie != null) {
                sessions.put(s, replacedCookie);
                cookie = null;
            }

        }

        cookieQueue.add(cookie);
        return cookie;
    }

    /**
     * Garbage cookies.
     */
    public final void garbageCookies() {
        while (true) {
            try {
                Session entry = cookieQueue.take();
                sessions.remove(entry.getId());
                System.out.println(entry.getId() + " expired!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Builds the nodes.
     *
     * @param json
     *            the json
     * @param node
     *            the node
     */
    private void buildNodes(final JSONObject json, final Node node) {

        JSONObject j = json;
        try {
            if (j.has("nav")) {
                JSONArray arr = j.getJSONArray("nav");
                for (int i = 0; i < arr.length(); ++i) {
                    j = arr.getJSONObject(i);
                    String n = j.getString("name");
                    String m = j.getString("method");
                    String l = j.has("label") ? j.getString("label") : n;

   

                    Node nn = new Node(node, n, l, m);
                    buildNodes(j, nn);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates a new http server.
     *
     * @param configpath
     *            the configpath
     */
    public HttpServer(final String configpath) {
        try {
            config = new JSONObject(new String(Files.readAllBytes(Paths
                    .get(configpath))));
            root = new Node(null, null, null, config.getString("method"));
            port = config.getInt("port");
            if (config.has("session")) {
                session = config.getLong("session");
            }

            buildNodes(config, root);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("    __    _     __                 _          ");
        System.out.println("   / /_  (_)___/ /________  ____ _(_)___  ___ ");
        System.out
                .println("  / __ \\/ / __  / ___/ __ \\/ __ `/ / __ \\/ _ \\");
        System.out.println(" / / / / / /_/ / /  / /_/ / /_/ / / / / /  __/");
        System.out
                .println("/_/ /_/_/\\__,_/_/   \\____/\\__, /_/_/ /_/\\___/ ");
        System.out.println("                         /____/  web service");
        System.out.println("Language: Java");
        System.out.println("Version: " + VERSION);
        System.out.println("Port: " + port);

        resources = new TreeMap<String, Controller>();
        sessions = new HashMap<String, Session>();
        cookieQueue = new DelayQueue<Session>();
        new Thread(new Runnable() {

            @Override
            public void run() {
                garbageCookies();
            }
        }).start();
    }

    /**
     * Adds the page.
     *
     * @param filename
     *            the filename
     * @param page
     *            the page
     */
    public final void addPage(final String filename, final Controller page) {
        resources.put(filename, page);
    }

    /**
     * Run.
     */
    public final void run() {
        try {
            @SuppressWarnings("resource")
            ServerSocket socket = new ServerSocket(port);

            while (true) {
                new Thread(new HttpThread(socket.accept(), this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    public final Node getRoot() {
        return root;
    }

    /**
     * Gets the nav.
     *
     * @param path
     *            the path
     * @return the nav
     */
    public final String getNav(final String path) {
        return getNav(getRoot(), path);
    }

    /**
     * Gets the nav.
     *
     * @param n
     *            the n
     * @param path
     *            the path
     * @return the nav
     */
    private String getNav(final Node n, final String path) {
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        String token = tokenizer.hasMoreTokens() ? tokenizer.nextToken("/")
                : "";
        String next = tokenizer.hasMoreTokens() ? tokenizer.nextToken("") : "";

        String res = "<ul>";
        for (Node c : n.getChild()) {
            res += "<li " + (c.getName().equals(token) ? "selected" : "") + ">";
            res += "<a href='" + c.getUrl() + "'>" + c.getLabel() + "</a>";
            if (c.getChild().size() > 0) {
                res += getNav(c, next);
            }
            res += "</li>";
        }
        res += "</ul>";
        return res;
    }

    /**
     * Gets the page.
     * 
     * @param socket
     *
     * @param url
     *            the url
     * @return the page
     * @throws IOException
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */
    public final void process(Socket socket) throws Exception{
        Request request = new Request(socket, this);
        String url = request.getFile().substring(1);
        StringTokenizer ssPage = new StringTokenizer(url, "/");
        Node currentNode = root;
        if (url.length() != 0) {
            while (currentNode != null && ssPage.hasMoreElements()) {
                currentNode = currentNode.getChild(ssPage.nextToken());
            }
        }
        
        Controller controller = null;
        Method method = null;
        if(currentNode!=null){
            controller = currentNode.getController();
            method = currentNode.getMethod();
        }
        else {
            controller = new FileController();
            method = FileController.class.getMethod("process");
        }
        
        controller.prepare(this, request);
        Response response = (Response) method.invoke(controller);
        response.send(socket, controller.getSession());

    }

}
