package com.liteorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public class SqlteLiteHelper extends SQLiteOpenHelper {

    private DbConfig.onUpdateCallBack onUpdateCallBack;

    public SqlteLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DbConfig.onUpdateCallBack onUpdateCallBack) {
        super(context, name, factory, version);
        this.onUpdateCallBack = onUpdateCallBack;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (onUpdateCallBack != null) {
            onUpdateCallBack.onUpdateListener(newVersion,oldVersion,db);
        }
    }

}
