import Network.SocketManager;
import Pages.PageManager;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        SocketManager socketManager = SocketManager.getInstance();
        PageManager pageManager = new PageManager();
        socketManager.init();
        while (true) {
            socketManager.handleRequests();
            switch (socketManager.getRequest().getPath()) {
                case "/":
                    socketManager.sendPage("Chrome");
                    break;
                case "/about":
                    socketManager.sendPage();
                    break;
                case "/counter":
                    pageManager.handleCounter();
                    break;
                case "/add":
                    pageManager.handleAdd();
                    break;
                case "/privatecounter":
                    pageManager.handlePrivateCounter();
                    break;
                default:
                    pageManager.handle404();
                    break;
            }
            socketManager.closeConnection();
        }
    }
}
