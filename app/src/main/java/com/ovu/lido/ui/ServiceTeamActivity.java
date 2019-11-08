package com.ovu.lido.ui;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.ServiceTeamLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ServiceTeamInfo;
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
 * 服务团队 -- 更多
 */
public class ServiceTeamActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.service_team_lv)
    ListView service_team_lv;

    private List<ServiceTeamInfo.InfoListBean> teamInfos = new ArrayList<>();
    private ServiceTeamLvAdapter mTeamLvAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_service_team;
    }

    @Override
    protected void initView() {
        super.initView();
        mTeamLvAdapter = new ServiceTeamLvAdapter(mContext, teamInfos);
        service_team_lv.setAdapter(mTeamLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

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
        String query_type = getIntent().getStringExtra("query_type");
        switch (query_type){
            case "for_id":
                int info_id = getIntent().getIntExtra("info_id", 0);
                queryTeamsForId(info_id);
                break;
            case "for_all":
                queryTeamsForAll();
                break;
        }
    }

    private void queryTeamsForId(int info_id) {
        OkHttpUtils.post()
                .url(Constant.QUERY_SERVICE_TEAM_URL)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("start","0")
                .addParams("count","100")
                .addParams("info_id", String.valueOf(info_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "服务团队数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            ServiceTeamInfo info = GsonUtil.GsonToBean(response, ServiceTeamInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                teamInfos.addAll(info.getInfo_list());
                                mTeamLvAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    private void queryTeamsForAll() {
        OkHttpUtils.post()
                .url(Constant.QUERY_SERVICE_TEAM_URL)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
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
                        Log.i(TAG, "服务团队数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            ServiceTeamInfo info = GsonUtil.GsonToBean(response, ServiceTeamInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                teamInfos.addAll(info.getInfo_list());
                                mTeamLvAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }



}
