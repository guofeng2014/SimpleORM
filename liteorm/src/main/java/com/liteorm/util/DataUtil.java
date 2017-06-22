package com.liteorm.util;

import android.database.Cursor;

import com.liteorm.model.SQLBuilder;
import com.liteorm.model.TableEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class DataUtil {

    private static final String TEXT = "TEXT";
    private static final String INTEGER = "Integer";
    private static final String REAL = "REAL";
    private static final String BLOB = "BLOB";

    /**
     * 是否是数据库支持的类型，目前只有基本数据类型和字节数组
     *
     * @param field
     * @return
     */
    public static boolean isSupportType(Field field) {
        String clzzName = field.getType().getName();
        if (clzzName.equals(String.class.getName())
                || clzzName.equals(Integer.class.getName())
                || clzzName.equals(int.class.getName())
                || clzzName.equals(byte.class.getName())
                || clzzName.equals(Byte.class.getName())
                || clzzName.equals(short.class.getName())
                || clzzName.equals(Short.class.getName())
                || clzzName.equals(long.class.getName())
                || clzzName.equals(Long.class.getName())
                || clzzName.equals(double.class.getName())
                || clzzName.equals(Double.class.getName())
                || clzzName.equals(Float.class.getName())
                || clzzName.equals(float.class.getName())
                || clzzName.equals(byte[].class.getName())
                || clzzName.equals(Byte[].class.getName()))
            return true;
        return false;
    }

    /**
     * 判断是否是有效的字段/static 和 transtraint不录入数据库
     *
     * @param field
     * @return
     */
    public static boolean isValidate(Field field) {
        int modify = field.getModifiers();
        if (Modifier.isStatic(modify) || Modifier.isTransient(modify)) {
            return false;
        }
        return true;
    }


    /**
     * 获得在数据库中的类型
     *
     * @param field
     * @return
     */
    public static String getSqliteType(Field field) {
        Class clazz = field.getType();
        String name = clazz.getName();
        if (String.class.getName().equals(name)) {
            return TEXT;
        } else if (int.class.getName().equals(name)
                || byte.class.getName().equals(name)
                || Byte.class.getName().equals(name)
                || short.class.getName().equals(name)
                || Short.class.getName().equals(name)) {
            return INTEGER;
        } else if (long.class.getName().equals(name)
                || Long.class.getName().equals(name)
                || double.class.getName().equals(name)
                || Double.class.getName().equals(name)
                || float.class.getName().equals(name)
                || Float.class.getName().equals(name)
                ) {
            return REAL;
        }
        return BLOB;
    }

    public static void injectData2Object(Cursor cursor, Object entity, TableEntity tableEntity) throws IllegalAccessException, IOException {

        //优先处理ID
        Field idField = tableEntity.getIdField();
        FieldUtil.setValue2Field(idField, entity, cursor.getInt(cursor.getColumnIndex(SQLBuilder.ID)));

        int columnCount = cursor.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            String columnName = cursor.getColumnName(i);
            Field field = tableEntity.getValues().get(columnName);
            if (field != null) {
                Class clazz = field.getType();
                String name = clazz.getName();
                if (int.class.getName().equals(name)
                        || byte.class.getName().equals(name)
                        || Byte.class.getName().equals(name)
                        || short.class.getName().equals(name)
                        || Short.class.getName().equals(name)) {
                    FieldUtil.setValue2Field(field, entity, cursor.getInt(i));
                } else if (name.equals(String.class.getName())) {
                    FieldUtil.setValue2Field(field, entity, cursor.getString(i));
                } else if (long.class.getName().equals(name)
                        || Long.class.getName().equals(name)
                        || double.class.getName().equals(name)
                        || Double.class.getName().equals(name)
                        || float.class.getName().equals(name)
                        || Float.class.getName().equals(name)) {
                    FieldUtil.setValue2Field(field, entity, cursor.getFloat(i));
                } else {
                    byte[] bytes = cursor.getBlob(i);
                    FieldUtil.setValue2Field(field, entity, bytes2Object(bytes));
                }
            }

        }
    }

    public static Object bytes2Object(byte[] bytes) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        try {
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] object2ByteArray(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
