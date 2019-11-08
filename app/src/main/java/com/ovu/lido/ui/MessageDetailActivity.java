package com.ovu.lido.ui;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MessageDetailInfo;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.content_tv)
    TextView content_tv;

    private int message_id;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        message_id = getIntent().getIntExtra("message_id", -1);

    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }


    @Override
    protected void loadData() {
        super.loadData();
        OkHttpUtils.post()
                .url(Constant.GET_MESSAGE_CORE_DETAIL)
                .addParams("message_id", String.valueOf(message_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "消息详情数据: " + response);
                        MessageDetailInfo info = GsonUtil.GsonToBean(response, MessageDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            MessageDetailInfo.DataBean data = info.getData();
                            title_tv.setText(data.getMessage_type() == 1 ? "缴费状态" : data.getMessage_type() == 3 ? "通知信息" : data.getMessage_type() == 4 ? "活动信息" : "未知");
                            content_tv.setText(data.getMessage_content());
                        }
                    }
                });
    }


}
