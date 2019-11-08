package com.ovu.lido.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.ovu.lido.R;
import com.ovu.lido.adapter.EventContentVpAdapter;
import com.ovu.lido.adapter.NeighborhoodLvAdapter;
import com.ovu.lido.adapter.ServiceTeamRvAdapter;
import com.ovu.lido.base.BaseFragment;
import com.ovu.lido.bean.CommunityActivityInfo;
import com.ovu.lido.bean.HomeHeadLineInfo;
import com.ovu.lido.bean.NeiBean;
import com.ovu.lido.bean.ProgressRateInfo;
import com.ovu.lido.bean.ServiceTeamInfo;
import com.ovu.lido.bean.TipStateBean;
import com.ovu.lido.ui.ActivitiesActivity;
import com.ovu.lido.ui.CommonWebActivity;
import com.ovu.lido.ui.CommunityAnnouncementActivity;
import com.ovu.lido.ui.DynamicsActivity;
import com.ovu.lido.ui.IntimateStewardActivity;
import com.ovu.lido.ui.MessageActivity;
import com.ovu.lido.ui.MyWorkOrderActivity;
import com.ovu.lido.ui.MyWorkOrderDetailActivity;
import com.ovu.lido.ui.NewsReportRepairActivity;
import com.ovu.lido.ui.OnlinePaymentActivity;
import com.ovu.lido.ui.SatisfactionSurveyActivity;
import com.ovu.lido.ui.ServiceTeamActivity;
import com.ovu.lido.ui.VisitorEmpowerActivity;
import com.ovu.lido.ui.WelfareActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.LockManager;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.CardTransformer;
import com.ovu.lido.widgets.ListViewForScrollView;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.ovu.lido.widgets.PopDoorDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Call;

/**
 * 首页
 */
public class HomeNewFragment extends BaseFragment {
    @BindView(R.id.top_title)
    TextView top_title;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.neighborhood_lv)
    ListViewForScrollView neighborhood_lv;
    @BindView(R.id.neighborhood_more_tv)
    TextView neighborhood_more_tv;
    @BindView(R.id.menu_yellow)
    FloatingActionMenu menu_yellow;
    @BindView(R.id.fab1)
    FloatingActionButton fab1;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;
    @BindView(R.id.head_lines)
    TextView head_lines;
//    @BindView(R.id.service_team_rv)
//    RecyclerView service_team_rv;
    @BindView(R.id.event_content_vp)
    ViewPager event_content_vp;
    @BindView(R.id.repair_progress_ll)
    LinearLayout repair_progress_ll;
    @BindView(R.id.report_content_tv)
    TextView report_content_tv;
    @BindView(R.id.report_time_tv)
    TextView report_time_tv;
    @BindView(R.id.work_status_tv)
    TextView work_status_tv;
    @BindView(R.id.more_event_tv)
    TextView more_event_tv;
    @BindView(R.id.nei_smart)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.banner_main_accordion)
    BGABanner mAccordionBanner;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    private EventContentVpAdapter mEventContentVpAdapter;
    private NeighborhoodLvAdapter mNeighborhoodLvAdapter;

    private List<ProgressRateInfo.DataBeanX.DataBean> progressRateInfos = new ArrayList<>();
    private List<ServiceTeamInfo.InfoListBean> serviceTeamInfos = new ArrayList<>();
    private List<CommunityActivityInfo.DataBean> communityActDatas = new ArrayList<>();
    private List<NeiBean.InfoListBean> neighborhoodListInfos = new ArrayList<>();


    private OnMoreButtonClick onMoreButtonClick;//2、定义接口成员变量
    private OnMoreEventBtnClick onMoreEventBtnClick;
    private ServiceTeamRvAdapter mServiceTeamRvAdapter;

    private Dialog mDialog;
    private int mStart = 0; //开始条数
    private int mPagerCount = 10; //每页加载条数
    private int mCurrentPage = 0; //当前页数
    private List<HomeHeadLineInfo.AdGroupListSjBean.AdListBean> adListInfos = new ArrayList<>();
    private List<String> imgs = new ArrayList<>();
    private List<String> tips = new ArrayList<>();
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2018;
    private LockManager lockManager;


    public void setOnMoreEventBtnClick(OnMoreEventBtnClick onMoreEventBtnClick) {
        this.onMoreEventBtnClick = onMoreEventBtnClick;
    }

    //定义接口变量的set方法
    public void setOnMoreButtonClick(OnMoreButtonClick onMoreButtonClick) {
        this.onMoreButtonClick = onMoreButtonClick;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_new;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected void initView() {
        super.initView();
        top_title.setText(AppPreference.I().getString("community_name", "丽岛物业"));
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        menu_yellow.setClosedOnTouchOutside(true);
        mNeighborhoodLvAdapter = new NeighborhoodLvAdapter(mContext, neighborhoodListInfos);
        neighborhood_lv.setAdapter(mNeighborhoodLvAdapter);
//        event_content_vp.setPageMargin(10);//设置viewpage之间的间距
//        event_content_vp.setOffscreenPageLimit(2);//预加载3个
//        event_content_vp.setPageTransformer(true,new CardTransformer());
//        event_content_vp.setCurrentItem(0);
//        mEventContentVpAdapter = new EventContentVpAdapter(mContext, communityActDatas);
//        event_content_vp.setAdapter(mEventContentVpAdapter);


        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        service_team_rv.setLayoutManager(manager);
        mServiceTeamRvAdapter = new ServiceTeamRvAdapter(mContext, serviceTeamInfos);
//        service_team_rv.setAdapter(mServiceTeamRvAdapter);

//        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);


    }

    @Override
    protected void setListener() {
        super.setListener();
        neighborhood_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DynamicsActivity.class);
                intent.putExtra("postId", neighborhoodListInfos.get(position).getId());
                intent.putExtra("position", position + "");
                startActivity(intent);
            }
        });

