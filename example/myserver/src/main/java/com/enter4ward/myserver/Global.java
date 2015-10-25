package com.enter4ward.myserver;

import com.enter4ward.myserver.controllers.ConvertController;
import com.enter4ward.myserver.controllers.IndexController;
import com.enter4ward.myserver.controllers.LoginController;
import com.enter4ward.myserver.controllers.StoreController;
import com.enter4ward.myserver.controllers.StreamController;
import com.enter4ward.myserver.controllers.UploadController;
import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.FileController;
import com.enter4ward.webserver.HttpServer;
import com.enter4ward.webserver.Request;
import com.enter4ward.webserver.RequestHandler;

/**
 * The Class Global.
 */
public final class Global {


    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
    	new Global();
    }

	public Global() {
 	new HttpServer(1991,360000, new RequestHandler() {
			
			@Override
			public Controller onRequestArrived(Request request) {
				String path = request.getUrl();
				if(path.equals("/")){
					return new IndexController();
				}else if(path.startsWith("/login")){
					return new LoginController();
				}else if(path.startsWith("/store")){
					return new StoreController();
				}else if(path.startsWith("/convert")){
					return new ConvertController();
				}else if(path.startsWith("/upload")){
					return new UploadController();
				}else if(path.startsWith("/stream")){
					return new StreamController();
				}else {
					return new FileController();	
				}				
			}
		}).run();
	}
}
