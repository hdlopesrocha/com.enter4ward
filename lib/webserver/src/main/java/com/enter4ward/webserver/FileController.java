package com.enter4ward.webserver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileController extends Controller {

	@Override
	public void run() throws IOException {

		final String filename = request().getUrl().substring(1);
		final URL url = FileController.class.getClassLoader().getResource(filename);
		if (url != null) {
			File file = new File(url.getFile());

			Response response = createResponse(Response.CODE_OK);
			//response.setContent(file);
			response.setContentType(file);
			response.setChunked();
			response.send();
			response.send(file);
		}else {
			Response response = createResponse(Response.CODE_NOT_FOUND);
			response.send();
		}

	}

}
