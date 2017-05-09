package com.brotherd.androidhtmlcommunicatedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.util.WebAppInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseKnowLedgeActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.btn_invoke_js)
    Button btnInvokeJs;
    @BindView(R.id.btn_invoke_js_with_params)
    Button btnInvokeJsWithParams;

    private WebAppInterface webAppInterface;

    public static void launch(Context context) {
        Intent starter = new Intent(context, BaseKnowLedgeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_know_ledge);
        ButterKnife.bind(this);
        //设置html页面的跳转都在这个webView上跳转
        webview.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //下面两句话允许网页弹框
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setWebChromeClient(new WebChromeClient());
        webAppInterface=new WebAppInterface(this);
        webview.addJavascriptInterface(webAppInterface, "Android");
        webview.loadUrl("file:///android_asset/Test.html");
    }

    @OnClick({R.id.btn_invoke_js, R.id.btn_invoke_js_with_params})
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://处理返回键事件
                if (webview.canGoBack()) {
                    webview.goBack();//让WebView回退到上一个网页
                    return true;
                } else {//如果WebView不能回退
                    //提示应用是否退出程序
                    BaseKnowLedgeActivity.this.finish();
                }
            default:
                break;
        }
        return false;
    }
}
