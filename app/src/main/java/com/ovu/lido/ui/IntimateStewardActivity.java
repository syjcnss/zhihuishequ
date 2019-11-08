package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.IntimateStewardInfo;
import com.ovu.lido.bean.IsJoinOrCheckInfo;
import com.ovu.lido.bean.IsSubmitInfo;
import com.ovu.lido.bean.TipStateBean;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.CircleImageView;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 贴心管家--首页
 */
public class IntimateStewardActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.no_data_ll)
    LinearLayout no_data_ll;
    @BindView(R.id.no_data_tv)
    TextView no_data_tv;

    @BindView(R.id.icon_civ)
    CircleImageView icon_civ;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.tv_tip)
    TextView tv_tip;//  红点
    @BindView(R.id.satisfaction_tv)
    TextView satisfaction_tv;
    @BindView(R.id.phone_tv)
    TextView phone_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.introduce_tv)
    TextView introduce_tv;
    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.good_rb)
    RadioButton good_rb;
    @BindView(R.id.soso_rb)
    RadioButton soso_rb;
    @BindView(R.id.bad_rb)
    RadioButton bad_rb;
    @BindView(R.id.is_agree_btn)
    Button is_agree_btn;
    private int housekeeper_id;
    private Dialog mDialog;
    private String tipState = "";


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_intimate_steward;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
    }

    @Override
    protected void setListener() {
        super.setListener();


    }


    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.INTIMATE_STEWARD_URL)
                .addParams("resident_id", AppPreference.I().getString("resident_id", "213964"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "贴心管家首页数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            IntimateStewardInfo info = GsonUtil.GsonToBean(response, IntimateStewardInfo.class);
                            IntimateStewardInfo.DataBean data = info.getData();
                            housekeeper_id = data.getHousekeeper_id();
                            refreshView(data);
                        } else {
                            no_data_ll.setVisibility(View.VISIBLE);
                            no_data_tv.setText(errorMsg);
                        }
                    }
                });
    }

    /**
     * 刷新视图
     *
     * @param data 数据源
     */
    private void refreshView(IntimateStewardInfo.DataBean data) {
        Glide.with(mContext)
                .load(data.getHousekeeper_pic())
                .apply(new RequestOptions().placeholder(R.drawable.default_head)
                        .error(R.drawable.default_head)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(icon_civ);//图片
        name_tv.setText(data.getHousekeeper_name());//姓名
        phone_tv.setText(data.getHousekeeper_tel());//电话
        satisfaction_tv.setText("(满意度" + data.getPercentageSatisfaction() + ")");//满意度
        time_tv.setText("工作时间：" + data.getWorkTime());//工作时间
        introduce_tv.setText(data.getHousekeeper_intro());//简介
        String isAgree = data.getIsAgree();
        switch (isAgree) {
            case "0"://没有点
                radio_group.setOnCheckedChangeListener(listener);
                break;
            case "1"://已点
                is_agree_btn.setVisibility(View.VISIBLE);
                is_agree_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShortToast("本月已评价");
                    }
                });
                radio_group.setFocusable(false);
                String agreeType = data.getAgreeType();
                switch (agreeType) {
                    case "1"://满意
                        radio_group.check(R.id.good_rb);
                        break;
                    case "2"://一般
                        radio_group.check(R.id.soso_rb);
                        break;
                    case "3"://不满意
                        radio_group.check(R.id.bad_rb);
                        break;
                }
                break;
        }
    }

    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String satisfaction_states = "";
            switch (checkedId) {
                case R.id.good_rb:
                    satisfaction_states = "1";
                    break;
                case R.id.soso_rb:
                    satisfaction_states = "2";
                    break;
                case R.id.bad_rb:
                    satisfaction_states = "3";
                    break;
            }
            toEvaluate(satisfaction_states);
        }
    };

    /**
     * 去评价
     *
     * @param satisfaction_states
     */
    private void toEvaluate(String satisfaction_states) {
        OkHttpUtils.post()
                .url(Constant.EVALUATE_URL)
                .addParams("resident_id", AppPreference.I().getString("resident_id", AppPreference.I().getString("resident_id", "")))
                .addParams("housekeeper_id", String.valueOf(housekeeper_id))
                .addParams("satisfaction_states", satisfaction_states)
                .addParams("isAgree", "0")
                .addParams("token", AppPreference.I().getString("token", AppPreference.I().getString("token", "")))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "评价数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                            if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                                showShortToast("评分成功");
                            } else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.back_iv, R.id.phone_iv, R.id.decoration_ll, R.id.occupation_ll,
            R.id.convenient_people_ll, R.id.leave_a_message_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.phone_iv:
                String tel = phone_tv.getText().toString();
                ViewHelper.toDialView(mContext, tel);
                break;
            case R.id.decoration_ll: //装修管理
                //判断是否已经申请过
                doDecoration();
                break;
            case R.id.occupation_ll://入伙管理
                //判断是否已经入伙或验房
                doOccupation();
                break;
            case R.id.convenient_people_ll://便民电话
                startActivity(new Intent(mContext, ConveniencePhoneActivity.class));
                break;
            case R.id.leave_a_message_ll://留言
                Intent intent = new Intent(mContext, LeaveCommentsActivity.class);
                intent.putExtra("tipState", tipState);
                startActivity(intent);
                break;
        }
    }

    /**
     * 判断是否已经入伙或验房
     */
    private void doOccupation() {
        mDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.ISJOIN_OR_ISCHECK)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "判断是否已经入伙或验房: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        IsJoinOrCheckInfo info = GsonUtil.GsonToBean(response, IsJoinOrCheckInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {

                            int is_join = info.getData().getIs_join();
                            int is_check = info.getData().getIs_check();
                            String line = Constant.REQ_URL_PRE + info.getData().getLine();

                            Intent intent = new Intent(mContext, OccupationActivity.class);
                            intent.putExtra("is_join", is_join);
                            intent.putExtra("is_check", is_check);
                            intent.putExtra("line", line);
                            startActivity(intent);

                        } else {
                            ToastUtil.show(mContext, info.getErrorMsg());
                        }

                    }
                });
    }

    /**
     * 判断是否已经申请
     */
    private void doDecoration() {
        mDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.IS_SUBMIT_DECORATION)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "判断是否已经申请过: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        IsSubmitInfo info = GsonUtil.GsonToBean(response, IsSubmitInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            String status = info.getData().getStatus();//1.提交过  0.没有提交
                            if (TextUtils.equals(status, "1")) {
                                startActivity(new Intent(mContext, DecorationHistoryActivity.class));
                            } else if (TextUtils.equals(status, "0")) {
                                startActivity(new Intent(mContext, DecorationActivity.class));
                            }
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }


    @Override
    protected void onResume() {
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
                            AppUtil.toLogin(IntimateStewardActivity.this);
                            return;
                        }
                        TipStateBean bean = GsonUtil.GsonToBean(response, TipStateBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (bean.getData().getIsRepay().equals("1")) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tipState = "1";
                            } else {
                                tv_tip.setVisibility(View.GONE);
                                tipState = "";
                            }
                        } else {
                            tv_tip.setVisibility(View.GONE);
                            tipState = "";
                        }
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
