package com.ovu.lido.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.MyWorkOrderLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MyWorkOrderInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 报事报修--我的工单
 */
public class MyWorkOrderActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.message_group)
    RadioGroup message_group;
    @BindView(R.id.message_list)
    ListView message_list;

    private String order_state = "1";
    private List<MyWorkOrderInfo.DataBeanX.DataBean> myWorkOrderInfos = new ArrayList<>();
    private MyWorkOrderLvAdapter mMyWorkOrderLvAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_work_order;
    }

    @Override
    protected void initView() {
        super.initView();
        message_group.check(R.id.message_type_0);
        mMyWorkOrderLvAdapter = new MyWorkOrderLvAdapter(mContext, myWorkOrderInfos);
        message_list.setAdapter(mMyWorkOrderLvAdapter);
        getWorkOrderData();
    }

    @Override
    protected void setListener() {
        super.setListener();
        message_group.setOnCheckedChangeListener(this);
        message_list.setOnItemClickListener(this);
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.message_type_0://待处理
                order_state = "1";
                break;
            case R.id.message_type_1://处理中
                order_state = "2";
                break;
            case R.id.message_type_2://待评价
                order_state = "3";
                break;
            case R.id.message_type_3://已评价
                order_state = "4";
                break;
        }
        getWorkOrderData();
    }

    private void getWorkOrderData() {
        myWorkOrderInfos.clear();
        OkHttpUtils.post()
                .url(Constant.GET_WORK_ORDER_LIST)
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("order_state", order_state)
                .addParams("start", "0")
                .addParams("count", "100")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "工单列表数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            MyWorkOrderInfo info = GsonUtil.GsonToBean(response, MyWorkOrderInfo.class);
                            myWorkOrderInfos.addAll(info.getData().getData());
                            mMyWorkOrderLvAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, MyWorkOrderDetailActivity.class);
        MyWorkOrderInfo.DataBeanX.DataBean bean = myWorkOrderInfos.get(position);
        intent.putExtra("order_id", bean.getId());
        intent.putExtra("workStatus", order_state);
        intent.putExtra("reportTime", bean.getReportTime());
        intent.putExtra("finishTime", bean.getFinishTime());
        intent.putExtra("evaluateComment",bean.getEvaluateComment());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == 1234) {
            getWorkOrderData();
        }
    }
}
