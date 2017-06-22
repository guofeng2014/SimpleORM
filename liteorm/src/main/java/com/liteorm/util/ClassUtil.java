package com.liteorm.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class ClassUtil {


    public static <T> Object getClassInstance(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor[] = clazz.getDeclaredConstructors();
        for (Constructor c : constructor) {
            Class[] cls = c.getParameterTypes();
            if (cls.length == 0) {
                c.setAccessible(true);
                return c.newInstance();
            } else {
                Object objects[] = new Object[cls.length];
                for (int i = 0; i < cls.length; i++) {
                    objects[i] = getDefaultPrimiticeValue(cls[i]);
                }
                c.setAccessible(true);
                return c.newInstance(objects);
            }
        }
        return null;
    }

    private static Object getDefaultPrimiticeValue(Class clazz) {
        if (clazz.isPrimitive()) {
            return clazz == boolean.class ? false : 0;
        }
        return null;
    }

}
