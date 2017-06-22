package com.liteorm.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liteorm.anno.TableName;
import com.liteorm.model.SQLBuilder;
import com.liteorm.model.SQLStatement;
import com.liteorm.model.TableEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public final class TableManager {

    /**
     * 缓存所有Class和TableEntity对象
     */
    private static final Map<String, TableEntity> tableEntityMap = new HashMap<>();
    /**
     * 记录所有表名称,用于判断表是否存在
     */
    private static final List<String> allTableList = new ArrayList<>();

    public static String getTableName(Class entity) {
        TableName tableName = (TableName) entity.getAnnotation(TableName.class);
        if (tableName != null) {
            return tableName.name();
        }
        return entity.getName();
    }

    public static TableEntity getTableEntity(Class entity) {
        String tableName = getTableName(entity);
        if (tableEntityMap.containsKey(tableName)) {
            return tableEntityMap.get(tableName);
        }
        TableEntity entity1 = new TableEntity(entity);
        tableEntityMap.put(tableName, entity1);
        return entity1;
    }

    //判断有没有表,没有则创建
    public static void checkOrCreateTable(SQLiteDatabase database, Class entity) {
        TableEntity tableEntity = getTableEntity(entity);
        if (!allTableList.contains(tableEntity.getTableName())) {
            SQLStatement statement = SQLBuilder.buildCheckTableExists(tableEntity);
            Cursor cursor = database.rawQuery(statement.getSql(), (String[]) statement.getArgs());
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                cursor.close();
                //创建表
                if (count == 0) {
                    SQLStatement sqlCreate = SQLBuilder.buildCreateTableSql(tableEntity);
                    database.execSQL(sqlCreate.getSql());
                }
            }
        }
    }


}
