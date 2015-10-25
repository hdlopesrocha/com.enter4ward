package com.enter4ward.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

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

	public static byte [] compressGzip(byte [] data) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(data);
		gzip.close();
		return out.toByteArray();
	}

	private static byte [] CRLF = "\r\n".getBytes();
	
	private static final int CHUNK_SIZE = 32768;

	
	public static void sendChunk(OutputStream os, byte [] data) throws IOException{
	
			if (data != null) {
				for (int l = 0; l < data.length; l += CHUNK_SIZE) {
					int wr = data.length - l;
					if (wr > CHUNK_SIZE){
						wr = CHUNK_SIZE;
					}
					
					os.write(Integer.toString(wr,16).getBytes());
					os.write(CRLF);
					os.write(data,l, wr);
					os.write(CRLF);
					os.flush();

				}
				
				os.write("0".getBytes());
				os.write(CRLF);
				os.write(CRLF);
				os.flush();	
				
			}
	
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
