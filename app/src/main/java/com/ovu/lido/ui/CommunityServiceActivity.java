package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.CommunityServiceRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CommunityServiceProductInfo;
import com.ovu.lido.bean.CommunityServiceTypeInfo;
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
 * 维修服务、特供商品 列表
 *
 * create by wangw on 2019/06/05
 */
public class CommunityServiceActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.search_et)
    EditText search_et;
    @BindView(R.id.service_tab_layout)
    TabLayout service_tab_layout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.community_service_rv)
    RecyclerView community_service_rv;
    private CommunityServiceRvAdapter mAdapter;
    private String mModuleType;//板块分类(1：维修服务  2:特供商品)
    private String mCategoryType;//商品分类
    private Dialog mLoadingDialog;
    private int mPageNo;

    private List<CommunityServiceProductInfo.DataBeanX.DataBean> productInfoList = new ArrayList<>();
    private int mTotalCount;
    private String mSearchName;//搜索关键字
    private String mServiceName;
    private int selectPos = 0;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_community_service;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mLoadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        community_service_rv.requestFocus();
        mAdapter = new CommunityServiceRvAdapter(mContext, productInfoList);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        community_service_rv.setLayoutManager(manager);
        community_service_rv.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mModuleType = getIntent().getStringExtra("module_type");
        mServiceName = getIntent().getStringExtra("service_name");
        search_et.setHint(TextUtils.equals(mModuleType, "1") ? "请输入维修项目名称" : "请输入商品名称");
        getTypeData();
    }

    @OnClick({R.id.back_iv,R.id.clean_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.clean_iv:
                search_et.setText("");
                break;
        }
    }

    @Override
    protected void setListener() {
        super.setListener();

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPageNo = 0;
                mSearchName = s.toString();
                getProductData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPageNo = 0;
                getProductData();
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mAdapter.getItemCount() < mTotalCount) {
                    mPageNo++;
                    getProductData();
                } else {
                    Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                    refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
                    refreshLayout.finishLoadMore();
                }
            }
        });

        service_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPageNo = 0;
                mCategoryType = (String) tab.getTag();
                getProductData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAdapter.setOnItemClickListener(new CommunityServiceRvAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                int position = community_service_rv.getChildAdapterPosition(view);
                CommunityServiceProductInfo.DataBeanX.DataBean bean = productInfoList.get(position);
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("module_type",mModuleType);//类型码
                intent.putExtra("product_id",bean.getId());//商品id
                intent.putExtra("picture",bean.getPicture());
                intent.putExtra("name",bean.getName());
                intent.putExtra("price",bean.getPrice());
                intent.putExtra("description",bean.getDescription());
                startActivity(intent);
            }
        });
    }

    /**
     * 获取类型下的产品数据
     */
    private void getProductData() {
        Map<String, String> map = new HashMap<>();
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("module_type", mModuleType);
        map.put("category_type", mCategoryType);
        map.put("page_no", String.valueOf(mPageNo));
        map.put("page_size", "20");
        map.put("name", mSearchName);
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.PRODUCT_LIST)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mLoadingDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "onSuccess : " + response.body());
                        LoadProgressDialog.closeDialog(mLoadingDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<CommunityServiceProductInfo>() {
                            }.getType();
                            CommunityServiceProductInfo productInfo = new Gson().fromJson(response.body(), type);
                            mTotalCount = productInfo.getData().getTotalCount();
                            List<CommunityServiceProductInfo.DataBeanX.DataBean> infoList = productInfo.getData().getData();
                            if (mPageNo == 0) {
                                productInfoList.clear();
                            }
                            productInfoList.addAll(infoList);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }


    /**
     * 获取类型
     */
    private void getTypeData() {
        mLoadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("type_group_code", "p_category_type_"+ mModuleType);//商品分类 p_category_type_ + 商品板块分类(例如 p_category_type_1维修服务, p_category_type_2特供商品)
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.TYPE_LIST)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "onSuccess : " + response.body());
                        LoadProgressDialog.closeDialog(mLoadingDialog);

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<TypeInfo>() {
                            }.getType();
                            TypeInfo typeInfo = new Gson().fromJson(response.body(), type);
                            setTabLayout(typeInfo);

                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 设置滚动标题
     */
    private void setTabLayout(TypeInfo typeInfo) {
        service_tab_layout.removeAllTabs();

        List<TypeInfo.DataBean> typeList = typeInfo.getData();
        if (typeList != null) {
            for (int i = 0; i < typeList.size(); i++) {
                String typename = typeList.get(i).getTypename();
                String typecode = typeList.get(i).getTypecode();

                service_tab_layout.addTab(service_tab_layout.newTab().setText(typename).setTag(typecode));
            }

            ViewHelper.setTabWidth(service_tab_layout, AppUtil.dip2px(mContext, 35));

            int tabCount = service_tab_layout.getTabCount();
            if (tabCount > 0) {
                if (!TextUtils.isEmpty(mServiceName)) {//如果需要选中tab名称不为空
                    for (int i = 0; i < tabCount; i++) {
                        CharSequence tabText = service_tab_layout.getTabAt(i).getText();
                        if (TextUtils.equals(mServiceName, tabText)){//如果名称相同
                            selectPos = i;
                        }
                    }
                }
                service_tab_layout.getTabAt(selectPos).select();
            }
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
        LoadProgressDialog.closeDialog(mLoadingDialog);
        EventBus.getDefault().unregister(this);
    }
}
