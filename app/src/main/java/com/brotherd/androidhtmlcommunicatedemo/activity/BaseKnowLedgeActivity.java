package com.brotherd.androidhtmlcommunicatedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.util.WebAppInterface;
import com.brotherd.androidhtmlcommunicatedemo.widget.ProgressWebView;

public class BaseKnowLedgeActivity extends AppCompatActivity {

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
                webview.loadUrl("javascript:javacalljswith('Android调用了JS的有参函数')");
                break;
        }
    }

}
