package com.enter4ward.myserver.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.enter4ward.myserver.ImageOverlay;
import com.enter4ward.webserver.ContentTypes;
import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.Payload;
import com.enter4ward.webserver.Response;

/**
 * The Class FileUpload.
 */
public class StreamController extends Controller {

	private static Map<String, Payload> globalBuffer = new TreeMap<String, Payload>();
	private static BufferedImage LOGO;
	
	public StreamController() {
		if (LOGO == null) {
			try {
				LOGO = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Payload getPayload(String path) {
		Payload payload = null;
		synchronized (globalBuffer) {
			payload = globalBuffer.get(path);
			if (payload == null) {
				payload = new Payload();
				globalBuffer.put(path, payload);
			}
		}
		return payload;
	}

	@Override
	public void run() throws IOException {
		Payload payload = getPayload(getRequest().getFile());

		if (getRequest().getMethod().equals("POST")) {
			while (true) {
				byte[] data = readChunk();
				payload.setData(data);
				payload.signal();
			}
		} else {
			Response response = createResponse(Response.CODE_OK);
			response.setContentType(ContentTypes.IMAGE_JPEG);
			response.setChunked();
			send(response);

			while (true) {
				byte[] data = null;
				payload.await();
				data = payload.getData();
				if (data != null) {
					if (getRequest().getAttributes().containsKey("info")) {
						data = ImageOverlay.editImage(data, LOGO);
					}
					send(data);
				}
			}
		}
	}
}
