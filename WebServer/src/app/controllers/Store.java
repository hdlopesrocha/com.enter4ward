package app.controllers;

import app.Global;
import hidrogine.webserver.Html;
import hidrogine.webserver.Controller;
import hidrogine.webserver.Response;

/**
 * The Class Store.
 */
public class Store extends Controller {

    /** The Constant store. */
    static final String STORE = Html.fromFile("src/app/views/store.html");

    /*
     * (non-Javadoc)
     *
     * @see hidrogine.ws.Controller#process()
     */
    @Override
    public final Response process() {
        String receivedText = getRequest().read("text");
        if (receivedText != null) {
            write("text", receivedText);
        }
        String storedText = (String) read("text");
        if (storedText == null) {
            storedText = "";
        }
        return ok(Global.getTemplate(STORE.replace("@content", storedText),
                getRequest().getFile()));
    }
}
