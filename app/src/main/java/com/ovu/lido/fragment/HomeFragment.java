package com.ovu.lido.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.EventContentVpAdapter;
import com.ovu.lido.adapter.SpecialProductRvAdapter;
import com.ovu.lido.base.BaseFragment;
import com.ovu.lido.bean.HomePageInfo;
import com.ovu.lido.ui.ActivitiesActivity;
import com.ovu.lido.ui.CommonWebActivity;
import com.ovu.lido.ui.CommunityAnnouncementActivity;
import com.ovu.lido.ui.CommunityServiceActivity;
import com.ovu.lido.ui.ConveniencePhoneActivity;
import com.ovu.lido.ui.FastEntryActivity;
import com.ovu.lido.ui.HousekeeperActivity;
import com.ovu.lido.ui.MessageActivity;
import com.ovu.lido.ui.NewsReportRepairActivity;
import com.ovu.lido.ui.OnlinePaymentActivity;
import com.ovu.lido.ui.ProductDetailActivity;
import com.ovu.lido.ui.SatisfactionSurveyActivity;
import com.ovu.lido.ui.VisitorEmpowerActivity;
import com.ovu.lido.ui.WelfareActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.LockManager;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.CardTransformer;
import com.ovu.lido.widgets.ConfirmDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.top_title)
    TextView top_title;
    @BindView(R.id.message_iv)
    ImageView message_iv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.ad_banner)
    BGABanner ad_banner;
    @BindView(R.id.head_lines)
    TextView head_lines;
    @BindView(R.id.housekeeper_cv)
    CardView housekeeper_cv;
    @BindView(R.id.housekeeper_pic_iv)
    ImageView housekeeper_pic_iv;
    @BindView(R.id.housekeeper_name_tv)
    TextView housekeeper_name;
    @BindView(R.id.work_time_tv)
    TextView work_time_tv;
    @BindView(R.id.percentage_tv)
    TextView percentage_tv;
    @BindView(R.id.housekeeper_tel_tv)
    TextView housekeeper_tel_tv;

    @BindView(R.id.fast_entry_ll)
    LinearLayout fast_entry_ll;

    @BindView(R.id.event_content_vp)
    ViewPager event_content_vp;

    @BindView(R.id.specical_product_ll)
    LinearLayout specical_product_ll;
    @BindView(R.id.special_product_rv)
    RecyclerView special_product_rv;

    private Integer[] resIds = {R.drawable.zhinengkaimen, R.drawable.baoshibaoxiu, R.drawable.zaixianjiaofei, R.drawable.bianmindianhua, R.drawable.fangkeyaoqing, R.drawable.manyidudiaocha};
    private String[] names = {"智能开门", "报事报修", "在线缴费", "便民电话", "访客邀请", "满意度调查"};

    private EventContentVpAdapter mEventContentVpAdapter;
    private LockManager lockManager;
    private Dialog loadingDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void initView() {
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        top_title.setText(AppPreference.I().getString("community_name",""));
        //功能区
        initFastEntryLayout();


    }

    /**
     * 初始化功能区快捷入口
     */
    private void initFastEntryLayout() {
        for (int i = 0; i < resIds.length; i++) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.fast_entry_item, fast_entry_ll, false);
            TextView fast_name_tv = view.findViewById(R.id.fast_name_tv);
            ImageView fast_icon_iv = view.findViewById(R.id.fast_icon_iv);
            fast_name_tv.setText(names[i]);
            fast_icon_iv.setImageResource(resIds[i]);
            view.setId(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    switch (id) {
                        case 0://智能开门
                            openDoor();
                            break;
                        case 1://报事报修
                            startActivity(new Intent(mContext, NewsReportRepairActivity.class));
                            break;
                        case 2://在线缴费
                            startActivity(new Intent(mContext, OnlinePaymentActivity.class));
                            break;
                        case 3://便民电话
                            startActivity(new Intent(mContext,ConveniencePhoneActivity.class));
                            break;
                        case 4://访客邀请
                            startActivity(new Intent(mContext,VisitorEmpowerActivity.class));
                            break;
                        case 5://满意度调查
                            startActivity(new Intent(mContext, SatisfactionSurveyActivity.class));
                            break;
                    }
                }
            });
            fast_entry_ll.addView(view);
        }

        //查看更多
        View view = LayoutInflater.from(mContext).inflate(R.layout.fast_entry_more, fast_entry_ll, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, FastEntryActivity.class));
            }
        });

        fast_entry_ll.addView(view);
    }

    private void openDoor() {
        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            lockManager = new LockManager(mContext);
            lockManager.showLockListDialog();
        } else {
            requestPermission(Constant.LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }


    @OnClick({R.id.message_iv, R.id.welfare_iv, R.id.head_lines, R.id.more_event_tv,
            R.id.ruodian_cv, R.id.jishui_cv, R.id.tumu_cv, R.id.more_service_tv,
            R.id.more_product_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_iv:
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
            case R.id.welfare_iv:
                startActivity(new Intent(mContext, WelfareActivity.class));
                break;
            case R.id.head_lines:
                startActivity(new Intent(mContext, CommunityAnnouncementActivity.class));
                break;
            case R.id.more_event_tv://社区活动--查看更多
                startActivity(new Intent(mContext, ActivitiesActivity.class));
                break;
            case R.id.ruodian_cv://强弱电维修
                startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","1").putExtra("service_name","强弱电维修"));
                break;
            case R.id.jishui_cv://给水排水维修
                startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","1").putExtra("service_name","给水排水维修"));
                break;
            case R.id.tumu_cv://土木维修
                startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","1").putExtra("service_name","土木维修"));
                break;
            case R.id.more_service_tv://社区服务--查看更多
                startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","1"));
                break;
            case R.id.more_product_tv://特供商品--查看更多
                startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","2"));
                break;

        }
    }


    @Override
    protected void loadData() {
        loadingDialog.show();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramMap.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(paramMap);
        OkGo.<String>post(Constant.GET_HOME_PAGE_INFO)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        refresh_layout.finishRefresh(true);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<HomePageInfo>() {
                            }.getType();
                            HomePageInfo info = new Gson().fromJson(response.body(), type);
                            setADBanner(info);//广告轮播
                            setHeadLine(info);//小区头条
                            setHouseKeeper(info);//管家
                            setCommunity(info);//社区活动
                            setSpecialProducts(info);//特供商品
                        } else {
                            showShortToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        if (mContext == null || mActivity.isFinishing()){
                            return;
                        }
                        refresh_layout.finishRefresh(true);
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }
                });
    }


    @Override
    protected void setListener() {
        super.setListener();
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    /**
     * 特供商品列表
     *
     * @param info
     */
    private void setSpecialProducts(HomePageInfo info) {
        final List<HomePageInfo.DataBean.SpecialProductsDataBean> specialProducts = info.getData().getSpecialProducts();
        if (specialProducts != null && !specialProducts.isEmpty()) {
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            special_product_rv.setLayoutManager(layoutManager);
            final SpecialProductRvAdapter specialProductRvAdapter = new SpecialProductRvAdapter(mContext, specialProducts);
            special_product_rv.setAdapter(specialProductRvAdapter);
            specialProductRvAdapter.setOnItemClickListener(new SpecialProductRvAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view) {
                    int position = special_product_rv.getChildAdapterPosition(view);
                    HomePageInfo.DataBean.SpecialProductsDataBean bean = specialProducts.get(position);
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("module_type","2");//类型码
                    intent.putExtra("product_id",bean.getId());//商品id
                    intent.putExtra("picture",bean.getPicture());
                    intent.putExtra("name",bean.getName());
                    intent.putExtra("price",bean.getPrice());
                    intent.putExtra("description",bean.getDescription());
                    startActivity(intent);
                }
            });

        }else {
            specical_product_ll.setVisibility(View.GONE);
        }
    }

    /**
     * 设置社区活动
     */
    private void setCommunity(HomePageInfo info) {
        List<HomePageInfo.DataBean.ActivitiesDataBean> activities = info.getData().getActivities();
        if (activities != null) {
            mEventContentVpAdapter = new EventContentVpAdapter(mContext, activities);
            event_content_vp.setAdapter(mEventContentVpAdapter);
            int count = mEventContentVpAdapter.getCount();
            if (count > 2) {
                event_content_vp.setCurrentItem(1);
            }
            event_content_vp.setPageMargin(11);
            event_content_vp.setOffscreenPageLimit(3);
            event_content_vp.setPageTransformer(false, new CardTransformer());
        }
    }

    /**
     * 设置管家
     */
    private void setHouseKeeper(HomePageInfo info) {
        HomePageInfo.DataBean.HousekeeperDataBean housekeeper = info.getData().getHousekeeper();
        if (housekeeper != null) {
            //管家头像
            Glide.with(mContext)
                    .load(housekeeper.getHousekeeper_pic())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.image_error)
                            .error(R.drawable.image_error)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(housekeeper_pic_iv);
            //管家名字
            housekeeper_name.setText(housekeeper.getHousekeeper_name());
            work_time_tv.setText(housekeeper.getWorkTime());
            percentage_tv.setText("满意度" + housekeeper.getPercentageSatisfaction());
            housekeeper_tel_tv.setText(housekeeper.getHousekeeper_tel());

            housekeeper_cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, HousekeeperActivity.class));
                }
            });

        }
    }

    /**
     * 设置小区头条滚动字幕
     */
    private void setHeadLine(HomePageInfo info) {
        String textStr = "暂无消息";
        List<HomePageInfo.DataBean.NoticeListDataBean> noticeList = info.getData().getNoticeList();
        StringBuffer stringBuffer = new StringBuffer();
        if (noticeList != null) {
            for (int i = 0; i < noticeList.size(); i++) {
                stringBuffer.append(noticeList.get(i).getTitle()).append("：").append(noticeList.get(i).getIntro()).append("\t");
            }
            textStr = stringBuffer.toString();
        }
        head_lines.setText(textStr);

    }

    /**
     * 设置顶部广告轮播
     *
     * @param info
     */
    private void setADBanner(HomePageInfo info) {
        final List<HomePageInfo.DataBean.ADInfoDataBean> adInfo = info.getData().getAdInfo();
        List<String> imgUrls = new ArrayList<>();
        if (adInfo != null) {
            for (int i = 0; i < adInfo.size(); i++) {
                imgUrls.add(adInfo.get(i).getImg());
            }

            ad_banner.setAutoPlayAble(imgUrls.size() > 1);
            ad_banner.setAdapter(new BGABanner.Adapter<CardView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, CardView itemView, @Nullable String model, int position) {
                    ImageView card_view = itemView.findViewById(R.id.image_view);
                    Glide.with(itemView.getContext())
                            .load(model)
                            .apply(new RequestOptions().placeholder(R.drawable.activity_image_error).error(R.drawable.activity_image_error).dontAnimate().centerCrop())
                            .into(card_view);
                }

            });
            ad_banner.setData(R.layout.ad_banner_item, imgUrls, null);
            ad_banner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                    HomePageInfo.DataBean.ADInfoDataBean bean = adInfo.get(position);
                    String ad_type = bean.getAd_type();
                    String link = bean.getLink();
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.LOCATION_PERMISSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED ) {//存储权限
                        // 权限请求成功
                        if (lockManager == null){
                            lockManager = new LockManager(mContext);
                        }
                        lockManager.showLockListDialog();
                    } else {
                        // 权限请求失败
                        if (shouldShowRequestPermissionRationale(permissions[0])) {// 权限申请失败，判断是否需要弹窗解释原因
                            ConfirmDialog dialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onConfirm() {
                                    //申请存储和位置权限
                                    requestPermission(Constant.LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
                                }
                            });
                            dialog.show();
                            dialog.setContentText("位置权限：用于蓝牙开门");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，开门功能才可正常使用");
                            dialog.setOkText("下一步");
                            dialog.setCancelVisible(View.GONE);
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        } else {// 权限申请失败，并且勾选不再提示，弹窗解释原因并引导进入设置界面设置权限
                            ConfirmDialog dialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                    Toast.makeText(mContext,"未取得您的位置信息使用权限，此功能无法使用。请前往应用权限设置打开权限",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onConfirm() {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                                    intent.setData(uri);
                                    //跳转到设置界面调用的是 startActivityForResult()
                                    startActivityForResult(intent, Constant.LOCATION_PERMISSION);
                                }

                            });
                            dialog.show();
                            dialog.setContentText("获取权限后\n应用不会读取您的个人信息\n请在 权限管理 中设置开启");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，开门功能才可正常使用");
                            dialog.setOkText("前往设置");
                            dialog.setCancelText("退出");
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        }
                    }

                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *  从 权限设置 返回：
         * 1、没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，所以 resultCode 总是为 RESULT_CANCEL
         * 2、还需要对权限进行判断，因为用户有可能没有授权就返回了！
         */
        switch (requestCode) {
            case Constant.LOCATION_PERMISSION:
                if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {//权限申请成功
                    //进入下一页
                    if (lockManager == null){
                        lockManager = new LockManager(mContext);
                    }
                    lockManager.showLockListDialog();
                } else {//权限申请失败
                    //申请存储和位置权限
                    requestPermission(Constant.LOCATION_PERMISSION,Manifest.permission.ACCESS_FINE_LOCATION);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (lockManager != null) {
            lockManager.onDestroy();
        }
        super.onDestroy();
    }
}
