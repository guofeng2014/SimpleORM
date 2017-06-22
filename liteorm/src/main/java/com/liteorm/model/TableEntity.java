package com.liteorm.model;

import com.liteorm.anno.ColumnName;
import com.liteorm.anno.ID;
import com.liteorm.db.TableManager;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by guofeng
 * on 2017/6/20.
 * 每个Table对应一个TableEntity
 */

public class TableEntity {

    private String tableName;

    private Class entity;

    private Field idField;

    private final HashMap<String, Field> values = new HashMap<>();

    public TableEntity(Class entity) {
        this.entity = entity;
        this.tableName = TableManager.getTableName(entity);
        listFieldValueToMap(entity);
    }

    private void listFieldValueToMap(Class entity) {
        Field[] fields = entity.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {

                ID id = field.getAnnotation(ID.class);
                if (id != null) {
                    idField=field;
                    continue;
                }

                ColumnName columnName = field.getAnnotation(ColumnName.class);
                if (columnName != null) {
                    values.put(columnName.name(), field);
                } else {
                    values.put(field.getName(), field);
                }

            }
        }
    }

    public Field getIdField() {
        return idField;
    }

    public String getTableName() {
        return tableName;
    }

    public Class getEntity() {
        return entity;
    }

    public HashMap<String, Field> getValues() {
        return values;
    }
}
