package com.enter4ward.webserver;

public interface RequestHandler {

	Controller onRequestArrived(Request request);
}
