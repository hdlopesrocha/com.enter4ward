package com.enter4ward.myserver.controllers;

import com.enter4ward.webserver.Controller;
import com.enter4ward.webserver.HttpTools;

/**
 * The Class FileUpload.
 */
public abstract class Template extends Controller {

	/** The Constant template. */
	private static final String TEMPLATE = HttpTools.loadText("template.html");

	protected String getTemplate(final String main, final String nav) {
		return TEMPLATE.replace("@main", main).replace("@nav", nav);
	}


	

    /**
     * Gets the nav.
     *
     * @param n
     *            the n
     * @param path
     *            the path
     * @return the nav
     */
    protected String getNav(final String path) {

        String res = "<ul>";
        res += getLi("Home","/",path.equals("/"));
        res += getLi("Convert","/convert",path.startsWith("/convert"));
        res += getLi("Upload","/upload",path.startsWith("/upload"));
        res += getLi("Store","/store",path.startsWith("/store"));
        res += getLi("Login","/login",path.startsWith("/login"));        
        res += "</ul>";
        return res;
    }

    private String getLi(String name, String url,boolean selected){
        String res = "";
    	res += "<li " + (selected ? "selected" : "") + ">";
        res += "<a href='" + url + "'>" + name + "</a>";
        res += "</li>";	
        return res;
    }
    
}
