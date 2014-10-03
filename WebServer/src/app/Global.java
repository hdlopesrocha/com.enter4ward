package app;

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

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
    	new HttpServer("src/app/config.json").run();
    }
}
