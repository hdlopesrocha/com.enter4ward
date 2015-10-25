package com.enter4ward.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

	private static byte [] CRLF = "\r\n".getBytes();
	
	private static final int CHUNK_SIZE = 32768;
	
	
	public void send(byte[] ans) throws IOException {
		if (ans != null) {
			for (int l = 0; l < ans.length; l += CHUNK_SIZE) {
				int wr = ans.length - l;
				if (wr > CHUNK_SIZE){
					wr = CHUNK_SIZE;
				}
				
				os.write(Integer.toString(wr,16).getBytes());
				os.write(CRLF);
				os.write(ans,l, wr);
				os.write(CRLF);
				os.flush();

			}
		}
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
