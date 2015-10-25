package com.enter4ward.myserver.controllers;

import java.io.IOException;

import com.enter4ward.webserver.HttpTools;
import com.enter4ward.webserver.Response;

/**
 * The Class FileUpload.
 */
public class LoginController extends Template {

	/** The Constant login. */
	private static final String LOGIN = HttpTools.loadText("login.html");

	@Override
	public void run() throws IOException{
		
		Response response = createResponse(Response.CODE_OK);	
		response.setContent(getTemplate(LOGIN, getNav(getRequest().getUrl())));
		send(response);
	
	}

}
