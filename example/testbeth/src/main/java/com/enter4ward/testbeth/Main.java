package com.enter4ward.testbeth;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.enter4ward.graphbeth.Alternative;
import com.enter4ward.graphbeth.Criteria;
import com.enter4ward.graphbeth.Judgement;
import com.enter4ward.graphbeth.JudgementType;

public class Main {



	public static void main(String[] args) throws Exception  {


	    	 Server server = new Server(8080);

		 ContextHandler handlerRoot = new ContextHandler("");
		 ContextHandler handlerCall = new ContextHandler("/call");
		 ContextHandler handlerAssets = new ContextHandler("/assets");

		 ResourceHandler root = new ResourceHandler();
		 root.setResourceBase("src/main/resources/root.html");


		 handlerRoot.setHandler(root);
		 handlerCall.setHandler(new CallServlet());




		 ResourceHandler rh0 = new ResourceHandler();
		 rh0.setDirectoriesListed(true);
		 rh0.setResourceBase("src/main/resources/assets");

		 handlerAssets.setHandler(rh0);


		 ContextHandlerCollection contexts = new ContextHandlerCollection();
		 contexts.setHandlers(new Handler[] {  handlerCall , handlerAssets,handlerRoot});
		 server.setHandler(contexts);

		 server.start();
		 server.join();



	}



}
