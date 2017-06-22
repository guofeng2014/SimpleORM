package com.liteorm.sqlite;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/6/20.
 */

public interface SqliteImp {

    <T> void save(List<T> entityList) throws IllegalAccessException, InstantiationException;

    <T> List<T> query(Class<T> entity) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException;

    <T> long delete(Class<T> entity, int id);

    <T> void delete(List<T> list);

    long delete(Object entity);

    <T> void dropTable(Class<T> entity);
}
