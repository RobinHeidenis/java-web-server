package com.Pages;

import com.Network.SocketManager;
import com.Terminal.Terminal;

public class PageManager {
    public void handle404() {
        Terminal.warn("Unknown route, returning 404");
        SocketManager.getInstance().sendStatusCode(404);
    }
}
