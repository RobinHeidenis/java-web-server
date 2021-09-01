package Pages;

import Counter.Counter;
import Network.Cookie;
import Network.QueryParameter;
import Network.SocketManager;
import Terminal.Terminal;

import java.util.ArrayList;

public class PageManager {
    public void handle404() {
        Terminal.warn("Unknown route, returning 404");
        SocketManager.getInstance().sendStatusCode(404);
    }

    public void handlePrivateCounter() {
        SocketManager socketManager = SocketManager.getInstance();
        Cookie numberCookie = null;
        for (Cookie cookie : socketManager.getRequest().getCookies()) {
            if (cookie.getName().equals("number")) {
                numberCookie = cookie;
                break;
            }
        }
        if (numberCookie != null)
            socketManager.sendPageWithCookie("" + (Integer.parseInt(numberCookie.getValue()) + 1), "number", "" + (Integer.parseInt(numberCookie.getValue()) + 1));
        else socketManager.sendPageWithCookie("0", "number", "0");
    }

    public void handleAdd() {
        SocketManager socketManager = SocketManager.getInstance();
        ArrayList<QueryParameter> parameters = socketManager.getRequest().getQueryParameters();
        int total = 0;
        for (QueryParameter parameter : parameters) {
            try {
                total += Integer.parseInt(parameter.getValue());
            } catch (NumberFormatException e) {
                socketManager.sendPage("Not a number");
                return;
            }
        }
        socketManager.sendPage("" + total);
    }

    public void handleCounter() {
        SocketManager.getInstance().sendPage("" + Counter.getInstance().addToGlobalCounter());
    }
}
