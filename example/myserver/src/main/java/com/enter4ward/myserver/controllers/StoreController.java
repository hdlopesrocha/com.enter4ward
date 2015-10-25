package com.enter4ward.myserver.controllers;

import java.io.IOException;

import com.enter4ward.webserver.HttpTools;
import com.enter4ward.webserver.Response;

/**
 * The Class FileUpload.
 */
public class StoreController extends Template {

	/** The Constant store. */
	private static final String STORE = HttpTools.loadText("store.html");

	@Override
	public void run() throws IOException {
		String receivedText = getRequest().read("text");
		if (receivedText != null) {
			getSession().write("text", receivedText);
		}
		String storedText = (String) getSession().read("text");
		if (storedText == null) {
			storedText = "";
		}

		Response response = createResponse(Response.CODE_OK);	

		response.setContent(getTemplate(STORE.replace("@content", storedText), getNav(getRequest().getUrl())));
		send(response);
	}

}
