package app;

import hidrogine.webserver.Html;
import hidrogine.webserver.HttpServer;

/**
 * The Class Global.
 */
public final class Global {

    /**
     * Instantiates a new global.
     */
    private Global() {
    }

    /** The server. */
    private static final HttpServer SERVER = new HttpServer(
            "src/app/config.json");

    /** The Constant template. */
    private static final String TEMPLATE = Html
            .fromFile("src/app/views/template.html");

    /**
     * Gets the template.
     *
     * @param main
     *            the main
     * @param file
     *            the file
     * @return the template
     */
    public static String getTemplate(final String main, final String file) {
        return TEMPLATE.replace("@main", main).replace("@nav",
                SERVER.getNav(file));
    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        SERVER.run();
    }
}
