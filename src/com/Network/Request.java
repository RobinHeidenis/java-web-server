package com.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Request {
    private String path;
    private String queryString;
    private String host;
    private String userAgent;
    private final ArrayList<Cookie> cookies = new ArrayList<>();
    private final ArrayList<QueryParameter> queryParameters = new ArrayList<>();

    public Request(BufferedReader in) throws IOException {
        String s;
        while (!(s = in.readLine()).equals("")) {
            if (s.startsWith("Host: ")) {
                host = s.split(":", 2)[1].strip();
            } else if (s.startsWith("User-Agent: ")) {
                userAgent = s.split(":", 2)[1].strip();
            } else if (s.startsWith("Cookie: ")) {
                parseCookies(s);
            } else if (s.startsWith("GET /")) {
                parsePathInfo(s);
            }
        }
    }

    private void parseCookies(String s) {
        String[] cookies = s.split(":", 2)[1].split(";");
        for (int i = 0; i < cookies.length; i++) {
            cookies[i] = cookies[i].trim();
            this.cookies.add(new Cookie(cookies[i].split("=")[0], cookies[i].split("=")[1]));
        }
    }

    private void parsePathInfo(String s) {
        String fullPath = s.substring(3);
        fullPath = fullPath.substring(0, fullPath.length() - 9);
        path = fullPath.split("\\?")[0].trim();
        if (fullPath.split("\\?").length > 1) {
            queryString = fullPath.split("\\?")[1].trim();
            for (String queryParam : queryString.split("&")) {
                queryParameters.add(new QueryParameter(queryParam.split("=")[0].trim(), queryParam.split("=")[1].trim()));
            }
        }
    }

    @Override
    public String toString() {
        return "com.Network.Request {\n" +
                "path: " + path + "\n" +
                "queryString: " + queryString + "\n" +
                "host: " + host + "\n" +
                "userAgent: " + userAgent + "\n" +
                "cookies: " + cookies + "\n" +
                "}";
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getHost() {
        return host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public ArrayList<Cookie> getCookies() {
        return cookies;
    }

    public ArrayList<QueryParameter> getQueryParameters() {
        return queryParameters;
    }
}
