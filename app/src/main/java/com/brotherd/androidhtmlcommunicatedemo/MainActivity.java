package com.brotherd.androidhtmlcommunicatedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.brotherd.androidhtmlcommunicatedemo.activity.BaseKnowLedgeActivity;
import com.brotherd.androidhtmlcommunicatedemo.activity.TbsWebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_base)
    Button btnBase;
    @BindView(R.id.btn_more)
    Button btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_base, R.id.btn_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_base:
                BaseKnowLedgeActivity.launch(this);
                break;
            case R.id.btn_more:
                TbsWebViewActivity.launch(this);
                break;
        }
    }
}
