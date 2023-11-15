package com;

import com.Annotations.RouteProcessor;
import com.Network.SocketManager;
import com.Pages.PageManager;
import com.Terminal.Terminal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        SocketManager socketManager = SocketManager.getInstance();
        PageManager pageManager = new PageManager();
        Routes routesClass = new Routes();
        socketManager.init();
        HashMap<String, Method> routes = RouteProcessor.process(routesClass);

        while (true) {
            try {
                socketManager.handleRequests();
                if (routes.containsKey(socketManager.getRequest().getPath())) {
                    routes.get(socketManager.getRequest().getPath()).invoke(routesClass);
                } else {
                    try {
                        socketManager.sendPublicResource(socketManager.getRequest().getPath());
                    } catch (IOException e) {
                        pageManager.handle404();
                    }
                }
                socketManager.closeConnection();
            } catch (Exception e) {
                pageManager.handle500();
                socketManager.closeConnection();
            }

        }
    }
}
