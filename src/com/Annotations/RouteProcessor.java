package com.Annotations;

import com.Routes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class RouteProcessor {
    public static HashMap<String, Method> process(Class<Routes> obj) {
        HashMap<String, Method> map = new HashMap<>();

        for (Method method : obj.getMethods()) {
            if(method.isAnnotationPresent(Route.class)) {
               map.put(method.getAnnotation(Route.class).value(), method);
            }
        }

        return map;
    }
}
