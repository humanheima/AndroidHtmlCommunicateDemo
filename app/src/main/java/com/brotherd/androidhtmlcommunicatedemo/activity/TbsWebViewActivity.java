package com.brotherd.androidhtmlcommunicatedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.util.WebAppInterface;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class TbsWebViewActivity extends AppCompatActivity {

    private static final String TAG = "TbsWebViewActivity";
    private WebView webView;
    Button btnInvokeJs;
    Button btnInvokeJsWithParams;
    //防止加载网页时调起系统浏览器
    private WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    };

    public static void launch(Context context) {
        Intent starter = new Intent(context, TbsWebViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs_web_view);
        webView = (WebView) findViewById(R.id.web_view);
        initWebView();
    }

    private void initWebView() {
        webView.setWebViewClient(client);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        //webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // webSetting.setSupportZoom(true);
        //webSetting.setBuiltInZoomControls(true);
        //webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        //webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl("file:///android_asset/Test.html");
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_invoke_js:
                webView.loadUrl("javascript:javacalljs()");
                break;
            case R.id.btn_invoke_js_with_params:
                webView.loadUrl("javascript:javacalljswith('Android调用了JS的有参函数')");
                break;
        }
    }
}
