package com;

import com.Annotations.RouteProcessor;
import com.Network.SocketManager;
import com.Pages.PageManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        //Process annotations in route file
        SocketManager socketManager = SocketManager.getInstance();
        PageManager pageManager = new PageManager();
        socketManager.init();
        HashMap<String, Method> routes = RouteProcessor.process(Routes.class);
        //TODO tell user what kinda browser he's using
        while (true) {
            socketManager.handleRequests();
            if(routes.containsKey(socketManager.getRequest().getPath())) {
                routes.get(socketManager.getRequest().getPath()).invoke(Routes.class);
            }
            //find proper route in hashmap, if exist execute method, if not, check if resource exists in public folder, if not, return 404
            pageManager.handle404();
            socketManager.closeConnection();
        }
    }
}
