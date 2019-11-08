package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ovu.lido.R;
import com.ovu.lido.adapter.ImgGridAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.CommentBean;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.bean.GiveGoodBean;
import com.ovu.lido.bean.PostDetailBean;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.widgets.MyGrideView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;


/**
 * 动态详情
 */
public class DynamicsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.iv_neighbourhood_head)
    ImageView mIv_head;
    @BindView(R.id.iv_feedback)
    ImageView mIv_feedback;
    @BindView(R.id.iv_good_state)
    ImageView mIv_good_state;
    @BindView(R.id.tv_neigh_name)
    TextView mTv_name;
    @BindView(R.id.tv_neigh_date)
    TextView mTv_date;
    @BindView(R.id.tv_neigh_type)
    TextView mTv_type;
    @BindView(R.id.tv_neigh_title)
    TextView mTv_title;
    @BindView(R.id.tv_neigh_content)
    TextView mTv_content;
    @BindView(R.id.tv_top_del)
    TextView mTv_top_del;
    @BindView(R.id.tv_comment_count)
    TextView mTv_comment_count;
    @BindView(R.id.tv_good_count)
    TextView mTv_good_count;
    @BindView(R.id.et_comment_content)
    EditText mEt_comment_content;
    @BindView(R.id.tv_send_msg)
    TextView mTv_send;
    @BindView(R.id.lv_comment)
    ListView mLv_comment;
    @BindView(R.id.rv_neigh_img)
    MyGrideView mRv_neigh_img;
    private ImgGridAdapter mImgRvAdapter;
    private ArrayList<String> mList = new ArrayList<>();
    private CommonAdapter<PostDetailBean.InfoResponseListBean> mCommonAdapter;
    private ArrayList<PostDetailBean.InfoResponseListBean> mCommentList = new ArrayList<>();
    private String mPostId;
    private String mUserId;
    String[] imgUrl;
    private String mInfo;
    private PostDetailBean mPostDetailBean = new PostDetailBean();
    private String mGoodCount;
    private String position;
    private String mTypeId;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_dynamics;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        // 重新加载逻辑

        mUserId = AppPreference.I().getString("resident_id", "");
        Intent intent = getIntent();
        mPostId = intent.getStringExtra("postId");
        position = intent.getStringExtra("position");
        mImgRvAdapter = new ImgGridAdapter(this);
        mRv_neigh_img.setAdapter(mImgRvAdapter);
        mCommonAdapter = new CommonAdapter<PostDetailBean.InfoResponseListBean>(this, mCommentList, R.layout.lv_dynamics_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final PostDetailBean.InfoResponseListBean item, final int position) {
                RequestOptions options = new RequestOptions()
                        .transform(new GlideCircleTransform(DynamicsActivity.this))
                        .placeholder(R.drawable.default_head)
                        .error(R.drawable.default_head);
                Glide.with(DynamicsActivity.this)
                        .load(item.getIcon_url())
                        .apply(options)
                        .into((ImageView) viewHolder.getView(R.id.iv_head));
                viewHolder.setText(R.id.tv_name, item.getNick_name());
                viewHolder.setText(R.id.tv_date, item.getResponse_time());
                viewHolder.setText(R.id.tv_content, item.getContent());
                if (item.getResident_id().equals(mUserId)) {
                    viewHolder.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.tv_delete).setVisibility(View.GONE);
                }
                viewHolder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteComment(item.getInfo_response_id());
                    }
                });
                viewHolder.getView(R.id.iv_head).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PersonalActivity.class);
                        intent.putExtra("personId", item.getResident_id());
                        intent.putExtra("typeId", mTypeId);
                        mContext.startActivity(intent);
                    }
                });
            }
        };
        mLv_comment.setAdapter(mCommonAdapter);
    }

    @Override
    protected void loadData() {
        getData();
    }


    /**
     * 获取帖子详情
     */
    private void getData() {
        OkHttpUtils.post()
                .tag(this)
                .url(HttpAddress.POST_DETAIL)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("info_id", mPostId)
                .addParams("has_response", 1 + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        PostDetailBean bean = GsonUtil.GsonToBean(response, PostDetailBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (bean.getResident_id().equals(mUserId)) {
                                mTv_top_del.setVisibility(View.VISIBLE);
                                mIv_feedback.setVisibility(View.GONE);
                            } else {
                                mTv_top_del.setVisibility(View.GONE);
                                mIv_feedback.setVisibility(View.VISIBLE);
                            }
                            mInfo = bean.getIs_agree().equals("1") ? "10" : "11";
                            RequestOptions options = new RequestOptions()
                                    .transform(new GlideCircleTransform(DynamicsActivity.this))
                                    .placeholder(R.drawable.default_head)
                                    .error(R.drawable.default_head);
                            Glide.with(DynamicsActivity.this)
                                    .load(bean.getIcon_url())
                                    .apply(options)
                                    .into(mIv_head);
                            mTv_name.setText(bean.getNick_name());
                            mTv_date.setText(bean.getCreate_time().substring(0, 10));
                            mTv_content.setText(bean.getContent());
                            mTv_title.setText(bean.getTitle());
                            mTypeId = bean.getInfo_type_id();
                            if (bean.getInfo_typename().equals("2")) {
                                mTv_type.setText("二手市场");
                            } else if (bean.getInfo_typename().equals("4")) {
                                mTv_type.setText("议事大厅");
                            }
                            mList.clear();
                            if (!TextUtils.isEmpty(bean.getImgs())) {
                                String[] imgUrl = bean.getImgs().split(",");
                                if (imgUrl.length > 0) {
                                    for (int i = 0; i < imgUrl.length; i++) {
                                        mList.add(imgUrl[i]);
                                    }
                                }
                            }
                            mImgRvAdapter.setList(mList);
                            mTv_comment_count.setText("(" + bean.getResponse_count() + ")");
                            mGoodCount = bean.getAgree_count();
                            mTv_good_count.setText(mGoodCount);
                            if (bean.getIs_agree().equals("1")) {
                                mIv_good_state.setSelected(true);
                            } else {
                                mIv_good_state.setSelected(false);
                            }
                            mPostDetailBean.setIs_agree(bean.getIs_agree());
                            mPostDetailBean.setResponse_count(bean.getResponse_count());
                            mPostDetailBean.setAgree_count(bean.getAgree_count());
                            mCommentList.clear();
                            mCommentList.addAll(bean.getInfo_response_list());
                            mCommonAdapter.setDatas(mCommentList);
                            if (mCommentList.size() == 0) {
                                return;
                            }
                        }

                    }
                });
    }


    @Override
    protected void setListener() {
        findViewById(R.id.iv_feedback).setOnClickListener(this);
        findViewById(R.id.tv_send_msg).setOnClickListener(this);
        findViewById(R.id.tv_top_del).setOnClickListener(this);
        mIv_good_state.setOnClickListener(this);
        iv_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_feedback:
                showFeedBack();
                break;
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.tv_send_msg:
                sendComment();
                break;
            case R.id.tv_top_del:
                delPost();
                break;
            case R.id.iv_good_state:
                giveGood();
                break;

        }
    }

    /**
     * 点赞
     */
    private void giveGood() {
        OkHttpUtils.post()
                .tag(this)
                .url(HttpAddress.GIVE_GOOD)
                .addParams("info_id", mPostId)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("operate_type", mInfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        GiveGoodBean bean = GsonUtil.GsonToBean(response, GiveGoodBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            int count = mPostDetailBean.getIs_agree().equals("0") ? 1 : -1;
                            if (count == 1) {
                                mPostDetailBean.setIs_agree("1");
                                mIv_good_state.setSelected(true);
                            } else {
                                mPostDetailBean.setIs_agree("0");
                                mIv_good_state.setSelected(false);
                            }
                            mGoodCount = (Integer.parseInt(mGoodCount) + count) + "";
                            mTv_good_count.setText(mGoodCount);
                            mPostDetailBean.setAgree_count(mGoodCount);
                        }
                    }
                });
    }

    /**
     * 发表评论
     */
    private void sendComment() {
        String content = mEt_comment_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("评论不能为空");
            return;
        }
        Log.i(TAG, "输入内容为: " + content);
        OkHttpUtils.post()
                .url(HttpAddress.ADD_RESPONSE)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("title", "")
                .addParams("content", content)
                .addParams("info_id", mPostId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mEt_comment_content.setText("");
                            KeyboardUtils.hideSoftInput(DynamicsActivity.this);
                            showToast("积分:" + bean.getPoint());
                            refreshCommentList();
                        } else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 获取评论列表
     */
    private void refreshCommentList() {
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_RESPONSE)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("info_id", mPostId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        CommentBean bean = GsonUtil.GsonToBean(response, CommentBean.class);
                        mCommentList.clear();
                        if (bean.getErrorCode().equals("0000")) {
                            for (int i = 0; i < bean.getInfo_response_list().size(); i++) {
                                PostDetailBean.InfoResponseListBean info = new PostDetailBean.InfoResponseListBean();
                                info.setContent(bean.getInfo_response_list().get(i).getContent());
                                info.setIcon_url(bean.getInfo_response_list().get(i).getIcon_url());
                                info.setInfo_response_id(bean.getInfo_response_list().get(i).getInfo_response_id());
                                info.setResident_id(bean.getInfo_response_list().get(i).getResident_id());
                                info.setInfo_type_name(bean.getInfo_response_list().get(i).getInfo_type_name());
                                info.setResponse_time(bean.getInfo_response_list().get(i).getResponse_time());
                                info.setTitle(bean.getInfo_response_list().get(i).getTitle());
                                info.setNick_name(bean.getInfo_response_list().get(i).getNick_name());
                                mCommentList.add(info);
                            }
                            mTv_comment_count.setText("(" + mCommentList.size() + ")");
                            mPostDetailBean.setResponse_count(mCommentList.size() + "");
                            mCommonAdapter.setDatas(mCommentList);
                            if (mCommentList.size() == 0) {
                                return;
                            }
                        } else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 删除评论
     */
    private void deleteComment(String responseId) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("info_response_id", responseId);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.DELETE_RESPONSE)
                //               .addParams("token", SPUtils.getInstance().getString("token"))
