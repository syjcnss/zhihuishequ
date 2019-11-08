package com.ovu.lido.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.AnnouncemenDetailInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * 小区公告详情
 */
public class AnnouncementDetailsActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.clause_wv)
    WebView clause_wv;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_announcement_details;
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void initView() {
        super.initView();
        clause_wv.getSettings().setJavaScriptEnabled(true);
        clause_wv.getSettings().setDefaultTextEncodingName("utf-8");
        clause_wv.getSettings().setSupportZoom(false);
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        String info_id = getIntent().getStringExtra("info_id");

        OkHttpUtils.post()
                .url(Constant.QUERY_INFO_DETAIL)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("info_id", info_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            AnnouncemenDetailInfo info = GsonUtil.GsonToBean(response, AnnouncemenDetailInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)) {
                                title_tv.setText(info.getTitle());
                                clause_wv.loadData(info.getContent(), "text/html; charset=UTF-8", null);
                            } else {

                            }
                        }
                    }
                });
    }

}
