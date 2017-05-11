package com.brotherd.androidhtmlcommunicatedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.brotherd.androidhtmlcommunicatedemo.activity.BaseKnowLedgeActivity;
import com.brotherd.androidhtmlcommunicatedemo.activity.TbsWebViewActivity;
import com.tencent.smtt.sdk.TbsVideo;

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

    @OnClick({R.id.btn_base, R.id.btn_more, R.id.btn_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_base:
                BaseKnowLedgeActivity.launch(this);
                break;
            case R.id.btn_more:
                TbsWebViewActivity.launch(this);
                break;
            case R.id.btn_video:
                //VideoActivity.launch(this);
                if (TbsVideo.canUseTbsPlayer(this)) {
                    TbsVideo.openVideo(this, "http://domhttp.kksmg.com/2016/10/28/h264_450k_mp4_ea8d46604b83e5e2517dda9c5f969907_ncm.mp4");
                } else {
                    Toast.makeText(this, "can not play video", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
