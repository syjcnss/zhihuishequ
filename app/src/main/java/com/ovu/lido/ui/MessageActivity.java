package com.ovu.lido.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.MessageListAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MessageInfo;
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

public class MessageActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener{
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.message_group)
    RadioGroup message_group;
    @BindView(R.id.message_list)
    ListView message_list;

    private List<MessageInfo.DataBean> messageInfos = new ArrayList<>();
    private String message_type = "0";
    private MessageListAdapter mAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void loadData() {
        super.loadData();
        messageInfos.clear();
        OkHttpUtils.post()
                .url(Constant.GET_MESSAGE_CORE)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("message_type",message_type)
                .addParams("start","0")
                .addParams("count","200")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "消息列表数据: " + response);
                        MessageInfo info = GsonUtil.GsonToBean(response, MessageInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            messageInfos.addAll(info.getData());
                            mAdapter.notifyDataSetChanged();
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        message_group.check(R.id.message_type_0);
        mAdapter = new MessageListAdapter(mContext, messageInfos);
        message_list.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {
        super.setListener();
        message_group.setOnCheckedChangeListener(this);
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.message_type_0://全部
                message_type = "0";
                break;
            case R.id.message_type_1://缴费状态
                message_type = "1";
                break;
            case R.id.message_type_2://通知信息
                message_type = "3";
                break;
            case R.id.message_type_3://活动信息
                message_type = "4";
                break;
        }
        loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, MessageDetailActivity.class);
        intent.putExtra("message_id", messageInfos.get(position).getId());
        startActivity(intent);
    }

}
