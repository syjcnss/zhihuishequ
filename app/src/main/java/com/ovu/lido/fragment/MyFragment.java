package com.ovu.lido.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseFragment;
import com.ovu.lido.bean.MyPersonalBean;
import com.ovu.lido.ui.AboutUsActivity;
import com.ovu.lido.ui.AccountManagerActivity;
import com.ovu.lido.ui.ChangeHousingActivity;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.ui.FeedBackActivity;
import com.ovu.lido.ui.MessageActivity;
import com.ovu.lido.ui.MyOrderActivity;
import com.ovu.lido.ui.MyWalletActivity;
import com.ovu.lido.ui.MyWorkOrderActivity;
import com.ovu.lido.ui.PersonalInfoActivity;
import com.ovu.lido.ui.PersonalInformtionActivity;
import com.ovu.lido.ui.ReceiveAddressActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.JPushHelper;
import com.ovu.lido.util.Logger;
import com.ovu.lido.widgets.CommonAction;
import com.ovu.lido.widgets.LoadProgressDialog;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 我的
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.title_bar_layout)
    RelativeLayout title_bar_layout;
    @BindView(R.id.icon_iv)
    ImageView icon_iv;//头像
    @BindView(R.id.human_name_tv)
    TextView human_name_tv;//人名
    @BindView(R.id.room_no_tv)
    TextView room_no_tv;//房间号

    private String nick_name;
    private String human_name;
    private String icon;
    private Dialog mDialog;
    private MyPersonalBean bean;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_layout).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(getActivity());

    }

    @OnClick({R.id.message_iv, R.id.human_info_ll, R.id.child_account_cv, R.id.choose_village_cv,
            R.id.repair_order_tv, R.id.order_tv, R.id.wallet_tv, R.id.address_tv,
            R.id.feedback_tv, R.id.about_tv})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.message_iv://我的消息
                intent.setClass(mContext, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.human_info_ll://个人资料
                intent.setClass(mContext, PersonalInfoActivity.class);
                intent.putExtra("MyPersonalBean",bean);
                startActivity(intent);
                break;
            case R.id.child_account_cv://子账户管理
                intent.setClass(mContext, AccountManagerActivity.class);
                intent.putExtra("nick_name", nick_name);
                intent.putExtra("human_name", human_name);
                intent.putExtra("icon", icon);
                startActivity(intent);
                break;
            case R.id.choose_village_cv://多小区切换
                intent.setClass(mContext, ChangeHousingActivity.class);
                startActivity(intent);
                break;
            case R.id.repair_order_tv://我的工单
                intent.setClass(mContext, MyWorkOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.order_tv://我的订单
                intent.setClass(mContext, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.wallet_tv://我的钱包
                intent.setClass(mContext, MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.address_tv://收货地址
//                intent.setClass(mContext, MyAddressActivity.class);
                intent.putExtra("mAddressType",1);
                intent.setClass(mContext, ReceiveAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback_tv://投诉建议
                intent.setClass(mContext, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.about_tv://关于我们
                intent.setClass(mContext, AboutUsActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }


    /**
     * 获取个人信息
     */
    public void getData() {
        mDialog.show();
        OkGo.<String>post(HttpAddress.QUERY_PERSONAL_INFO)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);
                        Logger.i(TAG, "onSuccess: " + response.body());
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<MyPersonalBean>() {
                            }.getType();
                            bean = new Gson().fromJson(response.body(), type);
                            nick_name = bean.getNick_name();
                            human_name = bean.getHuman_name();
                            icon = bean.getIcon_url();
                            Glide.with(mContext)
                                    .load(Constant.IMG_CONFIG + icon)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.default_icon)
                                            .error(R.drawable.default_icon)
                                            .transform(new GlideCircleTransform(mContext)))
                                    .into(icon_iv);
                            human_name_tv.setText(nick_name);
                            room_no_tv.setText(bean.getCommunity_name() + " | " + bean.getRoom_no());
                        } else {
                            showToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);

    }
}
