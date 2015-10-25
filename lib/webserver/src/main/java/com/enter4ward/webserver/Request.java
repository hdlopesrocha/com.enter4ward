package com.enter4ward.webserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;

/**
 * The Class Request.
 */
public class Request {

	/** The Constant BUFFERSZ. */
	static final int BUFFERSZ = 1024;

	/** The attributes. */
	private TreeMap<String, String> attributes = new TreeMap<String, String>();
	/** The session. */
	private String sessionId = null;

	/** The version. */
	private String version = "";

	/** The url. */
	private String url = "";

	/** The file. */
	private String file = "";

	/** The method. */
	private String method = "";

	private String connection = "";

	/** The encodings. */
	private List<String> encodings = new ArrayList<String>();

	/** The uploaded files. */
	private Map<String, Upload> uploadedFiles = new TreeMap<String, Upload>();

	/** The headers. */
	private Map<String, String> headers = new TreeMap<String, String>();


	/**
	 * Instantiates a new request.
	 *
	 * @param socket
	 *            the socket
	 * @param server
	 *            the server
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Request(final InputStream is) throws IOException {

		String currentLine = HttpTools.readLine(is);
		if (currentLine == null) {
			throw new IOException();
		}
		StringTokenizer ssLine = new StringTokenizer(currentLine, " ");
		method = ssLine.nextToken();
		url = ssLine.nextToken();
		version = ssLine.nextToken();

		StringTokenizer ssUrl = new StringTokenizer(url, "?");
		if (ssUrl.hasMoreElements()) {
			file = ssUrl.nextToken();
		}
		if (ssUrl.hasMoreElements()) {
			setAttributes(ssUrl.nextToken());
		}
		// System.out.println("****************");
		// BUILD HEADERS
		while ((currentLine = HttpTools.readLine(is)) != null && currentLine.length() > 0) {
			// System.out.println(currentLine);
			StringTokenizer ssDots = new StringTokenizer(currentLine, ":");
			String key = ssDots.hasMoreElements() ? ssDots.nextToken().trim().toLowerCase() : "";
			String value = ssDots.hasMoreElements() ? ssDots.nextToken().trim().toLowerCase() : "";
			headers.put(key, value);
		}
		String value;

		if ((value = headers.get("cookie")) != null) {
			sessionId = value.substring(3);
		}

		if ((value = headers.get("accept-encoding")) != null) {
			for (String s : value.split(",")) {
				encodings.add(s.trim());
			}
		}

		if ((value = headers.get("content-type")) != null) {
			if (value.contains("application/x-www-form-urlencoded")) {
				int contentLength = Integer.valueOf(headers.get("content-length"));
				setAttributes(readSize(is, contentLength));
			} else if (value.contains("multipart/form-data")) {
				String[] mpfd = value.split("boundary=");
				if (mpfd.length > 1) {
					StringTokenizer bst = new StringTokenizer(mpfd[1], ";");
					String boundary = "\r\n--" + bst.nextToken();
					multipartForm(is, boundary);
				}
			}
		}
		connection = headers.get("connection");
	}

	public String getSessionId() {
		return sessionId;
	}

	public boolean keepAlive() {
		return connection != null && connection.equals("keep-alive");

	}



	/**
	 * Read size.
	 *
	 * @param is
	 *            the is
	 * @param size
	 *            the size
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String readSize(final InputStream is, final int size) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		for (int i = 0; i < size; ++i) {
			b = is.read();
			baos.write(b);
		}
		return new String(baos.toByteArray());
	}

	/**
	 * Multipart form.
	 *
	 * @param is
	 *            the is
	 * @param boundary
	 *            the boundary
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void multipartForm(final InputStream is, final String boundary) throws IOException {
		String currentLine = "";
		String contentDisposition = "";
		String filename = "upload";
		String name = "name";

		while ((currentLine = HttpTools.readLine(is)) != null && currentLine.length() > 0) {
			StringTokenizer ssDots = new StringTokenizer(currentLine, ":");
			String key = ssDots.hasMoreElements() ? ssDots.nextToken().trim().toLowerCase() : "";
			String value = ssDots.hasMoreElements() ? ssDots.nextToken("\n").substring(1).trim() : "";
			if (key.equals("content-disposition")) {
				contentDisposition = value;
				String[] cds1 = contentDisposition.split("filename=");
				if (cds1.length > 1) {
					StringTokenizer stfn = new StringTokenizer(cds1[1], ";");
					filename = stfn.nextToken().trim();
					filename = filename.substring(1, filename.length() - 1);
				}

				String[] cds2 = contentDisposition.split("name=");
				if (cds2.length > 1) {
					StringTokenizer stfn = new StringTokenizer(cds2[1], ";");
					name = stfn.nextToken().trim();
					name = name.substring(1, name.length() - 1);
				}

			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] msg = new byte[BUFFERSZ];
		int len = 0;
		do {
			len = is.read(msg);
			if (len > 0) {
				baos.write(msg, 0, len);
			}
		} while (len == BUFFERSZ);

		byte[] bytes = baos.toByteArray();
		int boundPos = indexOf(bytes, boundary.getBytes("UTF-8"));
		if (boundPos > 0) {
			File file2 = File.createTempFile(UUID.randomUUID().toString(), "");
			FileOutputStream fos = new FileOutputStream(file2);
			fos.write(Arrays.copyOfRange(bytes, 0, boundPos));
			fos.close();
			uploadedFiles.put(name, new Upload(file2, filename));
			if (boundPos + 2 * boundary.length() < bytes.length) {
				InputStream nextIs = new ByteArrayInputStream(
						Arrays.copyOfRange(bytes, boundPos + boundary.length() + 2, bytes.length));
				multipartForm(nextIs, boundary);
				nextIs.close();
			}
		}
	}

	/**
	 * Gets the upload.
	 *
	 * @param name
	 *            the name
	 * @return the upload
	 */
	public final Upload getUpload(final String name) {
		return uploadedFiles.get(name);
	}

	/**
	 * Index of.
	 *
	 * @param bytes
	 *            the bytes
	 * @param boundary
	 *            the boundary
	 * @return the int
	 */
	private int indexOf(final byte[] bytes, final byte[] boundary) {
		for (int i = 0; i < bytes.length; ++i) {
			boolean found = true;
			for (int j = 0; j < boundary.length; ++j) {
				if (j + i >= bytes.length || bytes[i + j] != boundary[j]) {
					found = false;
					break;
				}
			}
			if (found) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Accept encoding.
	 *
	 * @param enc
	 *            the enc
	 * @return the boolean
	 */
	public final Boolean acceptEncoding(final String enc) {
		return encodings.contains(enc);
	}

	/**
	 * Sets the attributes.
	 *
	 * @param a
	 *            the new attributes
	 */
	private void setAttributes(final String a) {

		if (a != null) {
			StringTokenizer tok1 = new StringTokenizer(a, "&");
			while (tok1.hasMoreElements()) {
				StringTokenizer tok2 = new StringTokenizer(tok1.nextToken(), "=");
				String key = tok2.hasMoreElements() ? tok2.nextToken() : "";
				String value = tok2.hasMoreElements() ? tok2.nextToken() : "";
				attributes.put(key, value);
			}
		}
	}

	/**
	 * Read.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	public final String read(final String key) {
		return attributes.get(key);
	}

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	public final Set<Entry<String, String>> getEntries() {
		return attributes.entrySet();
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public final String getFile() {
		return file;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public final String getMethod() {
		return method;
	}

	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}
}
