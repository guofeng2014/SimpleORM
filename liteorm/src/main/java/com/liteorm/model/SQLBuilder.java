package com.liteorm.model;

import com.liteorm.anno.ID;
import com.liteorm.util.DataUtil;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class SQLBuilder {

    private static final String SELECT_TABLE_SQL = "select count(*) from sqlite_master where name=?";
    private static final String BLANK = " ";
    public static final String ID = "id";

    /**
     * 查询所有table的语句
     *
     * @return
     */
    public static SQLStatement buildCheckTableExists(TableEntity tableEntity) {
        SQLStatement statement = new SQLStatement();
        statement.setSql(SELECT_TABLE_SQL);
        statement.setArgs(new String[]{tableEntity.getTableName()});
        return statement;
    }

    /**
     * 创建 sql语句 create table if not exists xx(id integer primary key autoincrement, xx text,xx integer)
     *
     * @param tableEntity
     * @return
     */
    public static SQLStatement buildCreateTableSql(TableEntity tableEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists ");
        stringBuilder.append(tableEntity.getTableName());
        stringBuilder.append(BLANK);
        stringBuilder.append("(");
        HashMap<String, Field> values = tableEntity.getValues();

        Field idField = tableEntity.getIdField();
        if (idField != null) {
            stringBuilder.append(ID + " Integer primary key autoIncrement,");
        }

        for (String column : values.keySet()) {
            Field field = values.get(column);
            if (!DataUtil.isValidate(field)) {
                continue;
            }

            stringBuilder.append(column);
            stringBuilder.append(BLANK);
            stringBuilder.append(DataUtil.getSqliteType(field));
            stringBuilder.append(",");

        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(")");

        SQLStatement statement = new SQLStatement();
        statement.setSql(stringBuilder.toString());
        return statement;
    }


    public static SQLStatement buildQuerySql(TableEntity tableEntity) {
        String sql = "select * from " + tableEntity.getTableName();
        SQLStatement statement = new SQLStatement();
        statement.setSql(sql);
        return statement;
    }

    public static SQLStatement buildDeleteSql(TableEntity tableEntity, int id) {
        String sql = "delete from " + tableEntity.getTableName() + "where " + ID + "=" + id;
        SQLStatement statement = new SQLStatement();
        statement.setSql(sql);
        return statement;
    }

}
