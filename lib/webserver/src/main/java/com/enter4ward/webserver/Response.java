package com.enter4ward.webserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.httpclient.ChunkedOutputStream;

import com.enter4ward.session.Session;

/**
 * The Class Response.
 */
public class Response {

	public static final String CODE_OK = "200 OK";
	public static final String CODE_NOT_FOUND = "404 NOT FOUND";

	/** The version. */
	private String version = "1.1";
	private OutputStream os;

	/** The status. */
	private String status = CODE_OK;

	/** The data. */
	private byte[] data = null;

	private Map<String, String> headers = new TreeMap<String, String>();

	/**
	 * Sets the content type.
	 *
	 * @param c
	 *            the new content type
	 */
	public final void setContentType(final String c) {
		addHeader(Headers.CONTENT_TYPE, c);
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param s
	 *            the s
	 * @param request
	 *            the r
	 */

	public void setContent(String text) {
		setContentType(ContentTypes.TEXT_HTML);
		setContent(text.getBytes());
	}

	/**
	 * Sets the data.
	 *
	 * @param d
	 *            the new data
	 */
	public final void setContent(final byte[] d) {
		data = d;
	}

	public void setContent(File file) {

		try {
			setContent(read(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		setContentType(file);
	}

	public void setContent(File file, String filename) {
		setContent(file);
		setContentDisposition("attachment; filename=\"" + filename + "\"");
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	/**
	 * Read.
	 *
	 * @param file
	 *            the file
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private byte[] read(final File file) throws IOException {

		byte[] buffer = new byte[(int) file.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(file);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} finally {
			if (ios != null) {
				ios.close();
			}
		}

		return buffer;
	}

	public void send() throws IOException {
		byte[] data = build();
		os.write(data);
		os.flush();
	}

	public void send(File file) throws IOException {
		if (file != null) {
			send(new FileInputStream(file));
		}
	}

	private OutputStream getOutputStream() throws IOException {
		boolean gzip = "gzip".equals(headers.get(Headers.CONTENT_ENCODING));
		boolean chunked = "chunked".equals(headers.get(Headers.TRANSFER_ENCODING));

		OutputStream outputStream = os;
		if (chunked) {
			outputStream = new ChunkedOutputStream(outputStream);
		}
		if (gzip) {
			outputStream = new GZIPOutputStream(outputStream);
		}
		return outputStream;
	}

	public void send(InputStream is) throws IOException {
		if (is != null) {
			OutputStream outputStream = getOutputStream();
			byte[] buffer = new byte[2048];
			int wr;
			while ((wr = is.read(buffer)) > 0) {
				outputStream.write(buffer, 0, wr);
			}
			outputStream.close();
		}
	}

	public void send(byte[] data) throws IOException {
		if (data != null) {
			OutputStream outputStream = getOutputStream();
			outputStream.write(data);
			outputStream.close();
		}
	}

	public void setChunked() {
		addHeader(Headers.TRANSFER_ENCODING, "chunked");
	}

	protected Response(final String status, final Request request, Session session, OutputStream os) {
		this.os = os;
		this.status = status;
		if (request.acceptEncoding("gzip")) {
			addHeader(Headers.CONTENT_ENCODING, "gzip");
		}
		addHeader(Headers.CONNECTION, (request.keepAlive() ? "keep-alive" : "close"));

		if (session != null) {
			addHeader(Headers.SET_COOKIE,
					"ID=" + session.getId() + "; Max-Age=" + session.getMaxAge() + "; Version=1;Path=/;");
		}
	}

	/**
	 * Gets the server time.
	 *
	 * @return the server time
	 */
	final String getServerTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * Builds the header.
	 *
	 * @param size
	 *            the size
	 * @param cookie
	 *            the cookie
	 * @return the string
	 */
	final String buildHeader() {
		String s = "";
		s += "HTTP/" + version + " " + status + "\r\n";
		s += "Date: " + getServerTime() + "\r\n";
		s += "Server: com.enter4ward/" + HttpServer.VERSION + "\r\n";
		for (Entry<String, String> e : headers.entrySet()) {
			s += e.getKey() + ": " + e.getValue() + "\r\n";
		}
		s += "\r\n";
		return s;
	}

	/**
	 * Sets the content disposition.
	 *
	 * @param c
	 *            the new content disposition
	 */
	public final void setContentDisposition(final String c) {
		addHeader(Headers.CONTENT_DIPOSITION, c);
	}

	/**
	 * Send data.
	 *
	 * @param socket
	 *            the socket
	 * @param session
	 *            the session
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final byte[] build() throws IOException {

		if ("gzip".equals(headers.get(Headers.CONTENT_ENCODING))) {
			if (data != null) {
				data = HttpTools.compressGzip(data);
			}
		}

		if (data != null) {
			addHeader(Headers.CONTENT_LENGTH, Integer.toString(data.length));
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(buildHeader().getBytes());
		if (data != null) {
			baos.write(data);
		}
		return baos.toByteArray();
	}

	public void setContentType(File file) {
		String[] fileToks = file.getName().split("\\.");
		String last = fileToks[fileToks.length - 1];

		if ("jpeg".equals(last) || "jpg".equals(last)) {
			setContentType(ContentTypes.IMAGE_JPEG);
		} else if ("png".equals(last)) {
			setContentType(ContentTypes.IMAGE_PNG);
		} else if ("gif".equals(last)) {
			setContentType(ContentTypes.IMAGE_GIF);
		} else if ("js".equals(last)) {
			setContentType(ContentTypes.APPLICATION_JAVASCRIPT);
		} else if ("css".equals(last)) {
			setContentType(ContentTypes.TEXT_CSS);
		} else {
			setContentType(ContentTypes.TEXT_HTML);
		}
	}

}
