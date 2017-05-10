package com.brotherd.androidhtmlcommunicatedemo;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by dumingwei on 2017/5/10.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(getApplicationContext(), null);
    }
}
