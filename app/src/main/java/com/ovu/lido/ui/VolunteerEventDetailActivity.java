package com.ovu.lido.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.VolunteerEventDetailLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ListViewForScrollView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 在线社区 -- 服务团队 -- 志愿者活动详情
 */
public class VolunteerEventDetailActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.content_tv)
    TextView content_tv;

    @BindView(R.id.event_detail_lv)
    ListViewForScrollView event_detail_lv;
    private String[] urls;
    private VolunteerEventDetailLvAdapter mAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_volunteer_event_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        event_detail_lv.setFocusable(false);
    }

    @Override
    protected void loadData() {
        super.loadData();
        String name = getIntent().getStringExtra("name");
        String time = getIntent().getStringExtra("time");
        String content = getIntent().getStringExtra("content");
        String imgUrl = getIntent().getStringExtra("imgUrl");

        name_tv.setText(name);
        time_tv.setText(ViewHelper.getDisplayData(time));
        content_tv.setText(content);

        if (!TextUtils.isEmpty(imgUrl)) {
            urls = imgUrl.split(",");
            for (int i = 0; i < urls.length; i++) {
                Log.i(TAG, "urls: " + urls[i]);

            }
            mAdapter = new VolunteerEventDetailLvAdapter(mContext, urls);
            event_detail_lv.setAdapter(mAdapter);
        }
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
