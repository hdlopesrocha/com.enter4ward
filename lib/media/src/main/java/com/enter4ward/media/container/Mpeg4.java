package com.enter4ward.media.container;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Mpeg4 {

	private int readUnsigned32(DataInputStream dis) throws IOException{
		return dis.readShort() & 0xFFFF;
	}
	
	private long readUnsigned64(DataInputStream dis) throws IOException{
		return dis.readLong() & 0xFFFFFFFF;
	}
	
	public static final String intToChars(int value) {
	    return Arrays.toString(intToByteArray(value));
	}
	
	
	public static final byte[] intToByteArray(int value) {
	    return new byte[] {
	            (byte)(value >>> 24),
	            (byte)(value >>> 16),
	            (byte)(value >>> 8),
	            (byte)value};
	}
	
	
	public Mpeg4(InputStream is) throws IOException {
		DataInputStream dis = new DataInputStream(is);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		int l = 0;
		int size = readUnsigned32(dis);
		int type = readUnsigned32(dis);
		long largesize = 0l;
		if(size==1){
			largesize = readUnsigned64(dis);
		}
		else if(size==0){
			// box extends to end of file
		}
		
		System.out.println("Size: "+size);
		System.out.println("Type: "+intToChars(type));
		
		while ((b = is.read()) >= 0) {
			baos.write(b);
			
			
			
			if (++l < 128) {
				System.out.println(Integer.toString(b, 16));
			}

		}

	}

}
