package com.enter4ward.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class HttpTools {

	/**
	 * From file.
	 *
	 * @param url
	 *            the url
	 * @return the string
	 */
	public static String loadText(final String url) {
		try {
			InputStream is = HttpTools.class.getClassLoader().getResourceAsStream(url);
			byte[] bytes = IOUtils.toByteArray(is);

			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readLine(InputStream is) throws IOException {

		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		boolean cr = false;
		while (true) {
			int res = is.read();
			if (res == -1) {
				break;
			}
			if (res == 0xD) {
				if (cr) {
					baos.write(0xD); // add the previous \r
				} else {
					cr = true;
				}
			} else if (cr && res == 0xA) {
				return new String(baos.toByteArray());
			} else {
				baos.write(res);
				cr = false;
			}
		}
		return null;
	}
	
}
