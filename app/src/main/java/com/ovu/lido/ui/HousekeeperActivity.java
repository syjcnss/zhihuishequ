package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.IntimateStewardInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HousekeeperActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.housekeeper_tel_tv)
    TextView housekeeper_tel_tv;
    @BindView(R.id.housekeeper_pic_iv)
    ImageView housekeeper_pic_iv;
    @BindView(R.id.housekeeper_name_tv)
    TextView housekeeper_name_tv;
    @BindView(R.id.percentage_tv)
    TextView percentage_tv;
    @BindView(R.id.work_time_tv)
    TextView work_time_tv;
    @BindView(R.id.agree_type_ll)
    LinearLayout agree_type_ll;
    @BindView(R.id.housekeeper_intro_tv)
    TextView housekeeper_intro_tv;
    private String mIsAgree;
    private String mSatisfactionStates;
    private int mHousekeeperId;
    private Dialog loadingDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_housekeeper;

    }

    @Override
    protected void initView() {
        super.initView();
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
    }

    @Override
    protected void loadData() {
        super.loadData();
        loadingDialog.show();
        Map<String,String> map = new HashMap<>();
        OkGo.<String>post(Constant.INTIMATE_STEWARD_URL)
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("params",ViewHelper.getParams(map))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<IntimateStewardInfo>() {
                            }.getType();
                            IntimateStewardInfo info = new Gson().fromJson(response.body(), type);
                            setHousekeeper(info);
                        } else {
                            showToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }
                });
    }

    private void setHousekeeper(IntimateStewardInfo info) {
        final IntimateStewardInfo.DataBean data = info.getData();
        if (data != null) {
            housekeeper_tel_tv.setText(data.getHousekeeper_tel());
            housekeeper_tel_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHelper.toDialView(mContext,data.getHousekeeper_tel());
                }
            });

            Glide.with(mContext)
                    .load(data.getHousekeeper_pic())
                    .apply(new RequestOptions().placeholder(R.drawable.default_head)
                            .error(R.drawable.default_head)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(housekeeper_pic_iv);//图片

            housekeeper_name_tv.setText(data.getHousekeeper_name());//姓名
            percentage_tv.setText("满意度" + data.getPercentageSatisfaction());//满意度
            work_time_tv.setText(data.getWorkTime());//工作时间

            mHousekeeperId = data.getHousekeeper_id();
            mIsAgree = data.getIsAgree();
            int childCount = agree_type_ll.getChildCount();
            for (int i = 0; i < childCount; i++) {
                TextView satisfaction_btn = (TextView) agree_type_ll.getChildAt(i);
                satisfaction_btn.setSelected(Integer.parseInt(data.getAgreeType()) == (i+1));
                satisfaction_btn.setOnClickListener(onClickListener);
            }

            housekeeper_intro_tv.setText(data.getHousekeeper_intro());//简介
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (TextUtils.equals(mIsAgree,"1")){
                Toast.makeText(mContext,"本月已评价",Toast.LENGTH_SHORT).show();
                return;
            }else {
                switch (v.getId()) {
                    case R.id.good_tv:
                        mSatisfactionStates = "1";
                        break;
                    case R.id.soso_tv:
                        mSatisfactionStates = "2";
                        break;
                    case R.id.bad_tv:
                        mSatisfactionStates = "3";
                        break;
                }

                toEvaluate();
            }
        }
    };

    @OnClick({R.id.back_iv,R.id.save_msg_iv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_msg_iv:
                Intent intent = new Intent(mContext, LeaveCommentsActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 去评价
     *
     */
    private void toEvaluate() {
        OkGo.<String>post(Constant.EVALUATE_URL)
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("housekeeper_id", String.valueOf(mHousekeeperId))
                .params("satisfaction_states", mSatisfactionStates)
                .params("isAgree", mIsAgree)
                .params("token", AppPreference.I().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            showShortToast("评分成功");
                            loadData();
                        } else {
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

}
