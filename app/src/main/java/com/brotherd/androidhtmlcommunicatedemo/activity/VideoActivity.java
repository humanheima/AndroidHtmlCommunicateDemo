package com.brotherd.androidhtmlcommunicatedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.widget.X5WebView;

public class VideoActivity extends AppCompatActivity {

    private X5WebView webView;

    private static final String TAG = "VideoActivity";

    public static void launch(Context context) {
        Intent starter = new Intent(context, VideoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        webView = (X5WebView) findViewById(R.id.x5_webview);
        webView.loadUrl("file:///android_asset/fullscreenVideo.html");
        if (webView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

}
