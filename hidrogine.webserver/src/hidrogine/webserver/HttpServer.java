package hidrogine.webserver;

import hidrogine.sessionmanager.Session;
import hidrogine.sessionmanager.SessionManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The Class HttpServer.
 */
public class HttpServer {

    /** The Constant VERSION. */
    public static final String VERSION = "1.4.6";

    /** The port. */
    private int port;

    private SessionManager sessionManager;
    /** The resources. */
    private TreeMap<String, Controller> resources;

    /** The config. */
    private JSONObject config = null;

    /** The index. */
    private Node root;

    /**
     * Gets the session.
     *
     * @param sid
     *            the sid
     * @return the session
     */
    public final Session getSession(final String sid) {
        return sessionManager.getSession(sid);
    }

    /**
     * Generate session.
     *
     * @return the session
     */
    public final Session generateSession() {
        return sessionManager.generateSession();
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
            sessionManager = new SessionManager(
                    config.has("session") ? config.getLong("session") : 360000);

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

    
    public Call getCall(String url) throws NoSuchMethodException {

        StringTokenizer ssPage = new StringTokenizer(url, "/");
        Node currentNode = root;
        if (url.length() != 0) {
            while (currentNode != null && ssPage.hasMoreElements()) {
                currentNode = currentNode.getChild(ssPage.nextToken());
            }
        }
        Class<?> controller = null;
        Method method = null;
        if (currentNode != null) {
            controller = currentNode.getController();
            method = currentNode.getMethod();
        } else {
            controller = FileController.class;
            method = FileController.class.getMethod("process");
        }
        return new Call(controller,method);
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
    public final void process(Socket socket) throws Exception {
        Request request = new Request(socket, this);
        String url = request.getFile().substring(1);
        Call call = getCall(url);
        Response response = call.invoke(this,request);
        response.send(socket, request.getSession());
    }

}
