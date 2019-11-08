package com.ovu.lido.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.adapter.NeighDetailAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.NeiBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 个人信息动态
 */
public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.back_iv)
    ImageView iv_back;
    // @BindView(R.id.iv_personal_head)
    ImageView head;
    //   @BindView(R.id.tv_personal_name)
    TextView name;
    @BindView(R.id.lv_personal_info)
    ListView lv_info;
    // @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    private String typeId;
    private String personId;
    private NeighDetailAdapter mDetailAdapter;
    private ArrayList<NeiBean.InfoListBean> mList = new ArrayList<>();
    private int oldIndex;
    private int newIndex;
    private boolean isFirstScroll;
    private View mView;
    @BindView(R.id.nei_smart)
    SmartRefreshLayout mRefreshLayout;
    private final int mPagerCount = 20;
    private int mStart = 0;
    private int mCurrentPage = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_personal;
    }

    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        typeId = intent.getStringExtra("typeId");
        personId = intent.getStringExtra("personId");
        mView = LayoutInflater.from(this).inflate(R.layout.person_header_view, null);
        head = mView.findViewById(R.id.iv_personal_head);
        name = mView.findViewById(R.id.tv_personal_name);
        rl_top = mView.findViewById(R.id.rl_top);
        lv_info.addHeaderView(mView);
        mDetailAdapter = new NeighDetailAdapter(this, mList);
        mDetailAdapter.setFlag(false);
        lv_info.setAdapter(mDetailAdapter);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 获取个人动态
     */
    private void getData() {
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_USER_INFO)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("info_type_id", typeId)
                .addParams("user_id", personId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isFinishing()) {
                            return;
                        }
                        mRefreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, "请求信息:" + response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(PersonalActivity.this);
                            return;
                        }
                        NeiBean bean = GsonUtil.GsonToBean(response, NeiBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            RequestOptions options = new RequestOptions()
                                    .error(R.drawable.default_head)
                                    .placeholder(R.drawable.default_head)
                                    .transform(new GlideCircleTransform(PersonalActivity.this));
                            Glide.with(PersonalActivity.this)
                                    .load(bean.getIcon_url())
                                    .apply(options)
                                    .into(head);
                            name.setText(bean.getNick_name());
                            mList.addAll(bean.getInfo_list());
                            if (bean.getInfo_list().size() < 20) {
                                mStart = mCurrentPage * mPagerCount + bean.getInfo_list().size();
                            } else {
                                mCurrentPage++;
                                mStart = mCurrentPage * mPagerCount;
                            }
                            mDetailAdapter.setList(mList);
                            showFooterView();
                        } else {
                            showToast(bean.getErrorMsg());
                            mRefreshLayout.finishLoadMore();
                        }
                    }
                });


    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        mDetailAdapter.setAdapterOnItemClickListener(new NeighDetailAdapter.AdapterOnItemClickListener() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(PersonalActivity.this, DynamicsActivity.class);
                intent.putExtra("postId", mList.get(pos).getId());
                intent.putExtra("position", pos + "");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    /**
     * 判断数据是否加载完
     */
    private void showFooterView() {
        if (mStart % mPagerCount == 0) {
            mRefreshLayout.setNoMoreData(false);
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.setNoMoreData(true);
            mRefreshLayout.finishLoadMore();
        }
    }
}
