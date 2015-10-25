package com.enter4ward.webserver;

import java.io.File;
import java.io.IOException;

public class FileController extends Controller {


	@Override
	public void run() throws IOException {
		
		final String filename = getRequest().getUrl().substring(1);
		File file = new File(FileController.class.getClassLoader().getResource(filename).getFile());
		
		Response response = new Response(Response.CODE_OK, getRequest(), getSession());
		response.setContent(file);
		
		send (response);
		
	}

}
