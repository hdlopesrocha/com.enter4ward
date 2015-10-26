package com.enter4ward.myserver.controllers;

import java.io.IOException;

import com.enter4ward.myserver.Template;
import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.Response;

/**
 * The Class FileUpload.
 */
public class WebGLController extends Controller {


	private static final String IFRAME = "<div><iframe src='/WebGL/index.html' style='width:100%;height:100%;' frameBorder='0'></iframe></div>";

	@Override
	public void run() throws IOException {
		Response response = createResponse(Response.CODE_OK);	
		response.setContent(Template.getTemplate(IFRAME, Template.getNav(request().getUrl())));	
		response.send();
	}

}
