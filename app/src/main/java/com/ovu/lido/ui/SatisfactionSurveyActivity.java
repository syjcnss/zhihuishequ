package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.IsVoteInfo;
import com.ovu.lido.bean.SpVoteListInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 -- 满意度调查
 */
public class SatisfactionSurveyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.history_tv)
    TextView history_tv;
    @BindView(R.id.vote_btn)
    Button vote_btn;

    @BindView(R.id.satisfaction_survey_ll)
    LinearLayout satisfaction_survey_ll;

    @BindView(R.id.content_ll)
    LinearLayout content_ll;
    @BindView(R.id.satisfaction_survey_sv)
    ScrollView satisfaction_survey_sv;

    private List<SpVoteListInfo.InfoListBean> infoListBeans = new ArrayList<>();//调查列表
    private LayoutInflater mInflater;

    private List<Map<String, Object>> id_list = new ArrayList<>();//投票选项集合
    private int id;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_satisfaction_survey;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    protected void setListener() {
        super.setListener();
        history_tv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        toVote();

    }

    /**
     * 投票列表数据
     */
    private void getVoteList() {
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.QUERY_SP_VOTE_LIST_URL)
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("start", "0")
                .addParams("count", "100")
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
                        Logger.i(TAG, "投票列表数据-jsonStr: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)){
                            SpVoteListInfo info = GsonUtil.GsonToBean(response, SpVoteListInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)) {
                                infoListBeans.addAll(info.getInfo_list());
                                //根据infoListBeans给出的状态添加动态布局
                                loadingDynamicLayout();
                            }
                        }else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 加载动态布局
     */
    private void loadingDynamicLayout() {
        satisfaction_survey_ll.removeAllViewsInLayout();
        int size = infoListBeans == null ? 0 : infoListBeans.size();
        for (int i = 0; i < size; i++) {
            id = infoListBeans.get(i).getId();
            View title_view = mInflater.inflate(R.layout.satisfaction_survey_title, null);
            TextView survey_title_tv = title_view.findViewById(R.id.survey_title_tv);
            TextView type_tv = title_view.findViewById(R.id.type_tv);
            final int topic_id = infoListBeans.get(i).getTopic_id();
            survey_title_tv.setText( (i+1) + "." + infoListBeans.get(i).getTitle() );
            satisfaction_survey_ll.addView(title_view);
            String multi = infoListBeans.get(i).getMulti();
            final List<SpVoteListInfo.InfoListBean.TopicdetailBean> topicdetail = infoListBeans.get(i).getTopicdetail();

            switch (multi) {
                case "0"://单选
                    final Map<String, Object> danxMap = new HashMap<>();
                    View rb_view = mInflater.inflate(R.layout.satisfaction_survey_dx, null);
                    RadioGroup dx_group_rg = rb_view.findViewById(R.id.dx_group_rg);
                    for (int j = 0; j < topicdetail.size(); j++) {
                        RadioButton radioButton = new RadioButton(mContext);
                        radioButton.setTextSize(AppUtil.sp2px(16, 1));
                        radioButton.setTextColor(getResources().getColor(R.color.title_text));
                        radioButton.setPadding(AppUtil.dip2px(mContext, 5), AppUtil.dip2px(mContext, 5), AppUtil.dip2px(mContext, 5), AppUtil.dip2px(mContext, 5));
                        final String content = topicdetail.get(j).getContent();
                        radioButton.setText(content);
                        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    danxMap.put(String.valueOf(topic_id), content);
                                }
                            }
                        });
                        dx_group_rg.addView(radioButton);
                    }
                    satisfaction_survey_ll.addView(rb_view);
                    id_list.add(danxMap);
                    break;
                case "1"://多选
                    type_tv.setVisibility(View.VISIBLE);
                    final List<String> contentList = new ArrayList<>();
                    Map<String, Object> duoxMap = new HashMap<>();
                    for (int j = 0; j < topicdetail.size(); j++) {
                        View dx_cb_view = mInflater.inflate(R.layout.satisfaction_survey_checkbox, null);
                        CheckBox dx_cb = dx_cb_view.findViewById(R.id.dx_cb);
                        final String content = topicdetail.get(j).getContent();
                        dx_cb.setText(content);
                        dx_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    contentList.add(content);
                                } else {
                                    contentList.remove(content);
                                }
                            }
                        });
                        satisfaction_survey_ll.addView(dx_cb_view);
                    }
                    duoxMap.put(String.valueOf(topic_id), contentList);
                    id_list.add(duoxMap);
                    break;
                case "2"://可填
                    final Map<String, Object> ktMap = new HashMap<>();
                    View kt_et_view = mInflater.inflate(R.layout.satisfaction_survey_kt, null);
                    EditText kt_et = kt_et_view.findViewById(R.id.survey_options_et);
                    kt_et.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String recommend = s.toString().trim();
                            ktMap.put(String.valueOf(topic_id), recommend);
                        }
                    });
                    satisfaction_survey_ll.addView(kt_et_view);
                    id_list.add(ktMap);
                    break;
            }

        }
    }

    @OnClick({R.id.back_iv, R.id.history_tv, R.id.vote_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.history_tv://历史
                startActivity(new Intent(mContext, SatisfactionSurveyHistoryActivity.class));
                break;
            case R.id.vote_btn:
                Log.i(TAG, "id_list: " + id_list.toString());
                Log.i(TAG, "id_list: " + id_list.size() + "\n infoListBeans: " + infoListBeans.size());
                if (isRule()){
                    sendVote();
                }else {
                    showShortToast("请完善投票");
                }

                break;
        }
    }

    private boolean isRule() {
        for (Map<String, Object> pa : id_list){
            Log.i(TAG, "======pa======: " + pa);
            if (pa.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * 检查 ：
     * 1，是否已经投票
     * 2，是否有投票数据
     */
    private void toVote() {
        mDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkGo.<String>post(Constant.WHETHER_VOTE_URL)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "是否投票过数据-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(mDialog);

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            IsVoteInfo info = GsonUtil.GsonToBean(response.body(), IsVoteInfo.class);
                            IsVoteInfo.DataBean data = info.getData();
                            if (data.getIsEmpty().equals("0")){//有数据
                                getVoteList();//加载列表
                                if (data.getIsSend().equals("0")){//没有投过票
                                    vote_btn.setEnabled(true);
                                    vote_btn.setText("投票");
                                }else if (data.getIsSend().equals("1")){//已经投过票
                                    vote_btn.setEnabled(false);
                                    vote_btn.setText("已投票");
                                }
                            }else if (data.getIsEmpty().equals("1")){//没有数据
                                vote_btn.setEnabled(false);
                                showToast("无满意度调查！");
                            }
                        }else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    private void sendVote() {
        mDialog.show();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("id", id);
        paramsMap.put("ip", "183.95.51.39");
        paramsMap.put("id_list", id_list);
        Map<String, Object> paramsMap2 = new HashMap<>();
        paramsMap2.put("data", paramsMap);
        OkHttpUtils.post()
                .url(Constant.SEND_VOTE_URL)
                .addParams("params", GsonUtil.ToGson(paramsMap2))
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
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
                        Log.i(TAG, "提交投票信息数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)) {
                                showShortToast("投票成功");
                                vote_btn.setEnabled(false);
                                vote_btn.setText("已投票");
                            } else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }
}
