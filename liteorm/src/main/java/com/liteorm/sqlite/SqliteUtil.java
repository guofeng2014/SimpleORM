package com.liteorm.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liteorm.anno.ColumnName;
import com.liteorm.anno.ID;
import com.liteorm.db.TableManager;
import com.liteorm.model.SQLBuilder;
import com.liteorm.model.SQLStatement;
import com.liteorm.model.TableEntity;
import com.liteorm.util.ClassUtil;
import com.liteorm.util.DataUtil;
import com.liteorm.util.FieldUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public class SqliteUtil implements SqliteImp {

    private SQLiteDatabase database;

    public SqliteUtil(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public <T> void save(List<T> entityList) throws IllegalAccessException, InstantiationException {
        Object item = entityList.iterator().next();
        if (item != null) {
            database.beginTransaction();
            Class classType = item.getClass();
            TableManager.checkOrCreateTable(database, classType);
            TableEntity tableEntity = TableManager.getTableEntity(classType);
            for (T t : entityList) {
                List<Field> fieldList = new ArrayList<>();
                FieldUtil.getAllField(fieldList, t.getClass());
                ContentValues values = new ContentValues();
                for (String key : tableEntity.getValues().keySet()) {
                    Field field = tableEntity.getValues().get(key);
                    Object value = FieldUtil.getFieldValue(t, field);
                    if (value instanceof String) {
                        values.put(key, (String) value);
                    } else if (value instanceof Integer) {
                        values.put(key, (Integer) value);
                    } else if (value instanceof Byte) {
                        values.put(key, (Byte) value);
                    } else if (value instanceof Short) {
                        values.put(key, (Short) value);
                    } else if (value instanceof Long) {
                        values.put(key, (Long) value);
                    } else if (value instanceof Double) {
                        values.put(key, (Double) value);
                    } else if (value instanceof Float) {
                        values.put(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        values.put(key, (Boolean) value);
                    } else {
                        values.put(key, DataUtil.object2ByteArray(value));
                    }
                }

                long id = database.insert(tableEntity.getTableName(), null, values);
                FieldUtil.setValue2Field(tableEntity.getIdField(), t, (int) id);

            }
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }

    @Override
    public <T> List<T> query(Class<T> entity) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        TableEntity tableEntity = TableManager.getTableEntity(entity);
        SQLStatement statement = SQLBuilder.buildQuerySql(tableEntity);
        Cursor cursor = database.rawQuery(statement.getSql(), null);
        List<T> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            T t = (T) ClassUtil.getClassInstance(entity);
            DataUtil.injectData2Object(cursor, t, tableEntity);
            list.add(t);
        }
        cursor.close();
        return list;
    }

    @Override
    public <T> long delete(Class<T> entity, int id) {
        TableEntity tableEntity = TableManager.getTableEntity(entity);
        return database.delete(tableEntity.getTableName(), SQLBuilder.ID + "=?", new String[]{String.valueOf(id)});
    }

    @Override
    public <T> void delete(List<T> list) {
        T t = list.iterator().next();
        if (t != null) {
            TableEntity tableEntity = TableManager.getTableEntity(t.getClass());
            database.beginTransaction();
            for (T data : list) {
                int id = (int) FieldUtil.getFieldValue(data, tableEntity.getIdField());
                database.delete(tableEntity.getTableName(), SQLBuilder.ID + "=?", new String[]{String.valueOf(id)});
            }
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }

    @Override
    public long delete(Object entity) {
        TableEntity tableEntity = TableManager.getTableEntity(entity.getClass());
        int id = (int) FieldUtil.getFieldValue(entity, tableEntity.getIdField());
        return database.delete(tableEntity.getTableName(), SQLBuilder.ID + "=?", new String[]{String.valueOf(id)});
    }

    @Override
    public <T> void dropTable(Class<T> entity) {
        TableEntity tableEntity = TableManager.getTableEntity(entity);
        SQLStatement dropStatement = new SQLStatement();
        dropStatement.setSql("drop table if exists " + tableEntity.getTableName());
        database.execSQL(dropStatement.getSql());
    }

}
