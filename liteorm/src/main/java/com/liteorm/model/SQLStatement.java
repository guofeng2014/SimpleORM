package com.liteorm.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class SQLStatement {

    private String sql;

    private Object[] args;


    public String getSql() {
        return sql;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }


    public void setSql(String sql) {
        this.sql = sql;
    }


}
