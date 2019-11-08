package com.ovu.lido.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.AnnouncementListAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.AnnouncementInfo;
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
 * 在线社区 -- 通知公告
 */
public class AnnouncementActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,AdapterView.OnItemClickListener{
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.announcement_group)
    RadioGroup announcement_group;
    @BindView(R.id.message_list)
    ListView message_list;

    private List<AnnouncementInfo.InfoListBean> infoListBeans = new ArrayList<>();
    private String info_type_id = "04";
    private AnnouncementListAdapter mAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initView() {
        super.initView();
        announcement_group.check(R.id.message_type_0);
        mAdapter = new AnnouncementListAdapter(mContext, infoListBeans);
        message_list.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        announcement_group.setOnCheckedChangeListener(this);
        message_list.setOnItemClickListener(this);
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
        infoListBeans.clear();
        OkHttpUtils.post()
                .url(Constant.QUERY_INFO_LIST)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("info_type_id",info_type_id)
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
                        Log.i(TAG, "通知公告列表数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            AnnouncementInfo info = GsonUtil.GsonToBean(response, AnnouncementInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                infoListBeans.addAll(info.getInfo_list());
                                mAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.message_type_0:
                info_type_id = "04";
                break;
            case R.id.message_type_1:
                info_type_id = "0404";
                break;
            case R.id.message_type_2:
                info_type_id = "0403";
                break;
        }
        loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AnnouncementDetailsActivity.class);
        AnnouncementInfo.InfoListBean bean = infoListBeans.get(position);
        intent.putExtra("info_id", bean.getId());
        startActivity(intent);
    }


}
