package com.liteorm.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public final class FieldUtil {


    public static List<Field> getAllField(List<Field> fieldList, Class entity) {
        Field fields[] = entity.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (DataUtil.isValidate(field) || DataUtil.isSupportType(field)) {
                    fieldList.add(field);
                }
            }
            Class superClass = entity.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                getAllField(fieldList, superClass);
            }
        }
        return fieldList;
    }

    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setValue2Field(Field field,Object object,Object value) {
        field.setAccessible(true);
        try {
            field.set(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
