package com.enter4ward.media;

import java.io.IOException;
import java.io.InputStream;

import com.enter4ward.media.container.Mpeg4;

// TODO: Auto-generated Javadoc
/**
 * The Interface VisibleObjectHandler.
 */
public class Main {

	
	public Main() throws IOException{
		InputStream is = getClass().getClassLoader().getResourceAsStream("test.mp4");
		Mpeg4 mp4 = new Mpeg4(is);
		
	}
	
	public static void main(String [] args) throws IOException{
		new Main();
		
		
	}
}
