package com.ovu.lido.ui;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;

import butterknife.BindView;

public class SupermarketDetailsActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.top_title)
    TextView top_title;
    private String supermarketId;
    private String supermarketName;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_supermarket_details;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void loadData() {
        super.loadData();
        supermarketId = getIntent().getStringExtra("supermarketId");
        supermarketName = getIntent().getStringExtra("supermarket_name");
        top_title.setText(supermarketName);
    }
}
