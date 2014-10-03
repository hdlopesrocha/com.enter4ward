package hidrogine.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
 * The Class Controller.
 */
public abstract class Controller {

    /** The server. */
    private HttpServer server;

    /** The request. */
    private Request request;

    /** The session. */
    private Session session;

    /**
     * Instantiates a new controller.
     */
    public Controller() {
    }



    /**
     * Read.
     *
     * @param key
     *            the key
     * @return the object
     */
    public final Object session(final String key) {
        if (session == null) {
            session = request.getSession();
        }
        return session != null ? session.read(key) : null;
    }

    /**
     * Write.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public final void session(final String key, final Object value) {
        if (session == null) {
            session = request.getSession();
            if (session == null) {
                session = server.generateSession();
            }
        }
        session.write(key, value);
    }

    /**
     * Gets the request.
     *
     * @return the request
     */
    public final Request getRequest() {
        return request;
    }
    
    public String getNav(){
        return server.getNav(request.getFile());
    }

    /**
     * Ok.
     *
     * @param html
     *            the html
     * @return the response
     */
    public final Response ok(final String html) {
        Response response = new Response(server, request);
        response.setContentType("text/html");
        response.setData(html.getBytes());
        return response;
    }

    /**
     * Ok.
     *
     * @param file
     *            the file
     * @return the response
     */
    public final Response ok(final File file) {
        StringTokenizer fileToks = new StringTokenizer(file.getName(), ".");
        Response response = new Response(server, request);

        try {
            response.setData(read(file));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (fileToks.countTokens() > 1) {
            fileToks.nextElement();
            response.setContentType(fileToks.nextToken());
        } else {
            response.setContentType("text/html");
        }
        return response;
    }

    /**
     * Ok.
     *
     * @param file
     *            the file
     * @param filename
     *            the filename
     * @return the response
     */
    public final Response ok(final File file, final String filename) {
        Response response = ok(file);
        response.setContentDisposition("attachment; filename=\"" + filename
                + "\"");
        return response;
    }

    /**
     * Read.
     *
     * @param file
     *            the file
     * @return the byte[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private byte[] read(final File file) throws IOException {

        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException(
                        "EOF reached while trying to read the whole file");
            }
        } finally {
            if (ios != null) {
                ios.close();
            }
        }

        return buffer;
    }

    /**
     * Prepare.
     *
     * @param s
     *            the s
     * @param r
     *            the r
     */
    public final void prepare(final HttpServer s, final Request r) {
        server = s;
        request = r;
    }

}
