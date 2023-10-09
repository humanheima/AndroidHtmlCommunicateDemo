package com.zyj.hybridbridge;

import android.os.Handler;
import android.os.Looper;


/**
 * Created by p_dmweidu on 2023/10/9
 * Desc: 切换到主线程执行的工具类
 */
public class BackgroundTasks {

    private static final BackgroundTasks instance = new BackgroundTasks();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public static BackgroundTasks getInstance() {
        return instance;
    }

    private BackgroundTasks() {
    }

    public void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public boolean postDelayed(Runnable r, long delayMillis) {
        return handler.postDelayed(r, delayMillis);
    }
}
