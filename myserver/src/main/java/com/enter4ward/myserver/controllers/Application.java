package app.controllers;

import com.enter4ward.wavefront.MaterialLibrary;
import com.enter4ward.wavefront.WaveFront;
import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.Html;
import com.enter4ward.webserver.Response;
import com.enter4ward.webserver.Upload;

import java.io.IOException;

import org.json.JSONException;

/**
 * The Class FileUpload.
 */
public class Application extends Controller {

	/** The Constant upload. */
	private static final String CONVERT = Html.fromFile("src/app/views/convert.html");
	/** The Constant login. */
	private static final String LOGIN = Html.fromFile("src/app/views/login.html");
	/** The Constant upload. */
	private static final String UPLOAD = Html.fromFile("src/app/views/upload.html");
	/** The Constant store. */
	private static final String STORE = Html.fromFile("src/app/views/store.html");
	/** The Constant img. */
	private static final String IMAGE = "<div><img src='res/test.png'></div>";
	/** The Constant template. */
	private static final String TEMPLATE = Html.fromFile("src/app/views/template.html");

	private String getTemplate(final String main, final String nav) {
		return TEMPLATE.replace("@main", main).replace("@nav", nav);
	}

	public final Response index() {
		return ok(getTemplate(IMAGE, getNav()));

	}

	public final Response login() {
		return ok(getTemplate(LOGIN, getNav()));
	}

	public final Response convert() {
		Upload file = getRequest().getUpload("file");

		if (file == null) {
			return ok(getTemplate(CONVERT, getNav()));
		}

		try {
			if ("obj".equals(file.getExtension()))
				return ok(new WaveFront(file.getFile()).toFile(),
						file.getName() + ".geo");
			if ("mtl".equals(file.getExtension()))
				return ok(new MaterialLibrary(file.getFile()).toFile(),
						file.getName() + ".mat");
		} catch (IOException | JSONException e) {
		}
		return ok("ERROR");
	}

	public final Response store() {
		String receivedText = getRequest().read("text");
		if (receivedText != null) {
			session("text", receivedText);
		}
		String storedText = (String) session("text");
		if (storedText == null) {
			storedText = "";
		}
		return ok(getTemplate(STORE.replace("@content", storedText), getNav()));
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

		return ok(getTemplate(UPLOAD, getNav()));
	}

}
