package app.controllers;

import java.io.IOException;

import app.Global;
import hidrogine.webserver.Controller;
import hidrogine.webserver.Html;
import hidrogine.webserver.Response;
import hidrogine.webserver.Upload;

/**
 * The Class FileUpload.
 */
public class FileUpload extends Controller {

    /** The Constant upload. */
    static final String UPLOAD = Html.fromFile("src/app/views/upload.html");

    /*
     * (non-Javadoc)
     *
     * @see hidrogine.ws.Controller#process()
     */
    @Override
    public final Response process() {
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
