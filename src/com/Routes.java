package com;

import com.Annotations.Route;
import com.Counter.Counter;
import com.Network.Cookie;
import com.Network.QueryParameter;
import com.Network.SocketManager;

import java.util.ArrayList;

public class Routes {
    @Route("/")
    public void index() {
        SocketManager.getInstance().sendPage("index");
    }

    @Route("/about")
    public void about() {
        SocketManager.getInstance().sendPage("about");
    }

    @Route("/counter")
    public void counter(){
        SocketManager.getInstance().sendPage("counter","" + Counter.getInstance().addToGlobalCounter());
    }

    @Route("/add")
    public void add() {
        SocketManager socketManager = SocketManager.getInstance();
        ArrayList<QueryParameter> parameters = socketManager.getRequest().getQueryParameters();
        int total = 0;
        for (QueryParameter parameter : parameters) {
            try {
                total += Integer.parseInt(parameter.getValue());
            } catch (NumberFormatException e) {
                socketManager.sendPage("add", "Not a number");
                return;
            }
        }
        socketManager.sendPage("add", "" + total);
    }

    @Route("/counter/private")
    public void privateCounter() {
        SocketManager socketManager = SocketManager.getInstance();
        Cookie numberCookie = null;
        for (Cookie cookie : socketManager.getRequest().getCookies()) {
            if (cookie.getName().equals("number")) {
                numberCookie = cookie;
                break;
            }
        }
        if (numberCookie != null)
            socketManager.sendPageWithCookie("privatecounter", "" + (Integer.parseInt(numberCookie.getValue()) + 1), "number", "" + (Integer.parseInt(numberCookie.getValue()) + 1));
        else socketManager.sendPageWithCookie("privatecounter", "0", "number", "0");
    }
}
