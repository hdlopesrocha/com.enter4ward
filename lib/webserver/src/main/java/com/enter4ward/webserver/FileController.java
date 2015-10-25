package com.enter4ward.webserver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileController extends Controller {

	@Override
	public void run() throws IOException {

		final String filename = getRequest().getUrl().substring(1);
		final URL url = FileController.class.getClassLoader().getResource(filename);
		if (url != null) {
			File file = new File(url.getFile());

			Response response = new Response(Response.CODE_OK, getRequest(), getSession());
			response.setContent(file);
			response.setContentType(file);
			//response.setChunked();
			send(response);
			//send(file);
		}else {
			Response response = new Response(Response.CODE_NOT_FOUND, getRequest(), getSession());
			send(response);
		}

	}

}
