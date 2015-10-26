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
	private InputStream _inputStream;
	private OutputStream _outputStream;
	private Request _request;
	private Session _session;

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
	protected final void init(InputStream is, OutputStream os, Request r, Session s) {
		this._outputStream = os;
		this._inputStream = is;
		this._request = r;
		this._session = s;
	}

	public Session session() {
		return _session;
	}

	public Object session(String key) {
		return _session.read(key);
	}
	
	public void session(String key, Object value){
		_session.write(key,value);
	}
	
	public Request request() {
		return _request;
	}

	public Response createResponse(String status) {
		Response response = new Response(status, _request, _session,_outputStream);
		return response;
	}


	private int maxFrameSize = 0;

	protected int readChunkAux(ByteArrayOutputStream baos) throws IOException {
		String line = HttpTools.readLine(_inputStream);

		boolean isHex = line.matches("^[0-9a-fA-F]+$");
		if (isHex) {
			int len = Integer.valueOf(line, 16);
			byte[] data = new byte[len];
			_inputStream.read(data);
			baos.write(data);
			HttpTools.readLine(_inputStream);
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
