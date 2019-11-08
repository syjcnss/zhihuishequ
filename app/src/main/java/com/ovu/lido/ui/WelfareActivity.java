package com.ovu.lido.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.adapter.AwardsRecordLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.AwardsInfo;
import com.ovu.lido.bean.AwardsRecordInfo;
import com.ovu.lido.bean.BeginAwardsInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.bean.UserIntegrationInfo;
import com.ovu.lido.help.StringUtil;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.AutoScrollView;
import com.ovu.lido.widgets.LuckyMonkeyPanelView;
import com.ovu.lido.widgets.PanelItemView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 福利
 */
public class WelfareActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.accumulated_score_tv)
    TextView accumulated_score_tv;//累计积分

    @BindView(R.id.lucky_panel)
    LuckyMonkeyPanelView lucky_panel;
    @BindView(R.id.btn_action)
    Button btn_action;

    @BindView(R.id.item4)
    PanelItemView item4;
    @BindView(R.id.item1)
    PanelItemView item1;
    @BindView(R.id.item2)
    PanelItemView item2;
    @BindView(R.id.item3)
    PanelItemView item3;
    @BindView(R.id.item6)
    PanelItemView item6;
    @BindView(R.id.item9)
    PanelItemView item9;
    @BindView(R.id.item8)
    PanelItemView item8;
    @BindView(R.id.item7)
    PanelItemView item7;

    @BindView(R.id.awards_record_lv)
    AutoScrollView awards_record_lv;

    ImageView lucky_icon_iv1, lucky_icon_iv2, lucky_icon_iv3, lucky_icon_iv4,
            lucky_icon_iv6, lucky_icon_iv7, lucky_icon_iv8, lucky_icon_iv9;
    TextView lucky_title_tv1, lucky_title_tv2, lucky_title_tv3, lucky_title_tv4,
            lucky_title_tv6, lucky_title_tv7, lucky_title_tv8, lucky_title_tv9;

    private List<ImageView> imgs = new ArrayList<>();
    private List<TextView> txts = new ArrayList<>();
    private int present_point = 0;//积分余额
    private List<AwardsRecordInfo.DataBean> recordInfos = new ArrayList<>();//所有用户中奖记录集合
    private List<AwardsInfo.DataBean.ListBean> awardInfos = new ArrayList<>();//奖品列表集合
    private int awards_id;//中奖奖品id
    private AwardsRecordLvAdapter mAwardsRecordLvAdapter;
    private int index = 0;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void initView() {
        super.initView();
        lucky_icon_iv4 = item4.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv1 = item1.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv2 = item2.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv3 = item3.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv6 = item6.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv9 = item9.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv8 = item8.findViewById(R.id.lucky_icon_iv);
        lucky_icon_iv7 = item7.findViewById(R.id.lucky_icon_iv);

        lucky_title_tv4 = item4.findViewById(R.id.lucky_title_tv);
        lucky_title_tv1 = item1.findViewById(R.id.lucky_title_tv);
        lucky_title_tv2 = item2.findViewById(R.id.lucky_title_tv);
        lucky_title_tv3 = item3.findViewById(R.id.lucky_title_tv);
        lucky_title_tv6 = item6.findViewById(R.id.lucky_title_tv);
        lucky_title_tv9 = item9.findViewById(R.id.lucky_title_tv);
        lucky_title_tv8 = item8.findViewById(R.id.lucky_title_tv);
        lucky_title_tv7 = item7.findViewById(R.id.lucky_title_tv);

        imgs.add(lucky_icon_iv1);
        imgs.add(lucky_icon_iv2);
        imgs.add(lucky_icon_iv3);
        imgs.add(lucky_icon_iv6);
        imgs.add(lucky_icon_iv9);
        imgs.add(lucky_icon_iv8);
        imgs.add(lucky_icon_iv7);
        imgs.add(lucky_icon_iv4);

        txts.add(lucky_title_tv1);
        txts.add(lucky_title_tv2);
        txts.add(lucky_title_tv3);
        txts.add(lucky_title_tv6);
        txts.add(lucky_title_tv9);
        txts.add(lucky_title_tv8);
        txts.add(lucky_title_tv7);
        txts.add(lucky_title_tv4);
    }

    @Override
    protected void setListener() {
        super.setListener();
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lucky_panel.isGameRunning()) {
                    Map<String, String> paramsMap = new HashMap<>();
                    paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
                    paramsMap.put("pageSize", "0");
                    paramsMap.put("pageNo", "100");
                    String params = ViewHelper.getParams(paramsMap);
                    OkHttpUtils.post()
                            .url(Constant.BEGIN_AWARDS)
                            .addParams("params", params)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e(TAG, "onError: " + e);
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.i(TAG, "中奖id数据: " + response);
                                    BeginAwardsInfo info = GsonUtil.GsonToBean(response, BeginAwardsInfo.class);
                                    if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                                        awards_id = info.getData().getAwards_id();
                                        if (awards_id != 0) {
                                            present_point -= 200;
                                            accumulated_score_tv.setText(String.valueOf(present_point));
                                            lucky_panel.startGame();
                                            btn_action.setText("停止");
                                            getUserIntegration();
                                        }
                                    } else {
                                        showShortToast(info.getErrorMsg());
                                    }
                                }
                            });
                } else {
                    int stayIndex = 0;
                    String t = "";
                    for (int i = 0; i < awardInfos.size(); i++) {
                        int id = awardInfos.get(i).getId();
                        Log.i(TAG, "awardInfos.getId(): " + id);
                        if (id == awards_id) {
                            stayIndex = i;
                            t = awardInfos.get(i).getAwards_name();
                        }
                    }

//                    stayIndex = new Random().nextInt(8);
                    Log.e("LuckyMonkeyPanelView", "====stay===" + stayIndex);
                    lucky_panel.tryToStop(stayIndex);
                    btn_action.setText("开始");
                    showShortToast(t);

                }
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.details_tv, R.id.sign_in_tv,
            R.id.rule_iv, R.id.my_prize_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.details_tv://明细
                startActivity(new Intent(mContext, IntegralDetailActivity.class));
                break;
            case R.id.sign_in_tv://签到
                doSignIn();
                break;
            case R.id.rule_iv://规则
                startActivity(new Intent(mContext, RuleActivity.class));
                break;
            case R.id.my_prize_iv://我的奖品
                startActivity(new Intent(mContext, MyPrizeActivity.class));
                break;

        }
    }

    /**
     * 签到
     */
    private void doSignIn() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.SIGN_IN)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "签到数据: " + response);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        String errorCode = info.getErrorCode();
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            int point = info.getPoint();
                            int sumPoint = present_point + point;
                            accumulated_score_tv.setText(String.valueOf(sumPoint));
                            showShortToast("签到成功：+" + point + " 积分");
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    protected void loadData() {
        super.loadData();
        getUserIntegration();//积分余额
        getAwardsData();//奖品数据
        getAwardsRecord();//所有用户中奖记录数据


    }

    /**
     * 获取积分余额
     */
    private void getUserIntegration() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_USER_INTE_GRATION)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "积分余额数据: " + response);
                        UserIntegrationInfo info = GsonUtil.GsonToBean(response, UserIntegrationInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            present_point = info.getData().getPresent_point();
                            accumulated_score_tv.setText(String.valueOf(present_point));
                        }
                    }
                });
    }

    /**
     * 获取奖品数据
     */
    private void getAwardsData() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("pageSize", "0");
        paramsMap.put("pageNo", "100");
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_AWARDS)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "奖品列表数据: " + response);
                        AwardsInfo info = GsonUtil.GsonToBean(response, AwardsInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            awardInfos.addAll(info.getData().getList());
                            setAwardsLayout();
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 设置奖品布局
     */
    private void setAwardsLayout() {
        int awardInfoSize = awardInfos == null ? 0 : awardInfos.size();
        for (int i = 0; i < awardInfoSize; i++) {
            Log.i(TAG, "setAwardsLayout: " + awardInfos.get(i).getAwards_name());
            Glide.with(mContext)
                    .load(awardInfos.get(i).getAwards_img())
                    .apply(new RequestOptions().placeholder(R.drawable.lp_04)
                            .error(R.drawable.lp_04))
                    .into(imgs.get(i));
            txts.get(i).setText(awardInfos.get(i).getAwards_name());
        }

//        lucky_icon_iv1.setImageResource(R.drawable.lp_04);
//        lucky_icon_iv2.setImageResource(R.drawable.lp_01);
//        lucky_icon_iv3.setImageResource(R.drawable.lp_04);
//        lucky_icon_iv4.setImageResource(R.drawable.lp_02);
//        lucky_icon_iv6.setImageResource(R.drawable.lp_03);
//        lucky_icon_iv7.setImageResource(R.drawable.lp_04);
//        lucky_icon_iv8.setImageResource(R.drawable.lp_01);
//        lucky_icon_iv9.setImageResource(R.drawable.lp_04);

//        lucky_title_tv1.setText("谢谢参与");
//        lucky_title_tv2.setText("神秘礼包");
//        lucky_title_tv3.setText("谢谢参与");
//        lucky_title_tv4.setText("100积分");
//        lucky_title_tv6.setText("200积分");
//        lucky_title_tv7.setText("谢谢参与");
//        lucky_title_tv8.setText("神秘礼包");
//        lucky_title_tv9.setText("谢谢参与");
    }

    /**
     * 获取所有用户中奖记录数据
     */
    private void getAwardsRecord() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("pageSize", "0");
        paramsMap.put("pageNo", "100");
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_AWARDS_RECORD)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "所有用户的中奖纪录数据: " + response);
                        AwardsRecordInfo info = GsonUtil.GsonToBean(response, AwardsRecordInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            List<AwardsRecordInfo.DataBean> data = info.getData();
                            int count = data.size() > 5 ? 5 : data.size();
                            for (int i = 0; i < count; i++) {
                                recordInfos.add(data.get(i));
                            }
                            mAwardsRecordLvAdapter = new AwardsRecordLvAdapter(mContext, getData());
                            awards_record_lv.setAdapter(mAwardsRecordLvAdapter);
                            if (recordInfos.size() >= 5) {
                                Timer autoUpdate = new Timer();
                                autoUpdate.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @SuppressLint("NewApi")
                                            public void run() {
                                                if (isFinishing()){
                                                    return;
                                                }
                                                index += 1;
                                                if (index >= awards_record_lv.getCount()) {
                                                    index = 0;

                                                }
                                                awards_record_lv.smoothScrollToPositionFromTop(index, 30, 1000);
                                            }
                                        });
                                    }
                                }, 0, 1000);
                            }
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < recordInfos.size(); i++) {
            data.add(getEncryptPhone(recordInfos.get(i).getPhone()) + "  获得  " + recordInfos.get(i).getAwards_name());
        }

        return data;
    }

    private String getEncryptPhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return "";
        }
        if (!ViewHelper.isMobileNO(phone)) {
            return "";
        }

        StringBuilder sb = new StringBuilder(phone);
        sb.replace(3, 7, "****");

        return sb.toString();

    }

}
