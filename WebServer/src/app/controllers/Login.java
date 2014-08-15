package app.controllers;

import app.Global;
import hidrogine.webserver.Html;
import hidrogine.webserver.Controller;
import hidrogine.webserver.Response;

/**
 * The Class Login.
 */
public class Login extends Controller {

    /** The Constant login. */
    static final String LOGIN = Html.fromFile("src/app/views/login.html");

    /*
     * (non-Javadoc)
     *
     * @see hidrogine.ws.Controller#process()
     */
    @Override
    public final Response process() {
        return ok(Global.getTemplate(LOGIN, getRequest().getFile()));
    }
}