//        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                loadNeighborData();
//            }
//        });

        mServiceTeamRvAdapter.setOnItemClickListener(new ServiceTeamRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = serviceTeamInfos.get(position).getId();
                Intent intent = new Intent(mContext, ServiceTeamActivity.class);
                intent.putExtra("info_id", id);
                intent.putExtra("query_type", "for_id");
                startActivity(intent);
            }
        });
        menu_yellow.setIconAnimated(false);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOpen();
                menu_yellow.close(true);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInvate();
            }
        });

    }


    @Override
    protected void loadData() {
        super.loadData();

        loadHeadLinesData();//加载小区头条数据
        loadProgressRateData();//加载报事进程数据
        loadCommunityActivityData();//加载社区活动数据
        loadServiceTeamData();//加载服务团队数据
        loadNeighborData();//加载邻里数据
    }

    /**
     * 服务团队数据
     */
    private void loadServiceTeamData() {
        OkHttpUtils.post()
                .url(Constant.QUERY_SERVICE_TEAM_URL)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("start", "0")
                .addParams("count", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "服务团队数据: " + response);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            ServiceTeamInfo info = GsonUtil.GsonToBean(response, ServiceTeamInfo.class);
                            serviceTeamInfos.addAll(info.getInfo_list());
                            mServiceTeamRvAdapter.notifyDataSetChanged();
                        } else {
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 社区活动数据
     */
    private void loadCommunityActivityData() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token", AppPreference.I().getString("token", ""));
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.QUERY_COMMUNITY_ACTIVITY_LIST)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "社区活动数据: " + response);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            CommunityActivityInfo info = GsonUtil.GsonToBean(response, CommunityActivityInfo.class);
                            communityActDatas.addAll(info.getData());
                            mEventContentVpAdapter.notifyDataSetChanged();
                        } else {
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 报事进程数据
     */
    private void loadProgressRateData() {
        OkHttpUtils.get()
                .url(Constant.GET_ORDER_LIST_INDEX)
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("start", "0")
                .addParams("count", "1")
                .addParams("order_state", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "报事进程数据: " + response);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            ProgressRateInfo info = GsonUtil.GsonToBean(response, ProgressRateInfo.class);
                            progressRateInfos.addAll(info.getData().getData());
                            mHandler.sendEmptyMessage(0);
                        } else {
//                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int progressRateInfosSize = progressRateInfos == null ? 0 : progressRateInfos.size();
                    if (progressRateInfosSize > 0) {
                        repair_progress_ll.setVisibility(View.VISIBLE);
                        String description = "";
                        String created = "";
                        String status = "";
                        for (int i = 0; i < progressRateInfosSize; i++) {
                            description = progressRateInfos.get(i).getDescription();
                            created = progressRateInfos.get(i).getReportTime();
                            int work_status = progressRateInfos.get(i).getWorkStatus();
                            switch (work_status) {
                                case 1://待处理
                                    status = "待处理";
                                    break;
                                case 2://处理中
                                    status = "处理中";
                                    break;
                                case 3://待评价
                                    status = "待评价";
                                    break;
                                case 4://已评价
                                    status = "已评价";
                                    break;
                            }

                        }
                        report_content_tv.setText(description);
                        report_time_tv.setText(ViewHelper.getDisplayData(created));
                        work_status_tv.setText(status);
                    }
                    break;
            }
        }
    };


    /**
     * 小区头条数据
     */
    private void loadHeadLinesData() {
        OkHttpUtils.get()
                .url(Constant.GET_HOME_LIST_URL)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "小区头条数据: " + response);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            HomeHeadLineInfo info = GsonUtil.GsonToBean(response, HomeHeadLineInfo.class);
                            refreshHeadLines(info);
                            setBannaView(info);

                        } else {
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 设置轮播广告视图
     *
     * @param info
     */
    private void setBannaView(HomeHeadLineInfo info) {
        List<HomeHeadLineInfo.AdGroupListSjBean> adGroupList = info.getAd_group_list_sj();
        if (adGroupList != null) {
            adListInfos.addAll(adGroupList.get(0).getAd_list());
        }
        for (HomeHeadLineInfo.AdGroupListSjBean.AdListBean adBean :
                adListInfos) {
            String img = adBean.getImg();
            if (!TextUtils.isEmpty(img)){
                imgs.add(img);
                tips.add(adBean.getAd_name());
            }
        }

        mAccordionBanner.setAutoPlayAble(imgs.size() > 1);
        mAccordionBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(itemView.getContext())
                        .load(model)
                        .apply(new RequestOptions().placeholder(R.drawable.activity_image_error).error(R.drawable.activity_image_error).dontAnimate().centerCrop())
                        .into(itemView);
            }
        });
        mAccordionBanner.setData(imgs, tips);
        mAccordionBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                HomeHeadLineInfo.AdGroupListSjBean.AdListBean bean = adListInfos.get(position);
                String ad_type = bean.getAd_type();
                String ad_content_id = bean.getAd_content_id();
                String ad_name = bean.getAd_name();
                String link = bean.getLink();

                // type 广告类型: 01商铺 02商品 03链接
