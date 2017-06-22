package com.liteorm;

import android.database.sqlite.SQLiteDatabase;

import com.liteorm.sqlite.SqliteImp;
import com.liteorm.sqlite.SqliteUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public class DbFactory {

    private DbFactory() {
    }

    private static DbFactory instance;

    public static DbFactory getInstance() {
        if (instance == null) {
            synchronized (DbFactory.class) {
                if (instance == null) {
                    instance = new DbFactory();
                }
            }
        }
        return instance;
    }

    private SQLiteDatabase database;

    private SqliteImp sqliteImp;

    public void setDbConfig(DbConfig dbConfig) {
        SqlteLiteHelper sqlteLiteHelper = new SqlteLiteHelper(dbConfig.getContext(),
                dbConfig.getDbName(), null, dbConfig.getDbVersion(), dbConfig.getOnUpdateCallBack());
        database = sqlteLiteHelper.getWritableDatabase();
        sqliteImp = new SqliteUtil(database);
    }

    public <T> void save(List<T> entityList) {
        checkConfigNull();
        try {
            sqliteImp.save(entityList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> query(Class<T> entity) {
        checkConfigNull();
        try {
            return sqliteImp.query(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> long delete(Class<T> entity, int id) {
        checkConfigNull();
        return sqliteImp.delete(entity, id);
    }


    public <T> void delete(List<T> list) {
        checkConfigNull();
        if (!list.isEmpty()) {
            sqliteImp.delete(list);
        }
    }
    
    public <T> void dropTable(Class<T> entity) {
        checkConfigNull();
        sqliteImp.dropTable(entity);
    }

    private void checkConfigNull() {
        if (database == null) {
            throw new NullPointerException("you should set DbConfig first...");
        }
    }

}
