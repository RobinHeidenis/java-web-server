package com.Network;

import com.Pages.FileReader;
import com.Terminal.Terminal;
import com.Terminal.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager {
    private static SocketManager instance;
    private ServerSocket server;
    private Socket client;
    private BufferedReader in;
    private Request request;
    private OutputStream out;

    public static SocketManager getInstance() {
        if (instance == null) instance = new SocketManager();
        return instance;
    }

    public void init() {
        try {
            server = new ServerSocket(8080);
            Terminal.info("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRequests() throws IOException {
        client = server.accept();
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        request = new Request(in);
        Terminal.info("GET " + request.getPath());
        out = client.getOutputStream();
    }

    public void sendData(String data) {
        try {
            out.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStatusCode(int code) {
        SocketManager.getInstance().sendData("HTTP/1.0 " + code + "\r\n");
    }

    public void sendText(String text) {
        sendData("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + Util.getLength(text) + "\r\n\r\n" + text);
    }

    public void sendPublicResource(String text, String fileType) {
        sendData("HTTP/1.0 200 OK\r\nContent-Type: text/" + fileType + "\r\nContent-Length: " + Util.getLength(text) + "\r\n\r\n" + text);
    }

    public void sendTextWithCookie(String text, String name, String value) {
        sendData("HTTP/1.0 200 OK\r\nSet-Cookie: " + name + "=" + value + "\r\nContent-Type: text/html\r\nContent-Length: " + Util.getLength(text) + "\r\n\r\n" + text);
    }

    public void sendPage(String location) {
        sendPage(location, "");
    }

    public void sendPublicResource(String url) throws IOException {
        String extension = url.substring(url.lastIndexOf('.') + 1);
        SocketManager.getInstance().sendPublicResource(FileReader.getPublicResource(url), extension);
    }

    public void sendPage(String location, String param) {
        SocketManager.getInstance().sendText(FileReader.tryGetPageTextWithParam("/" + location, param));
    }

    public void sendPageWithCookie(String location, String param, String name, String value) {
        SocketManager.getInstance().sendTextWithCookie(FileReader.tryGetPageTextWithParam("/" + location, param), name, value);
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request getRequest() {
        return request;
    }
}
