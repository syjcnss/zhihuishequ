package com.ovu.lido.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

public class TestActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView content_tv;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
            }
            content_tv.setText("Title : " + title + "  " + "Content : " + content);
        }
    }
}
