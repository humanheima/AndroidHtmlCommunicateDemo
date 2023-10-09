package com.zyj.hybridbridge;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebView;
import com.google.gson.Gson;

/**
 * dec:抽象的js处理类，处理具体业务的类需要继承此类
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public abstract class JsAction {

    private static final String TAG = "JsAction";

    protected abstract void handleAction(Activity context, String jsonStr);


    public void callback(final WebView webView, final String callback, final Object result) {
        //JsAction的子类中的handleAction方法是在JavaBridge线程中执行的，
        // 这里要切换到主线程执行
        Log.i(TAG, "callback: current thread is " + Thread.currentThread().getName());
        BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == result || null == callback || "".equals(callback)) {
                    return;
                }
                String resultStr = new Gson().toJson(result);
                String url = "javascript:" + callback + "(" + resultStr + ")";
                Log.i(TAG, "onNext: url = " + url);
                webView.loadUrl(url);

            }
        });
    }
}
