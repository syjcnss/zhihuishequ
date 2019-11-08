package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.LeaveCommentsLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.LeaveCommentsInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 留言
 */
public class LeaveCommentsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.leave_comments_lv)
    ListView leave_comments_lv;
    @BindView(R.id.content_et)
    EditText content_et;
    @BindView(R.id.send_tv)
    TextView send_tv;

    private List<LeaveCommentsInfo.DataBean.AlllistBean> leaveCommentsInfos = new ArrayList<>();
    private LeaveCommentsLvAdapter mLeaveCommentsLvAdapter;
    private int mhousekeeperId;
    private Dialog mDailog;
    String tipState;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .keyboardEnable(false)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_leave_comments;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        tipState = intent.getStringExtra("tipState");
        if (TextUtils.isEmpty(tipState)) {
            tipState = "0";
        }
        mDailog = LoadProgressDialog.createLoadingDialog(mContext);
        mLeaveCommentsLvAdapter = new LeaveCommentsLvAdapter(mContext, leaveCommentsInfos);
        leave_comments_lv.setAdapter(mLeaveCommentsLvAdapter);
        leave_comments_lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    protected void setListener() {
        super.setListener();
        content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    send_tv.setEnabled(true);

                } else {
                    send_tv.setEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDailog.show();
        String params = getParams();
        OkHttpUtils.post()
                .url(Constant.LEAVE_COMMENTS_URL)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDailog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        LoadProgressDialog.closeDialog(mDailog);

                        LeaveCommentsInfo info = GsonUtil.GsonToBean(response, LeaveCommentsInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            mhousekeeperId = info.getData().getHousekeeper_id();
                            LeaveCommentsInfo.DataBean data = info.getData();
                            leaveCommentsInfos.addAll(data.getAlllist());
                            mLeaveCommentsLvAdapter.notifyDataSetChanged();
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取请求参数
     *
     * @return json参数
     */
    private String getParams() {
//        "data": {
//              "resident_id": "213964",
//              "pageSize": "100",
//              "pageNo": "0"
//        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramMap.put("pageSize", "100");
        paramMap.put("pageNo", "0");
        paramMap.put("isRepay", tipState);
        String params = ViewHelper.getParams(paramMap);
//        Map<String, Object> paramMap2 = new HashMap<>();
//        paramMap2.put("data", paramMap);
//        JSONObject jsonObject = new JSONObject(paramMap2);
//        String params = jsonObject.toString();
        Log.i("wangw", "params: " + params);
        return params;
    }

    @OnClick({R.id.back_iv, R.id.send_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.send_tv:
                doSend();
                break;
        }
    }

    /**
     * 处理发送按钮
     */
    private void doSend() {
        String content = content_et.getText().toString().trim();
        leaveCommentsInfos.add(new LeaveCommentsInfo.DataBean.AlllistBean(content, 1));
        content_et.setText("");
        mLeaveCommentsLvAdapter.notifyDataSetChanged();
        leave_comments_lv.setSelection(mLeaveCommentsLvAdapter.getCount() - 1);
        upLoadComments(content);
    }

    /**
     * 上传留言信息
     *
     * @param content
     */
    private void upLoadComments(String content) {
        mDailog.show();
        OkHttpUtils.post()
                .url(Constant.SEND_COMMENTS_URL)
                .addParams("comment_content", content)
                .addParams("housekeeper_id", String.valueOf(mhousekeeperId))
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
                        LoadProgressDialog.closeDialog(mDailog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LoadProgressDialog.closeDialog(mDailog);
                        if (isTokenError(response)) {
                            startLoginActivity();
                        } else {
                            UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                            if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                                showShortToast("发送成功");
                            } else {
                                showShortToast("发送失败：" + info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDailog);
    }
}
