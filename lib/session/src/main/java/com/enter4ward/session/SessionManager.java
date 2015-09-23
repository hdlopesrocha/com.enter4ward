package com.enter4ward.session;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * The Class HttpServer.
 */
public class SessionManager {

	/** The session. */
	private long duration = 0;

	/** The sessions. */
	private HashMap<String, Session> sessions;

	/** The cookie queue. */
	private DelayQueue<Session> cookieQueue;

	/** The Constant random. */
	private static final Random RANDOM = new Random();

	/** The Constant chars. */
	private static final String CHARS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

	/**
	 * Gets the session.
	 *
	 * @param sid
	 *            the sid
	 * @return the session
	 */
	public final Session getSession(final String sid) {
		return sessions.get(sid);
	}

	/**
	 * Random string.
	 *
	 * @param size
	 *            the size
	 * @return the string
	 */
	private String randomString(final int size) {
		String str = "";
		int charsLen = CHARS.length();
		for (int i = 0; i < size; i++) {
			str += CHARS.charAt(RANDOM.nextInt(charsLen));
		}
		return str;
	}

	/**
	 * Generate session.
	 *
	 * @return the session
	 */
	public final Session generateSession() {
		Session cookie = null;

		long expireTime = System.currentTimeMillis() + duration;

		while (cookie == null) {
			String s = randomString(32);
			while (sessions.get(s) != null) {
				s = randomString(32);
			}
			cookie = new Session(s, expireTime);

			Session replacedCookie = sessions.put(s, cookie);
			if (replacedCookie != null) {
				sessions.put(s, replacedCookie);
				cookie = null;
			}

		}

		cookieQueue.add(cookie);
		return cookie;
	}

	/**
	 * Garbage cookies.
	 */
	private final void garbageCookies() {
		while (true) {
			try {
				Session entry = cookieQueue.take();
				sessions.remove(entry.getId());
				System.out.println(entry.getId() + " expired!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Instantiates a new http server.
	 *
	 * @param configpath
	 *            the configpath
	 */
	public SessionManager(long d) {
		duration = d;
		sessions = new HashMap<String, Session>();
		cookieQueue = new DelayQueue<Session>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				garbageCookies();
			}
		}).start();
	}

}
