package com.enter4ward.mystream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.enter4ward.webserver.ChunkHandler;

public class HTTPStreamRunnable implements Runnable {

	private static final String CRLF = "\r\n";
	private static final int CHUNK_SIZE = 32768;

	public void doPost(String uri, Map<String, List<String>> args, InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int maxChunkSize = 0;
		while (true) {
			String line = readLine(is);
			boolean isHex = line.matches("^[0-9a-fA-F]+$");
			if (isHex) {
				int len = Integer.valueOf(line, 16);
				byte[] data = new byte[len];
				is.read(data);
				baos.write(data);
				if (len < maxChunkSize) {
					handler.onChunkArrived(uri, args, baos.toByteArray());
					baos.reset();
				} else {
					maxChunkSize = len;
				}
			}
		}
	}

	public String readLine(InputStream is) throws IOException {
		String line = "";
		boolean hasCarriage = false;
		while (true) {
			int res = is.read();
			if (res == -1) {
				break;
			}
			if (res == 0xD) {
				if (hasCarriage) {
					line += (char) 0xD; // add the previous \r
				} else {
					hasCarriage = true;
				}
			} else if (hasCarriage && res == 0xA) {
				return line;
			} else {
				line += (char) res;
				hasCarriage = false;
			}
		}
		return null;
	}

	private void replaceGet(String uri, Map<String, List<String>> args, Map<String, String> params,
			OutputStream outToClient) throws IOException, InterruptedException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		ps.print("HTTP/1.0 200" + CRLF);
		for (Entry<String, String> e : params.entrySet()) {
			ps.print(e.getKey() + ": " + e.getValue() + CRLF);
		}
		ps.print("Content-Type: multipart/x-mixed-replace;boundary=--myboundary" + CRLF);
		ps.flush();
		outToClient.write(baos.toByteArray());

		while (true) {
			byte[] ans = handler.onChunkRequest(uri, args);

			if (ans != null) {
				baos.reset();
				ps.print(CRLF);
				ps.print("--myboundary" + CRLF);
				ps.print("Content-Type: image/jpeg" + CRLF);
				ps.print("Content-Length: " + ans.length + CRLF);
				ps.print(CRLF);
				ps.write(ans);
				ps.flush();
				outToClient.write(baos.toByteArray());

			}
		}

	}

	private void doGet(String uri, Map<String, List<String>> args, Map<String, String> params, OutputStream outToClient)
			throws IOException, InterruptedException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		String type = "";
		if (uri.endsWith("mjpg")) {
			type = "image/jpeg";
		} else if (uri.endsWith("mp4")) {
			type = "video/mp4";
		}

		ps.print("HTTP/1.1 200" + CRLF);
		ps.print("Content-Type: " + type + CRLF);
		ps.print("Transfer-Encoding: chunked" + CRLF);
		for (Entry<String, String> e : params.entrySet()) {
			ps.print(e.getKey() + ": " + e.getValue() + CRLF);
		}
		ps.print(CRLF);
		ps.flush();
		outToClient.write(baos.toByteArray());
		baos.reset();

		while (true) {
			byte[] ans = handler.onChunkRequest(uri, args);

			if (ans != null) {

				for (int l = 0; l < ans.length; l += CHUNK_SIZE) {
					int wr = ans.length - l;
					if (wr > CHUNK_SIZE){
						wr = CHUNK_SIZE;
					}
					ps.print(Integer.toString(wr, 16) + CRLF);
					ps.write(ans, l, wr);
					ps.print(CRLF);
					ps.flush();
					outToClient.write(baos.toByteArray());
					baos.reset();
				}
			}
		}
	}

	private Socket socket;
	private ChunkHandler handler;

	public HTTPStreamRunnable(Socket socket, ChunkHandler handler) {
		this.socket = socket;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			OutputStream outToClient = socket.getOutputStream();

			String line = readLine(is);
			String[] splits = line.split(" ");
			String[] path = splits[1].split("\\?");
			String[] keyValues = path.length == 2 ? path[1].split("&") : new String[0];

			Map<String, List<String>> args = new TreeMap<String, List<String>>();
			for (String str : keyValues) {
				String[] kv = str.split("=");

				List<String> list = args.get(kv[0]);
				if (list == null) {
					list = new ArrayList<String>();
					args.put(kv[0], list);
				}
				list.add(kv.length == 2 ? kv[1] : null);

			}
			String uri = path[0];

			Map<String, String> headers = new TreeMap<String, String>();

			System.out.println("---------------------------");
			System.out.println(line);
			while ((line = readLine(is)).length() > 0) {
				String[] tokens = line.split(":");
				String key = tokens[0].trim();
				String value = tokens[1].trim();
				headers.put(key, value);
				System.out.println(line);
			}

			Map<String, String> params = handler.onStartReceiving(uri);

			if (splits[0].startsWith("POST")) {
				doPost(uri, args, is);
			} else if (splits[0].startsWith("GET")) {
				if (args.containsKey("r")) {
					replaceGet(uri, args, params, outToClient);
				} else {
					doGet(uri, args, params, outToClient);
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
