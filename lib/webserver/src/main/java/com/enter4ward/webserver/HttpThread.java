package com.enter4ward.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * The Class HttpThread.
 */
public class HttpThread implements Runnable {

	/** The Constant BUFFERSZ. */
	static final int BUFFERSZ = 1024;

	/** The server. */
	private HttpServer server;

	/** The socket. */
	private Socket socket;

	/**
	 * Instantiates a new http thread.
	 *
	 * @param sok
	 *            the sok
	 * @param srv
	 *            the srv
	 */
	public HttpThread(final Socket sok, final HttpServer srv) {
		server = srv;
		socket = sok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public final void run() {
		try {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			try {
				while (server.process(is, os));
			} catch (Exception e) {
				e.printStackTrace();
			}
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
