package com.brotherd.androidhtmlcommunicatedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brotherd.androidhtmlcommunicatedemo.R;
import com.brotherd.androidhtmlcommunicatedemo.widget.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseKnowLedgeActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    ProgressWebView webview;

    public static void launch(Context context) {
        Intent starter = new Intent(context, BaseKnowLedgeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_know_ledge);
        ButterKnife.bind(this);
        webview.loadUrl("http://www.hao123.com");
    }
}
