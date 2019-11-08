package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.OrderLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.OrderInfo;
import com.ovu.lido.bean.TypeInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.special_product_tv)
    TextView special_product_tv;
    @BindView(R.id.community_service_tv)
    TextView community_service_tv;
    @BindView(R.id.status_tab)
    TabLayout status_tab;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.order_lv)
    ListView order_lv;
    @BindView(R.id.list_empty)
    TextView list_empty;

    private List<OrderInfo.DataBeanX.DataBean> dataBeanList = new ArrayList<>();
    private String mType = "2";//1:维修服务 2:特供商品
    private String mStatus;//0:待付款 1:待收货 2:已完成 3:已失效
    private int mPageNo = 0;//页码
    private int mTotalCount;
    private OrderLvAdapter mOrderLvAdapter;
    private Dialog loadingDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mOrderLvAdapter = new OrderLvAdapter(mContext, R.layout.order_lv_item, dataBeanList);
        order_lv.setAdapter(mOrderLvAdapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        loadingDialog.show();
        getStatusData();
    }

    /**
     * 获取状态tab数据
     */
    private void getStatusData() {
        Map<String, String> map = new HashMap<>();
        map.put("type_group_code", "o_p_status");//订单状态
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.TYPE_LIST)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "订单状态-onSuccess: " + response);
                        String errorMsg = getErrorMsg(response.body());
                        String errorCode = getErrorCode(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<TypeInfo>() {
                            }.getType();
                            TypeInfo typeInfo = new Gson().fromJson(response.body(), type);
                            setTabLayout(typeInfo);

                            //刷新适配器
                            mOrderLvAdapter.notifyDataSetChanged();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    private void setTabLayout(TypeInfo typeInfo) {
        status_tab.removeAllTabs();

        List<TypeInfo.DataBean> data = typeInfo.getData();
        status_tab.addTab(status_tab.newTab().setText("全部").setTag(""));
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                TypeInfo.DataBean info = data.get(i);
                status_tab.addTab(status_tab.newTab().setText(info.getTypename()).setTag(info.getTypecode()));
            }

            ViewHelper.setTabWidth(status_tab, AppUtil.dip2px(mContext, 35));

            int tabCount = status_tab.getTabCount();
            if (tabCount > 1) {
                status_tab.getChildAt(0).requestFocus();
            }
        }
    }

    /**
     * 获取订单列表数据
     */
    private void getOrderData() {
        Map<String, String> map = new HashMap<>();
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("type", mType);//1:维修服务 2:特供商品
        if (!TextUtils.isEmpty(mStatus)) {
            map.put("status", mStatus);//0:待付款 1:待收货 2:已完成 3:已取消
        }
        map.put("page_no", String.valueOf(mPageNo));
        map.put("page_size", "20");
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.GET_ORDER_LIST)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "订单列表-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);
                        String errorMsg = getErrorMsg(response.body());
                        String errorCode = getErrorCode(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<OrderInfo>() {
                            }.getType();
                            OrderInfo orderInfo = new Gson().fromJson(response.body(), type);
                            if (mPageNo == 0) {
                                dataBeanList.clear();
                            }
                            mTotalCount = orderInfo.getData().getTotalCount();
                            List<OrderInfo.DataBeanX.DataBean> data = orderInfo.getData().getData();
                            dataBeanList.addAll(data);
                            //刷新适配器
                            mOrderLvAdapter.notifyDataSetChanged();

                            if (mOrderLvAdapter.getCount() == 0) {
                                list_empty.setVisibility(View.VISIBLE);
                            } else {
                                list_empty.setVisibility(View.GONE);
                            }
                        } else {
                            showToast(errorMsg);
                        }

                    }
                });
    }

    @Override
    protected void setListener() {
        super.setListener();
        status_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPageNo = 0;
                mStatus = (String) tab.getTag();
                loadingDialog.show();
                getOrderData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPageNo = 0;
                getOrderData();
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mOrderLvAdapter.getCount() < mTotalCount) {
                    mPageNo++;
                    getOrderData();
                } else {
                    Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                    refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
                    refreshLayout.finishLoadMore();
                }
            }
        });

        order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("mOrderProductId",dataBeanList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.special_product_tv, R.id.community_service_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.special_product_tv:
                mType = "2";
                special_product_tv.setTextColor(getResources().getColor(R.color.black));
                community_service_tv.setTextColor(getResources().getColor(R.color.color_ff333333));
                special_product_tv.setTextSize(22);
                community_service_tv.setTextSize(17);
                getOrderData();
                break;
            case R.id.community_service_tv:
                mType = "1";
                special_product_tv.setTextColor(getResources().getColor(R.color.color_ff333333));
                community_service_tv.setTextColor(getResources().getColor(R.color.black));
                special_product_tv.setTextSize(17);
                community_service_tv.setTextSize(22);
                getOrderData();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.PRODUCT_BUY_SUCCESS){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(loadingDialog);
        EventBus.getDefault().unregister(this);
    }
}
