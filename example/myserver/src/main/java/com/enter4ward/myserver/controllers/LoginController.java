package com.enter4ward.myserver.controllers;

import java.io.IOException;

import com.enter4ward.myserver.Template;
import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.HttpTools;
import com.enter4ward.webserver.Response;

/**
 * The Class FileUpload.
 */
public class LoginController extends Controller {

	/** The Constant login. */
	private static final String LOGIN = HttpTools.loadText("login.html");

	@Override
	public void run() throws IOException{
		if("POST".equals( request().getMethod())){
			System.out.println(request().getAttributes().entrySet().size());
		}
			
		Response response = createResponse(Response.CODE_OK);	
		response.setContent(Template.getTemplate(LOGIN, Template.getNav(request().getUrl())));
		response.send();
	
	}

}
