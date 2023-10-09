package com.brotherd.androidhtmlcommunicatedemo.util;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by p_dmweidu on 2023/10/7
 * Desc: TODO 如何封装一个通信框架，
 * https://www.jianshu.com/p/02afb387b6b4
 */
public class WebAppInterface {

    private static final String TAG = "WebAppInterface";

    private Activity mContext;

    /**
     * Instantiate the interface and set the context
     */
    public WebAppInterface(Activity c) {
        mContext = c;
    }

    /**
     * js调用Android的函数
     */
    @JavascriptInterface
    public void startFunction() {
        //current thread is JavaBridge
        //可以在子线程弹出Toast，但是要有Looper，Looper.prepare()和Looper.loop()
        Log.i(TAG, "startFunction: current thread is " + Thread.currentThread().getName());
        Toast.makeText(mContext, "我是安卓原生的弹窗", Toast.LENGTH_SHORT).show();
    }

    /**
     * js调用Android的函数并给Android原生传递一个参数
     */
    @JavascriptInterface
    public void startFunction(String arg) {
        Log.i(TAG, "startFunction: current thread is " + Thread.currentThread().getName());
        Toast.makeText(mContext, arg, Toast.LENGTH_SHORT).show();
    }

}