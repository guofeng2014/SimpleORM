package com.liteorm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class SqliteEntity {

    private String tableName;

    private final List<String> mList = new ArrayList<>();

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void add(String sqliteInfo) {
        mList.add(sqliteInfo);
    }

    public List<String> getmList() {
        return mList;
    }
}
