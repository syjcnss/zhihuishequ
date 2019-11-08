package com.ovu.lido.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.VolunteerEventLvAdapter;
import com.ovu.lido.adapter.VolunteerTeamRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.VolunteerEventInfo;
import com.ovu.lido.bean.VolunteerTeamInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.widgets.ListViewForScrollView;
import com.ovu.lido.widgets.PageListScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 -- 志愿者服务
 */
public class VolunteerServiceActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.volunteer_sv)
    PageListScrollView volunteer_sv;
    @BindView(R.id.volunteer_team_rv)
    RecyclerView volunteer_team_rv;
    @BindView(R.id.volunteer_event_lv)
    ListViewForScrollView volunteer_event_lv;

    private List<VolunteerTeamInfo.InfosBean> volunteerTeamInfos = new ArrayList<>();
    private List<VolunteerEventInfo.InfoListBean> volunteerEventInfos = new ArrayList<>();
    private VolunteerTeamRvAdapter mVolunteerTeamRvAdapter;
    private VolunteerEventLvAdapter mVolunteerEventLvAdapter;
    private int pagesize = 5;
    private int currentpage = 0;
    private boolean judgeCanLoadMore = true;
    private int totalCount = 200;//设置本次加载的数据的总数


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_volunteer_service;
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        volunteer_team_rv.setLayoutManager(linearLayoutManager);
        mVolunteerTeamRvAdapter = new VolunteerTeamRvAdapter(mContext, volunteerTeamInfos);
        volunteer_team_rv.setAdapter(mVolunteerTeamRvAdapter);

        mVolunteerEventLvAdapter = new VolunteerEventLvAdapter(mContext, volunteerEventInfos);
        volunteer_event_lv.setAdapter(mVolunteerEventLvAdapter);

    }

    @Override
    protected void setListener() {
        super.setListener();
        volunteer_sv.setOnScrollToBottomListener(new PageListScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {
                //模拟进行数据的分页加载
                if (judgeCanLoadMore && isBottom && !volunteer_event_lv.isLoading()) {
                    volunteer_event_lv.startLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadEventData();
                            volunteer_event_lv.loadComplete();
                            initLoadMoreTagOp();
                        }
                    }, 2000);//模拟网络请求，延缓2秒钟
                }
            }
        });

        volunteer_event_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, VolunteerEventDetailActivity.class);
                VolunteerEventInfo.InfoListBean bean = volunteerEventInfos.get(position);
                intent.putExtra("name", bean.getActivity_name());
                intent.putExtra("content",bean.getActivity_content());
                intent.putExtra("time",bean.getCreate_time());
                intent.putExtra("imgUrl",bean.getActivity_url());
                startActivity(intent);
            }
        });
    }

    private void initLoadMoreTagOp() {
        if (totalCount == 0 || totalCount <= currentpage * pagesize) {//当前获取的数目大于等于总共的数目时表示数据加载完毕，禁止滑动
            judgeCanLoadMore = false;
            volunteer_event_lv.noMoreDataLoad();
            return;
        }
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        loadTeamData();
        loadEventData();
    }

    /**
     * 志愿者团队数据
     */
    private void loadTeamData() {
        OkHttpUtils.post()
                .url(Constant.QUERY_VOLUNTEER_TEAM)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("start","0")
                .addParams("count","100")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "志愿者团队数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            VolunteerTeamInfo info = GsonUtil.GsonToBean(response, VolunteerTeamInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                volunteerTeamInfos.addAll(info.getInfos());
                                mVolunteerTeamRvAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }

    /**
     * 志愿者活动数据
     */
    private void loadEventData() {
        int currentpageCount = currentpage * pagesize;
        if (currentpageCount >= totalCount) {
            volunteer_event_lv.noMoreDataLoad();
            return;
        }
        OkHttpUtils.post()
                .url(Constant.QUERY_VOLUNTEER_EVENT)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("start","0")
                .addParams("count","100")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "志愿者活动数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            VolunteerEventInfo info = GsonUtil.GsonToBean(response, VolunteerEventInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                volunteerEventInfos.addAll(info.getInfo_list());
                                mVolunteerEventLvAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
        currentpage++;
    }

}
