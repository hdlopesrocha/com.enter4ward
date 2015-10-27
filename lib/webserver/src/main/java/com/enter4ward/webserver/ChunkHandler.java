package com.enter4ward.webserver;

public interface ChunkHandler {
	void onChunkArrived(Request request, byte [] bytes);
	byte [] onChunkRequest(Request request);
}
