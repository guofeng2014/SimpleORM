package com.simpleorm;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.liteorm.DbConfig;
import com.liteorm.DbFactory;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //faceBook浏览器查看数据库
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        //LiteOrm 配置
        DbConfig.Config config = new DbConfig.Config();
        config.setContext(this);
        config.setDbVersion(1);
        config.setOnUpdateCallBack(new DbConfig.onUpdateCallBack() {

            @Override
            public void onUpdateListener(int newVersion, int oldVersion, SQLiteDatabase db) {

            }
        });
        config.setDbName("myDb");
        DbFactory.getInstance().setDbConfig(config.build());
    }
}
