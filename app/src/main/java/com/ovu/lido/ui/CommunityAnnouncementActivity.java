package com.ovu.lido.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.HeadLinesLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.AnnouncementListInfo;
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
 * 小区公告
 */
public class CommunityAnnouncementActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.headlines_lv)
    ListView headlines_lv;

    private List<AnnouncementListInfo.InfoListBean> headLinesInfos = new ArrayList<>();
    private HeadLinesLvAdapter mHeadLinesLvAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_community_announcement;
    }

    @Override
    protected void initView() {
        super.initView();
        mHeadLinesLvAdapter = new HeadLinesLvAdapter(mContext, headLinesInfos);
        headlines_lv.setAdapter(mHeadLinesLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        headlines_lv.setOnItemClickListener(this);

    }

    @Override
    protected void loadData() {
        super.loadData();
        OkHttpUtils.post()
                .url(Constant.QUERY_INFO_LIST)
                .addParams("info_type_id","01")
                .addParams("start","0")
                .addParams("count","100")
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("info_typename","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e );
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "小区公告列表数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            AnnouncementListInfo info = GsonUtil.GsonToBean(response, AnnouncementListInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                headLinesInfos.addAll(info.getInfo_list());
                                mHeadLinesLvAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(mContext,AnnouncementDetailsActivity.class).putExtra("info_id",headLinesInfos.get(position).getId()));
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
