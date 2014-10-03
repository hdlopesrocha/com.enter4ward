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
public class Application extends Controller {

    /** The Constant upload. */
    static final String CONVERT = Html.fromFile("src/app/views/convert.html");
    /** The Constant login. */
    static final String LOGIN = Html.fromFile("src/app/views/login.html");
    /** The Constant upload. */
    static final String UPLOAD = Html.fromFile("src/app/views/upload.html");
    /** The Constant store. */
    static final String STORE = Html.fromFile("src/app/views/store.html");

    /** The Constant img. */
    static final String IMAGE = "<div><img src='res/test.png'></div>";


    public final Response index() {
        return ok(Global.getTemplate(IMAGE, getRequest().getFile()));

    }
    public final Response login() {
        return ok(Global.getTemplate(LOGIN, getRequest().getFile()));
    }
    
    public final Response convert() {
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

    public final Response store() {
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
    public final Response upload() {
        Upload file1 = getRequest().getUpload("file1");

        if (file1 != null) {
            try {
                file1.copyTo(file1.getFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Upload file2 = getRequest().getUpload("file2");
        if (file2 != null) {
            try {
                file2.copyTo(file2.getFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ok(Global.getTemplate(UPLOAD, getRequest().getFile()));
    }
    

}