package com.enter4ward.webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Call {

    private Class<?> clazz;
    private Method method;
    
    public Call(Class<?> c, Method m){
        clazz = c;
        method = m;
    }
    
    public Response invoke(HttpServer server,Request request) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Controller instance = (Controller) clazz.newInstance();
        instance.prepare(server, request);
        return (Response) method.invoke(instance);
    }
    
}
