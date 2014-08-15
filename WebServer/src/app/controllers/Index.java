package app.controllers;

import app.Global;
import hidrogine.webserver.Controller;
import hidrogine.webserver.Response;

/**
 * The Class Index.
 */
public class Index extends Controller {

    /** The Constant img. */
    static final String IMAGE = "<div><img src='res/test.png'></div>";

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.ws.Controller#process()
     */
    @Override
    public final Response process() {
        return ok(Global.getTemplate(IMAGE, getRequest().getFile()));

    }
}
