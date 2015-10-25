package com.enter4ward.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.enter4ward.session.Session;
import com.enter4ward.session.SessionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpServer.
 */
public class HttpServer {

	/** The Constant VERSION. */
	public static final String VERSION = "1.5.0";

	/** The port. */
	private int port;

	private RequestHandler requestHandler;

	/** The session manager. */
	private SessionManager sessionManager;

	/**
	 * Instantiates a new http server.
	 *
	 * @param configpath
	 *            the configpath
	 */
	public HttpServer(int port, int session, RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
		this.port = port;
		System.out.println("    __    _     __                 _          ");
		System.out.println("   / /_  (_)___/ /________  ____ _(_)___  ___ ");
		System.out.println("  / __ \\/ / __  / ___/ __ \\/ __ `/ / __ \\/ _ \\");
		System.out.println(" / / / / / /_/ / /  / /_/ / /_/ / / / / /  __/");
		System.out.println("/_/ /_/_/\\__,_/_/   \\____/\\__, /_/_/ /_/\\___/ ");
		System.out.println("                         /____/  web service");
		System.out.println("Language: Java");
		System.out.println("Version: " + VERSION);
		System.out.println("Port: " + port);
		sessionManager = new SessionManager(session);
	}

	/**
	 * Run.
	 */
	public final void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket socket = new ServerSocket(port);

			while (true) {
				new Thread(new HttpThread(socket.accept(), this)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the page.
	 *
	 * @param socket
	 *            the socket
	 * @param i
	 *            the i
	 * @return the page
	 * @throws Exception
	 *             the exception
	 */
	public final boolean process(InputStream is, OutputStream os) throws IOException {
		Request request = new Request(is);
		Session session = sessionManager.getSession(request.getSessionId());
		Controller controller = requestHandler.onRequestArrived(request);
		controller.init(is, os, request, session);
		controller.run();
		return request.keepAlive();
	}

}
