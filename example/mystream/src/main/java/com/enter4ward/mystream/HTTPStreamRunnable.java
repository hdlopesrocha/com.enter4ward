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

public class HTTPStreamRunnable implements Runnable {

	private static final byte[] CRLF = "\r\n".getBytes();
	private static final int CHUNK_SIZE = 32768;

	
	
	public void doPost(String uri, Map<String, List<String>> args, InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int maxChunkSize =0;
		while (true) {
			String line = readLine(is);
			
			boolean isHex = line.matches("^[0-9a-fA-F]+$");
			if (isHex) {
				int len = Integer.valueOf(line, 16);
				// System.out.println("L=" + len);
				int l = len;
				byte[] data = new byte[len];

				while (l > 0) {
					int read = is.read(data, 0, l);
					baos.write(data, 0, read);
					l -= read;
				}

				if (len < maxChunkSize) {
					byte[] bytes = baos.toByteArray();
					handler.onChunkArrived(uri, args, bytes);
					baos.reset();
				}else{
					maxChunkSize = len;
					System.out.println(maxChunkSize);
				}

				/*
				 * FileOutputStream fos = new
				 * FileOutputStream("test"+path+"."+part); fos.write(data);
				 * fos.close();
				 */
			}
			line = "";
		}
	}

	public String readLine(InputStream is) throws IOException {
		String line = "";
		byte[] buffer = new byte[1];

		while (true) {
			int res = is.read(buffer);
			if (res == -1) {
				break;
			}
			line += (char) buffer[0];
			if (line.endsWith("\r\n")) {
				return line.trim();
			}
		}
		return null;
	}


	private void replaceGet(String uri, Map<String, List<String>> args, Map<String, String> params, OutputStream outToClient)
			throws IOException, InterruptedException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
	
		ps.println("HTTP/1.0 200");
		for(Entry<String, String> e : params.entrySet()){
			ps.println(e.getKey()+": " + e.getValue());
		}
		ps.println("Content-Type: multipart/x-mixed-replace;boundary=--myboundary");
		outToClient.write(baos.toByteArray());

		while (true) {
			byte[] ans = handler.onChunkRequest(uri, args);

			if (ans != null) {
				baos.reset();
				baos.write(CRLF);
				ps.println("--myboundary");
				ps.println("Content-Type: image/jpeg");
				ps.println("Content-Length: " + ans.length);
				baos.write(CRLF);
				baos.write(ans);
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
		
		ps.println("HTTP/1.1 200");
		ps.println("Connection: close");
		ps.println("Content-Type: " + type);
		ps.println("Transfer-Encoding: chunked");
		for(Entry<String, String> e : params.entrySet()){
			ps.println(e.getKey()+": " + e.getValue());
		}
		baos.write(CRLF);
		outToClient.write(baos.toByteArray());
		baos.reset();

		while (true) {
			byte[] ans = handler.onChunkRequest(uri, args);

			if (ans != null) {

				for(int l=0; l < ans.length ; l+=CHUNK_SIZE) {
					int wr = ans.length -l;
					if(wr>CHUNK_SIZE)
						wr = CHUNK_SIZE;
					
					
					ps.print(Integer.toString(wr, 16));
					baos.write(CRLF);
					baos.write(ans,l, wr);
					baos.write(CRLF);
					outToClient.write(baos.toByteArray());
					baos.reset();
				}
				
				ps.print("0");
				baos.write(CRLF);
				baos.write(CRLF);
				outToClient.write(baos.toByteArray());
				baos.reset();
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

			Map<String,String> headers = new TreeMap<String,String>();
			
			System.out.println("---------------------------");
			System.out.println(line);
			while ((line = readLine(is)).length()>0) {
				String [] tokens = line.split(":"); 
				String key = tokens[0].trim();
				String value = tokens[1].trim();
				headers.put(key, value);
				System.out.println(line);
			}
			
			Map<String,String> params = handler.onStartReceiving(uri);		
			
			if (splits[0].startsWith("POST")) {
				doPost(uri, args, is);
			} else if (splits[0].startsWith("GET")) {
				if(args.containsKey("r")){
					replaceGet(uri, args, params, outToClient);
				}else {
					doGet(uri, args, params,outToClient);
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
