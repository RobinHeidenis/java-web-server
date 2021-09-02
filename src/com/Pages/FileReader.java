package com.Pages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    public static String getPageText(String page) throws IOException {
        String uri = System.getProperty("user.dir") + "/src/com.Pages/" + page + ".html";
        return String.join("", Files.readAllLines(Paths.get(uri)));
    }

    public static String tryGetPageText(String page) {
        try {
            return getPageText(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String tryGetPageTextWithParam(String page, String param) {
        String text = tryGetPageText(page);
        return text.replace("{{$param}}", param);
    }
}
