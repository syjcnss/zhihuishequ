package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.ChilAccountBean;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;


/**
 * 子账户管理
 */

public class AccountManagerActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.iv_head)
    ImageView mIv_head;
    @BindView(R.id.tv_name)
    TextView mTv_name;
    @BindView(R.id.tv_account_name)
    TextView mTv_account_name;
    @BindView(R.id.tv_phone)
    TextView mTv_phone;
    @BindView(R.id.lv_account)
    ListView mLv_account;
    private CommonAdapter<ChilAccountBean.DataBean> mCommonAdapter;
    private ArrayList<ChilAccountBean.DataBean> mList = new ArrayList<>();
    private int type = 0;
    private int mChildId;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_account_manager;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("nick_name");
        String icon = intent.getStringExtra("icon");
        String human_name = intent.getStringExtra("human_name");
        RequestOptions options = new RequestOptions()
                .transform(new GlideCircleTransform(this))
                .error(R.drawable.default_head)
                .placeholder(R.drawable.default_head);
        Glide.with(this).load(Constant.IMG_CONFIG + icon).apply(options).into(mIv_head);
        mTv_phone.setText("电话:" + AppPreference.I().getString("phoneNum", ""));
        mTv_account_name.setText("姓名:" + human_name);
        mTv_name.setText(name);

        mCommonAdapter = new CommonAdapter<ChilAccountBean.DataBean>(this, mList, R.layout.lv_account_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final ChilAccountBean.DataBean item, final int position) {
                viewHolder.setText(R.id.tv_name, item.getNick_name());
                viewHolder.setText(R.id.tv_phone, item.getMOBILE_NO());
                if (item.getChild_state() == 1) {
                    viewHolder.setText(R.id.tv_state, "已启用");
                    viewHolder.getView(R.id.tv_state).setSelected(false);
                    type = 0;
                } else {
                    viewHolder.setText(R.id.tv_state, "已禁用");
                    viewHolder.getView(R.id.tv_state).setSelected(true);
                    type = 1;
                }
                viewHolder.getView(R.id.tv_state).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChildId = item.getChild_id();
                        changeState(position);
                    }
                });
            }
        };
        mLv_account.setAdapter(mCommonAdapter);

    }


    /**
     * 启用或者禁用账户
     *
     * @param position
     */
    private void changeState(final int position) {
        if (mList != null) {
            if (mList.get(position).getChild_state() == 1) {
                type = 0;
            } else {
                type = 1;
            }
        }
        OkHttpUtils.post()
                .url(HttpAddress.CHANGE_ACCOUNT_STATE)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("child_id", mChildId + "")
                .addParams("type", type + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(AccountManagerActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (type == 0) {
                                mList.get(position).setChild_state(0);
                                type = 1;
                            } else {
                                mList.get(position).setChild_state(1);
                                type = 0;
                            }
                            mCommonAdapter.setDatas(mList);
                        }
                    }
                });

    }

    @Override
    protected void loadData() {
        getData();
    }

    private void getData() {
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_CHILD_LIST)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("child_state", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(AccountManagerActivity.this);
                            return;
                        }
                        ChilAccountBean bean = GsonUtil.GsonToBean(response, ChilAccountBean.class);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

}
