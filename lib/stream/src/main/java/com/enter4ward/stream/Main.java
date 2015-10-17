package com.enter4ward.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

public class Main {
	private static Map<String, byte[]> globalBuffer = new TreeMap<String, byte[]>();

	// ffmpeg -i rtsp://admin:admin@192.168.0.100:554/video.mjpg -codec copy -an
	// http://localhost:1991/video.mjpg

	public static String readLine(InputStream is) throws IOException {
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

	public static void doPost(String line, InputStream is ) throws IOException {
		String[] splits = line.split(" ");
		String path = splits[1];

		while (true) {
			line = readLine(is);

			boolean isHex = line.matches("^[0-9a-fA-F]+$");

			if (isHex) {
				int len = Integer.valueOf(line, 16);
				byte[] data = new byte[len];
				int read = is.read(data);
				globalBuffer.put(path, data);
				/*
				 * FileOutputStream fos = new
				 * FileOutputStream("test"+path+"."+part);
				 * fos.write(data); fos.close();
				 */
			} else {
				System.out.println(line);
			}
			line = "";
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ServerSocket welcomeSocket = new ServerSocket(1991);

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			InputStream inFromClient = connectionSocket.getInputStream();
			OutputStream outToClient = connectionSocket.getOutputStream();

			String line = readLine(inFromClient);
			if (line.startsWith("POST")) {
				doPost(line, inFromClient);
			}else if (line.startsWith("GET")) {
				goGet(line , outToClient);
			}
		}
	}

	private static void goGet(String line, OutputStream outToClient) {
		// TODO Auto-generated method stub
		
	}
}
