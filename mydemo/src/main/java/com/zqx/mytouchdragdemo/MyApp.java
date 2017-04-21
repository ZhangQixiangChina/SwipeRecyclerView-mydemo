package com.zqx.mytouchdragdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by ZhangQixiang on 2017/4/21.
 */

public class MyApp extends Application {

    public static Context context;

        @Override
        public void onCreate() {
            super.onCreate();
            context = this;
        }
}
