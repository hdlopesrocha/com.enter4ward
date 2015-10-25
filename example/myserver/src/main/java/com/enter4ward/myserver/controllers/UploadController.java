package com.enter4ward.myserver.controllers;

import java.io.IOException;

import com.enter4ward.webserver.HttpTools;
import com.enter4ward.webserver.Response;
import com.enter4ward.webserver.Upload;

/**
 * The Class FileUpload.
 */
public class UploadController extends Template {

	/** The Constant upload. */
	private static final String UPLOAD = HttpTools.loadText("upload.html");


	@Override
	public void run() throws IOException {
	
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

		Response response = createResponse(Response.CODE_OK);	
		response.setContent(getTemplate(UPLOAD, getNav(getRequest().getUrl())));

		send(response);
		
		
	}

}
