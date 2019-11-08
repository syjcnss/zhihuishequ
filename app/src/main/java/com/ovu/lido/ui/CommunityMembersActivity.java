package com.ovu.lido.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.MembersLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MembersInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 -- 社区党员
 */
public class CommunityMembersActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.members_lv)
    ListView members_lv;
    @BindView(R.id.no_data_ll)
    LinearLayout no_data_ll;

    private MembersLvAdapter mMembersLvAdapter;
    private List<MembersInfo.DataBean> membersInfos = new ArrayList<>();



    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_community_members;
    }

    @Override
    protected void initView() {
        super.initView();
        mMembersLvAdapter = new MembersLvAdapter(mContext, membersInfos);
        members_lv.setAdapter(mMembersLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mMembersLvAdapter.setPhoneBtnClickListener(new MembersLvAdapter.PhoneBtnClickListener() {
            @Override
            public void onBtnClick(View view, int position) {
                ViewHelper.toDialView(mContext,membersInfos.get(position).getMobile());
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        Map<String,Object> params = new HashMap<>();
        params.put("data",paramMap);
        OkHttpUtils.post()
                .url(Constant.QUERY_PARTY_MEMBER_URL)
                .addParams("params", GsonUtil.ToGson(params))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MembersInfo info = GsonUtil.GsonToBean(response, MembersInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            membersInfos.addAll(info.getData());
                            mMembersLvAdapter.notifyDataSetChanged();
                            showNoDataView();
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    private void showNoDataView() {
        int count = mMembersLvAdapter.getCount();
        no_data_ll.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
    }

    @OnClick (R.id.back_iv)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
