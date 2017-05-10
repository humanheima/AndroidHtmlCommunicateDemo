package com.brotherd.androidhtmlcommunicatedemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.util.ScreenUtil;
import com.brotherd.androidhtmlcommunicatedemo.util.WebAppInterface;

/**
 * Created by dumingwei on 2017/5/9.
 */
public class ProgressWebView extends LinearLayout {

    private static final String TAG = "ProgressWebView";
    private ProgressBar progressBar;
    private WebView webView;
    private int height;
    private ViewGroup.LayoutParams params;
    private boolean encountError;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_view, this);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        webView = (WebView) view.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                height = ScreenUtil.dpToPx(((Activity) context), 2);
                params = progressBar.getLayoutParams();
                params.height = height;
                progressBar.setLayoutParams(params);
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                height = progressBar.getHeight();
                params = progressBar.getLayoutParams();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        params.height = (int) (height - height * value);
                        progressBar.setLayoutParams(params);
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressBar.setVisibility(INVISIBLE);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //不使用缓存直接从网络加载
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //下面两句话允许网页弹框
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(TAG, "onReceivedTitle: " + title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.e(TAG, "onProgressChanged: newProgress=" + newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        webView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack() && !encountError) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void addJavascriptInterface(WebAppInterface object, String name) {
        webView.addJavascriptInterface(object, name);
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

}
