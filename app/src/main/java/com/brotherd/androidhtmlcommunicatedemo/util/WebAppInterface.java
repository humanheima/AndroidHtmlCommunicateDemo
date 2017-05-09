package com.brotherd.androidhtmlcommunicatedemo.util;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {

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
        Toast.makeText(mContext, "我是安卓原生的弹窗", Toast.LENGTH_SHORT).show();
    }

    /**
     * js调用Android的函数并给Android原生传递一个参数
     */
    @JavascriptInterface
    public void startFunction(String arg) {
        Toast.makeText(mContext, arg, Toast.LENGTH_SHORT).show();
    }

}