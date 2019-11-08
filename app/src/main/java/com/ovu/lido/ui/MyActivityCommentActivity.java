package com.ovu.lido.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.ActivityCommentBean;
import com.ovu.lido.bean.CommentBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class MyActivityCommentActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.lv_comment)
    ListView mLv_comment;
    @BindView(R.id.et_comment_content)
    EditText et_content;
    private CommonAdapter<ActivityCommentBean.DataBean> mCommonAdapter;
    private ArrayList<ActivityCommentBean.DataBean> mList = new ArrayList<>();
    private String mActivityId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).keyboardEnable(false).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mActivityId = intent.getStringExtra("activityId");
        mCommonAdapter = new CommonAdapter<ActivityCommentBean.DataBean>(this, mList, R.layout.lv_activity_comment_item) {
            @Override
            protected void convert(ViewHolder viewHolder, ActivityCommentBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_content, item.getUser_name() + ":" + item.getComment());
            }
        };
        mLv_comment.setAdapter(mCommonAdapter);
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
        Logger.i(TAG, GsonUtil.ToGson(map1));
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_COMMENT_LIST)
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
                            AppUtil.toLogin(MyActivityCommentActivity.this);
                            return;
                        }
                        ActivityCommentBean bean = GsonUtil.GsonToBean(response, ActivityCommentBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            mList.addAll(bean.getData());
                            mCommonAdapter.setDatas(mList);
                        }
                    }
                });

    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.tv_send_msg).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_send_msg:
                sendComment();
                break;

        }
    }

    /**
     * 发表评论
     */
    private void sendComment() {
        String content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("评论不能为空");
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token",""));
        map.put("resident_id", AppPreference.I().getString("resident_id",""));
        map.put("activity_id", mActivityId);
        map.put("community_id", AppPreference.I().getString("community_id",""));
        map.put("comment", content);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_COMMENT)
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
                            AppUtil.toLogin(MyActivityCommentActivity.this);
                            return;
                        }
                        CommentBean bean = GsonUtil.GsonToBean(response, CommentBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            et_content.setText("");
                            KeyboardUtils.hideSoftInput(MyActivityCommentActivity.this);
                            if (bean.getPoint() == 0) {
                                showToast("评论成功");
                            } else {
                                showToast("积分:" + bean.getPoint());
                            }
                            getData();

                        }
                    }
                });
    }
}
