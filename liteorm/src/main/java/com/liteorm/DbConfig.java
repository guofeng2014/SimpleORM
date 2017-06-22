package com.liteorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public class DbConfig {
    private onUpdateCallBack onUpdateCallBack;
    private String dbName;
    private int dbVersion;
    private Context context;

    public DbConfig(Config config) {
        this.onUpdateCallBack = config.onUpdateCallBack;
        this.dbName = config.dbName;
        this.dbVersion = config.dbVersion;
        this.context = config.context.getApplicationContext();
    }

    public DbConfig.onUpdateCallBack getOnUpdateCallBack() {
        return onUpdateCallBack;
    }

    public String getDbName() {
        return dbName;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public Context getContext() {
        return context;
    }

    public static class Config {
        onUpdateCallBack onUpdateCallBack;
        String dbName;
        int dbVersion;
        Context context;

        public Config setContext(Context context) {
            this.context = context;
            return this;
        }

        public Config setOnUpdateCallBack(DbConfig.onUpdateCallBack onUpdateCallBack) {
            this.onUpdateCallBack = onUpdateCallBack;
            return this;
        }

        public Config setDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Config setDbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
            return this;
        }

        public DbConfig build() {
            return new DbConfig(this);
        }
    }

    public interface onUpdateCallBack {

        void onUpdateListener(int newVersion, int oldVersion, SQLiteDatabase db);
    }
}
