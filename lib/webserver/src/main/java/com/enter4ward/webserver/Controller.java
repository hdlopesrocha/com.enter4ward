package com.enter4ward.webserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.enter4ward.session.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
public abstract class Controller {
	private InputStream is;
	private OutputStream os;
	private Request request;
	private Session session;

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
	}

	public abstract void run() throws IOException;

	/**
	 * Prepare.
	 *
	 * @param s
	 *            the s
	 * @param request
	 *            the r
	 * @param socket
	 */
	protected final void init(InputStream is, OutputStream os, Request request, Session session) {
		this.os = os;
		this.is = is;
		this.request = request;
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

	public Request getRequest() {
		return request;
	}

	public Response createResponse(String status) {
		Response response = new Response(status, request, session);
		return response;
	}

	public void send(Response response) throws IOException {
		byte [] data = response.build();
		os.write(data);
		os.flush();
	}

	public void send(File file) throws FileNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(file);
		send(IOUtils.toByteArray(fis));
		fis.close();
	}
	
	
	public void send(byte[] data) throws IOException {
		HttpTools.sendChunk(os, data);
	}

	private int maxFrameSize = 0;

	protected int readChunkAux(ByteArrayOutputStream baos) throws IOException {
		String line = HttpTools.readLine(is);

		boolean isHex = line.matches("^[0-9a-fA-F]+$");
		if (isHex) {
			int len = Integer.valueOf(line, 16);
			byte[] data = new byte[len];
			is.read(data);
			
			
			baos.write(data);
			HttpTools.readLine(is);
			return len;
		}
		return 0;
	}

	
	public byte[] readChunk() throws IOException {
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		while (true){
			int len = readChunkAux(baos);
			if (len < maxFrameSize || len==0) {
				return baos.toByteArray();
			} else {
				maxFrameSize = len;
			}
		}		
	}

}