//                .addParams("resident_id", SPUtils.getInstance().getString("id"))
//                .addParams("info_response_id", responseId)
                .addParams("params", gson.toJson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("删除成功");
                            refreshCommentList();
                        } else {
                            showToast(bean.getErrorMsg());
                        }

                    }
                });
    }

    /**
     * 投诉举报
     */
    private void feedBack() {
        OkHttpUtils.post()
                .url(HttpAddress.FEEDBACK)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("info_id", mPostId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("举报成功");
                        } else {
                            showToast(bean.getErrorMsg());
                        }

                    }
                });

    }

    /**
     * 删除帖子
     */
    private void delPost() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();

        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("info_id", mPostId);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.DELETE_POST)
                .addParams("params", gson.toJson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(DynamicsActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("删除成功");
                            EventBus.getDefault().post(new RefreshEvent(RefreshConstant.NEIGHBOR_LIST));
                            finish();
                        } else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("postBean", (Serializable) mPostDetailBean);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }


    private void showFeedBack() {
        final Dialog mDialog = new Dialog(this, R.style.DialogStyle);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.setContentView(R.layout.dialog_feddback_paper);
        TextView tv_cancel = (TextView) mDialog.findViewById(R.id.tv_dialog_cancel);
        TextView sure = (TextView) mDialog.findViewById(R.id.tv_dialog_save);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                feedBack();
            }
        });

    }


}
