package com.ovu.lido.ui;

import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ClauseDetailInfo;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 协议详情
 */
public class ClauseDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.clause_detail_wv)
    WebView clause_detail_wv;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_clause_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        clause_detail_wv.getSettings().setJavaScriptEnabled(true);
        clause_detail_wv.getSettings().setDefaultTextEncodingName("utf-8");
        clause_detail_wv.getSettings().setSupportZoom(false);
    }

    @Override
    protected void loadData() {
        super.loadData();
        String id = getIntent().getStringExtra("id");
        OkHttpUtils.post()
                .url(Constant.CLAUSE_DETAIL_URL)
                .addParams("id",id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ClauseDetailInfo info = GsonUtil.GsonToBean(response, ClauseDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            clause_detail_wv.loadData(info.getData().getAgreementNode(),"text/html; charset=UTF-8", null);
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
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
