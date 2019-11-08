package com.ovu.lido.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ActivityDetailBean;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class ActivityDetailedActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.iv_content)
    ImageView mIv_content;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_address)
    TextView address;
    @BindView(R.id.tv_time)
    TextView date;
    @BindView(R.id.tv_apply_time)
    TextView apply_time;
    @BindView(R.id.tv_host)
    TextView host;
    @BindView(R.id.tv_intro)
    TextView intro;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.tv_apply_count)
    TextView apply_conut;
    @BindView(R.id.tv_join)
    TextView join;
    @BindView(R.id.good_state_ll)
    LinearLayout good_state_ll;
    @BindView(R.id.good_state_tv)
    TextView good_state_tv;
    @BindView(R.id.good_state_iv)
    ImageView good_state_iv;

    private String mActivityId;
    private ActivityDetailBean mBean;
    private boolean isJoin = false;
    private int likeType = -1;//0:表示取消点赞。1：表示点赞
    private boolean selected;//点赞按钮是否选中


    @Override
    protected int setLayoutId() {
        return R.layout.activity_activity_detailed;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mActivityId = intent.getStringExtra("activityId");
        good_state_iv.setSelected(false);
    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.tv_comment).setOnClickListener(this);
        join.setOnClickListener(this);
        good_state_ll.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token",""));
        map.put("resident_id", AppPreference.I().getString("resident_id",""));
        map.put("activity_id", mActivityId);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_DETAILED)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        mBean = GsonUtil.GsonToBean(response, ActivityDetailBean.class);
                        if (mBean.getErrorCode().equals("0000")) {
                            RequestOptions requestOptions = new RequestOptions()
                                    .placeholder(R.drawable.activity_image_error)
                                    .error(R.drawable.activity_image_error);
                            Glide.with(mContext)
                                    .load(mBean.getData().getActivity_img())
                                    .apply(requestOptions)
                                    .into(mIv_content);
//                            title.setText(mBean.getData().getActivity_name());
                            address.setText(mBean.getData().getAddress());
                            date.setText(mBean.getData().getBegin_time() + "--" + mBean.getData().getEnd_time());
                            apply_time.setText(mBean.getData().getEnroll_end_time());
                            host.setText(mBean.getData().getCreator_name());
                            intro.setText(mBean.getData().getIntroduce());
                            content.setText(mBean.getData().getActivity_content());
                            apply_conut.setText(mBean.getData().getEnrollCount() + "/" + mBean.getData().getEnroll_limit());
                            if (mBean.getData().isIs_end()) {
                                join.setText("已结束");
                                join.setSelected(false);
                                join.setEnabled(false);
                            } else {
                                if (mBean.getData().isIs_enroll()) {
                                    isJoin = true;
                                    join.setText("已参加");
                                    join.setSelected(false);
                                } else {
                                    join.setText("参加");
                                    join.setSelected(true);
                                }
                                join.setEnabled(true);
                            }
                        }

                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_comment://评论
                Intent intent = new Intent(this, MyActivityCommentActivity.class);
                intent.putExtra("activityId", mActivityId);
                startActivity(intent);
                break;
            case R.id.tv_join://加入
                activityApply();
                break;
            case R.id.good_state_ll://点赞
                doLike();
                break;
        }
    }

    private void doLike() {
        if (mBean.getData().getEvaluateComment() != null){
            showShortToast("每月只能赞一次哦");
            return;
        }

        Log.i(TAG, "doLike: ");
        selected = good_state_iv.isSelected();
        if (selected){
            likeType = 0;
        }else {
            likeType = 1;
        }

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("likeType", String.valueOf(likeType));
        paramMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        paramMap.put("activity_id",mActivityId);
        paramMap.put("community_id", AppPreference.I().getString("community_id",""));
        String params = ViewHelper.getParams(paramMap);
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_DETAILED_ISLIKE)
                .addParams("params",params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "活动详情点赞数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            if (selected){
                                good_state_iv.setSelected(false);
                            }else {
                                good_state_iv.setSelected(true);
                            }
                        }
                    }
                });
    }


    /**
     * 参加活动
     */
    private void activityApply() {
        if (mBean != null) {
            if (mBean.getData().isIs_end()) {
                showToast("活动已结束");
                return;
            }
            if (mBean.getData().getEnrollCount() >= mBean.getData().getEnroll_limit()) {
                showToast("活动人数已满");
                return;
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token",""));
        map.put("resident_id", AppPreference.I().getString("resident_id",""));
        map.put("activity_id", mActivityId);
        map.put("enroll_remark", "");
        map.put("community_id", SPUtils.getInstance().getString("community_id"));
        map1.put("data", map);
        Logger.i(TAG, GsonUtil.ToGson(map1));
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_APPLY)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (isJoin) {
                                join.setText("参加");
                                join.setSelected(true);
                                isJoin = false;
                            } else {
                                join.setText("已参加");
                                join.setSelected(false);
                                isJoin = true;
                            }
                            showToast("积分:+" + bean.getPoint());
                        }

                    }
                });

    }
}
