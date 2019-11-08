package com.ovu.lido.ui;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.QuestionDetailInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class QuestionDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.item_type_name)
    TextView item_type_name;
    @BindView(R.id.item_content)
    TextView item_content;
    @BindView(R.id.item_status)
    TextView item_status;
    @BindView(R.id.item_remarks)
    TextView item_remarks;
    @BindView(R.id.item_time)
    TextView item_time;
    private int id;
    private String checkTypeName;
    private String content;
    private String createTime;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        id = getIntent().getIntExtra("id", 0);
        checkTypeName = getIntent().getStringExtra("checkTypeName");
        content = getIntent().getStringExtra("content");
        createTime = getIntent().getStringExtra("createTime");
        item_type_name.setText(checkTypeName);
        item_content.setText(content);
        item_time.setText(createTime);
    }

    @Override
    protected void loadData() {
        super.loadData();
        /**
         * {
         *   "data": {
         *     "resident_id": "213964",
         *     "checkroomId": "56"
         *    }
         * }
         */
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        paramsMap.put("checkroomId", String.valueOf(id));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_QUESTION_DETAIL)
                .addParams("params",params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        QuestionDetailInfo info = GsonUtil.GsonToBean(response, QuestionDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            QuestionDetailInfo.DataBean data = info.getData();
                            if (data != null){
                                String createTime = data.getCreateTime();
                                String time = createTime;
                                item_time.setText(time);
                                item_status.setText("已处理");
                                item_remarks.setText(data.getResult());
                            }
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }


}
