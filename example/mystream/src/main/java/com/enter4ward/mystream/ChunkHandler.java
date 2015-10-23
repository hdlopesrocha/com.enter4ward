package com.enter4ward.mystream;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChunkHandler {

	void onChunkArrived(String path, Map<String,List<String>> args, byte [] bytes);
	byte [] onChunkRequest(String path, Map<String,List<String>> args) throws IOException;
	
	Map<String,String> onStartReceiving(String path);
	
}
