package com.enter4ward.webserver;

import java.util.List;
import java.util.Map;

public interface ChunkHandler {
	void onChunkArrived(String path, Map<String,List<String>> args, byte [] bytes);
	byte [] onChunkRequest(String path, Map<String,List<String>> args);
	Map<String,String> onStartReceiving(String path);
}