//                if (TextUtils.equals(ad_type, "01")) {
//                    Intent data = new Intent(mContext, SupermarketDetailsActivity.class);
//                    data.putExtra("supermarket_id", ad_content_id);
//                    data.putExtra("supermarket_name", ad_name);
//                    mContext.startActivity(data);
//                } else if (TextUtils.equals(ad_type, "02")) {
//                    Intent data = new Intent(mContext, ProductDetailsAct.class);
//                    data.putExtra("product_id", ad_content_id);
//                    mContext.startActivity(data);
//                } else
                if (TextUtils.equals(ad_type, "03")) {
                    if (link.equals("0")) {
                        startActivity(new Intent(mContext, SatisfactionSurveyActivity.class));
                    } else {
                        Intent data = new Intent(mContext, CommonWebActivity.class);
                        data.putExtra("url", link);
                        data.putExtra("title", "详情");
                        startActivity(data);
                    }
                }

            }
        });
    }

    /**
     * 刷新小区头条视图
     *
     * @param info
     */
    private void refreshHeadLines(HomeHeadLineInfo info) {
        HomeHeadLineInfo.InfoMapBean info_map = info.getInfo_map();
        List<HomeHeadLineInfo.InfoMapBean._$01Bean> info_map_$01 = info_map.get_$01();
        int size = info_map_$01 == null ? 0 : info_map_$01.size();
        String s = "暂无消息";
        for (int i = 0; i < size; i++) {
            s += info_map_$01.get(i).getTitle() + ": " + info_map_$01.get(i).getIntro() + "\t";
        }
        head_lines.setText(s);
    }

    @OnClick({R.id.message_iv, R.id.welfare_iv, R.id.intimate_steward_ll,
            R.id.online_payment_ll, R.id.news_report_repair_ll,
            R.id.open_door_ll, R.id.head_lines, R.id.more_repair_tv, R.id.neighborhood_more_tv, R.id.vote_iv,
            R.id.more_event_tv, R.id.content_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_iv://消息
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
            case R.id.welfare_iv://福利
                startActivity(new Intent(mContext, WelfareActivity.class));
                break;
            case R.id.intimate_steward_ll://贴心管家
                startActivity(new Intent(mContext, IntimateStewardActivity.class));
                break;
            case R.id.online_payment_ll://线上缴费
                startActivity(new Intent(mContext, OnlinePaymentActivity.class));
                break;
            case R.id.news_report_repair_ll://报事报修
                startActivity(new Intent(mContext, NewsReportRepairActivity.class));
                break;
            case R.id.open_door_ll://在线社区
                final PopDoorDialog popDoorDialog = new PopDoorDialog(mContext);
                popDoorDialog.setOnDoorDialogClickListener(new PopDoorDialog.OnDoorDialogClickListener() {
                    @Override
                    public void click(int index) {
                        popDoorDialog.dismiss();
                        if (index == 0){
                             toOpen();
                        }else if (index == 1) {
                             toInvate();
                        }
                    }
                });
                popDoorDialog.show();
                break;
            case R.id.head_lines://小区头条
                startActivity(new Intent(mContext, CommunityAnnouncementActivity.class));
                break;
            case R.id.more_repair_tv://报事进程--更多
                startActivity(new Intent(mContext, MyWorkOrderActivity.class));
                break;
            case R.id.content_rl://报事进程--内容
                Intent intent = new Intent(mContext, MyWorkOrderDetailActivity.class);
                ProgressRateInfo.DataBeanX.DataBean bean = progressRateInfos.get(0);
                intent.putExtra("order_id", bean.getId());
                intent.putExtra("workStatus", String.valueOf(bean.getWorkStatus()));
                intent.putExtra("reportTime", bean.getReportTime());
                intent.putExtra("finishTime", bean.getFinishTime());
                startActivity(intent);
                break;
            case R.id.vote_iv://满意度调查
                startActivity(new Intent(mContext, SatisfactionSurveyActivity.class));
                break;
            case R.id.more_event_tv://社区活动 -- 更多
                //onMoreEventBtnClick.onClick(more_event_tv);
                startActivity(new Intent(mContext, ActivitiesActivity.class));
                break;
//            case R.id.more_service_tv://服务团队 -- 更多
//                startActivity(new Intent(mContext, ServiceTeamActivity.class).putExtra("query_type", "for_all"));
//                break;
            case R.id.neighborhood_more_tv://邻里--更多
                onMoreButtonClick.onClick(neighborhood_more_tv);
                break;
        }
    }


    //1、定义接口
    public interface OnMoreButtonClick {
        void onClick(View view);
    }

    public interface OnMoreEventBtnClick {
        void onClick(View view);
    }

    /**
     * 邻里数据
     *
     * @return
     */
    private void loadNeighborData() {
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.QUERY_NEIGHBOR_LIST_URL)
                .addParams("info_type_id", "02")
                .addParams("start", mStart + "")
                .addParams("count", mPagerCount + "")
                .addParams("info_typename", "")
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
//                        mRefreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "邻里数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            NeiBean info = GsonUtil.GsonToBean(response, NeiBean.class);
                            List<NeiBean.InfoListBean> info_list = info.getInfo_list();
