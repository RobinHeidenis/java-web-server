package com.Network;

public class Cookie {
    private String name;
    private String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "com.Network.Cookie {\n" +
                "       name: " + name + '\n' +
                "       value: " + value + '\n' +
                "    }\n";
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
