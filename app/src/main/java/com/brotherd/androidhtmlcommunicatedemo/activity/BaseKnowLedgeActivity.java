package com.brotherd.androidhtmlcommunicatedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.util.WebAppInterface;
import com.brotherd.androidhtmlcommunicatedemo.widget.ProgressWebView;

/**
 * Created by p_dmweidu on 2023/10/7
 * Desc: 用于演示Android和H5之间的交互
 * 参考链接：https://blog.csdn.net/leilifengxingmw/article/details/52135289
 */
public class BaseKnowLedgeActivity extends AppCompatActivity {

    private static final String TAG = "BaseKnowLedgeActivity";

    private ProgressWebView webview;

    public static void launch(Context context) {
        Intent starter = new Intent(context, BaseKnowLedgeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_know_ledge);
        webview = findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/Test.html");
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_invoke_js:
                webview.loadUrl("javascript:javacalljs()");
                break;
            case R.id.btn_invoke_js_with_params:
                webview.evaluateJavascript("javascript:javacalljswith(" + "'Android调用了JS的有参函数'" + ")",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                                Log.i(TAG, "onReceiveValue: " + value);
                            }
                        });

                //webview.loadUrl("javascript:javacalljswith('Android调用了JS的有参函数')");
                break;
        }
    }

}