//                            int size = info_list == null ? 0 : info_list.size();
//                            if (mStart == 0) {
//                                neighborhoodListInfos.clear();
//                            }
                            neighborhoodListInfos.addAll(info_list);
//                            if (size < mPagerCount) {
//                                mStart = mCurrentPage * mPagerCount + size;
//                            } else {
//                                mCurrentPage++;
//                                mStart = mCurrentPage * mPagerCount;
//                            }
                            mNeighborhoodLvAdapter.notifyDataSetChanged();
//                            showFooterView();

                        } else {
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getTipState();
    }

    /**
     * 改变红点状态
     */
    private void getTipState() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        OkHttpUtils.post()
                .url(Constant.TIP_STATE)
                .addParams("params", gson.toJson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            if (getActivity() != null)
                                AppUtil.toLogin(getActivity());
                            return;
                        }
                        TipStateBean bean = GsonUtil.GsonToBean(response, TipStateBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (bean.getData().getIsRepay().equals("1")) {
                                tv_tip.setVisibility(View.VISIBLE);
                            } else {
                                tv_tip.setVisibility(View.GONE);
                            }
                        } else {
                            tv_tip.setVisibility(View.GONE);
                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        if(lockManager!=null) {
            lockManager.onDestroy();
        }
        super.onDestroy();
    }


    private void toOpen(){
        //判断是否有权限
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            //判断是否需要 向用户解释，为什么要申请该权限
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(mContext, "用于蓝牙开门", Toast.LENGTH_SHORT).show();
            }
        } else {
            lockManager = new LockManager(mContext);
            lockManager.showLockListDialog();
        }
    }

    private void toInvate(){
        Intent intent = new Intent(mContext, VisitorEmpowerActivity.class);
        mContext.startActivity(intent);
    }
    /**
     * 判断数据是否加载完
     */
//    private void showFooterView() {
//        if (mStart % mPagerCount == 0) {
//            mRefreshLayout.setNoMoreData(false);
//            mRefreshLayout.finishLoadMore();
//        } else {
//            mRefreshLayout.setNoMoreData(true);
//            mRefreshLayout.finishLoadMore();
//        }
//    }
}
