package com.ovu.lido.ui;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.SatisfactionSurveyHistoryLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.SatisfactionSurveyHistoryInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 -- 满意度调查 -- 历史
 */
public class SatisfactionSurveyHistoryActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.satisfaction_survey_history_lv)
    ListView satisfaction_survey_history_lv;

    private List<SatisfactionSurveyHistoryInfo.InfoHistorylistBean> historyInfos = new ArrayList<>();
    private SatisfactionSurveyHistoryLvAdapter mHistoryLvAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_satisfaction_survey_history;
    }

    @Override
    protected void initView() {
        super.initView();
        mHistoryLvAdapter = new SatisfactionSurveyHistoryLvAdapter(mContext, historyInfos);
        satisfaction_survey_history_lv.setAdapter(mHistoryLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @OnClick({R.id.back_iv})
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
        OkHttpUtils.get()
                .url(Constant.VOTE_HISTORY_URL)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("start","0")
                .addParams("count","100")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            SatisfactionSurveyHistoryInfo info = GsonUtil.GsonToBean(response, SatisfactionSurveyHistoryInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                historyInfos.addAll(info.getInfo_historylist());
                                mHistoryLvAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }
}
