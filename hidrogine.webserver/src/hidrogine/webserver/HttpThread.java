package hidrogine.webserver;

import java.io.IOException;
import java.net.Socket;

/**
 * The Class HttpThread.
 */
public class HttpThread implements Runnable {

    /** The Constant BUFFERSZ. */
    static final int BUFFERSZ = 1024;

    /** The server. */
    private HttpServer server;

    /** The socket. */
    private Socket socket;

    /**
     * Instantiates a new http thread.
     *
     * @param sok
     *            the sok
     * @param srv
     *            the srv
     */
    public HttpThread(final Socket sok, final HttpServer srv) {
        server = srv;
        socket = sok;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public final void run() {

        try {

            Request request = new Request(socket, server);

            String filename = request.getFile().substring(1);

            Controller page = filename.length() == 0 ? server.getIndex()
                    : server.getPage(filename);

            if (page == null) {
                page = new FileController();
            }
            page.prepare(server, request);
            Response response = page.process();
            response.send(socket, page.getSession());

        } catch (IOException e) {
            // e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
