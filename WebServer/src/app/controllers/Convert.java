package app.controllers;

import java.io.IOException;

import org.json.JSONException;

import app.Global;
import hidrogine.wavefront.MaterialLibrary;
import hidrogine.wavefront.WaveFront;
import hidrogine.webserver.Controller;
import hidrogine.webserver.Html;
import hidrogine.webserver.Response;
import hidrogine.webserver.Upload;

/**
 * The Class FileUpload.
 */
public class Convert extends Controller {

    /** The Constant upload. */
    static final String CONVERT = Html.fromFile("src/app/views/convert.html");
    /*
     * (non-Javadoc)
     *
     * @see hidrogine.ws.Controller#process()
     */
    @Override
    public final Response process() {
        Upload file = getRequest().getUpload("file");

        if (file == null) {
            return ok(Global.getTemplate(CONVERT, getRequest().getFile()));   
        }

        try {
            if("obj".equals(file.getExtension()))
                return ok(new WaveFront(file.getFile()).toFile(),file.getName()+".geo");
            if("mtl".equals(file.getExtension()))
                return ok(new MaterialLibrary(file.getFile()).toFile(),file.getName()+".mat");
        } catch (IOException | JSONException e) {
        }
        return ok("ERROR");                  

                
    }
}
