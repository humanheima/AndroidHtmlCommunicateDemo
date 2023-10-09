package com.brotherd.androidhtmlcommunicatedemo.framework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.framework.jsiml.JsCall;
import com.brotherd.androidhtmlcommunicatedemo.framework.jsiml.JsDeviceInfo;
import com.brotherd.androidhtmlcommunicatedemo.framework.jsiml.JsSelectPhoto;
import com.brotherd.androidhtmlcommunicatedemo.framework.jsiml.JsTakePhoto;
import com.zyj.hybridbridge.JsBridge;

/**
 * Created by p_dmweidu on 2023/10/9
 * Desc: 一个简单的JsBridge框架
 *
 */
public class SimpleFrameworkActivity extends AppCompatActivity {


    private WebView webView;

    public static void launch(Context context) {
        Intent starter = new Intent(context, SimpleFrameworkActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_framework);
        webView = findViewById(R.id.webView);
        JsBridge.getInstance().init(this, webView)
                .addJsAction(JsCall.ACTION, JsCall.class)
                .addJsAction(JsSelectPhoto.ACTION, JsSelectPhoto.class)
                .addJsAction(JsTakePhoto.ACTION, JsTakePhoto.class)
                .addJsAction(JsDeviceInfo.ACTION, JsDeviceInfo.class);

        webView.loadUrl("file:///android_asset/framework/demo.html");

    }
}