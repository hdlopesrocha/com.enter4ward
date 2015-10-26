package com.enter4ward.myserver.controllers;

import java.io.IOException;

import com.enter4ward.myserver.Template;
import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.HttpTools;
import com.enter4ward.webserver.Response;

/**
 * The Class FileUpload.
 */
public class StoreController extends Controller {

	/** The Constant store. */
	private static final String STORE = HttpTools.loadText("store.html");

	@Override
	public void run() throws IOException {
		String receivedText = request().read("text");
		if (receivedText != null) {
			session().write("text", receivedText);
		}
		String storedText = (String) session().read("text");
		if (storedText == null) {
			storedText = "";
		}

		Response response = createResponse(Response.CODE_OK);	

		response.setContent(Template.getTemplate(STORE.replace("@content", storedText), Template.getNav(request().getUrl())));
		response.send();
	}

}
