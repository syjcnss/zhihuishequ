package com.ovu.lido.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.PostMenuAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MyResonseBean;
import com.ovu.lido.bean.PostDetailBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class PostMenuActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.top_title)
    TextView mTv_top_title;
    @BindView(R.id.lv_menu)
    ListView mLv_menu;
    private PostMenuAdapter mDetailAdapter;
    private ArrayList<MyResonseBean.DataBean.ListBean> mList = new ArrayList<>();
    private String url;
    private final int mPagerCount = 20;
    private int mStart = 0;
    private int mCurrentPage = 0;
    @BindView(R.id.nei_smart)
    SmartRefreshLayout mRefreshLayout;
    private PostDetailBean mPostDetailBean = new PostDetailBean();
    private static final int POST_MENU_RECODE = 5;
    private int mPos = -1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_post_menu;
    }

    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("menuType");
        switch (type) {
            case "1":
                mTv_top_title.setText("我的发帖");
                url = HttpAddress.QUERY_MY_INFO;
                break;
            case "2":
                mTv_top_title.setText("我的回复");
                url = HttpAddress.QUERY_MY_RESPONSE;
                break;
            case "3":
                mTv_top_title.setText("我的点赞");
                url = HttpAddress.QUERY_MY_AGREE;
                break;
        }
        mDetailAdapter = new PostMenuAdapter(this, mList);
        mLv_menu.setAdapter(mDetailAdapter);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);

    }


    private void getData() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map.put("info_type_id", "0");
        map.put("pageNo", mStart + "");
        map.put("pageSize", mPagerCount + "");
        map.put("info_typename", "1");
        map.put("token", AppPreference.I().getString("token",""));
        map.put("resident_id", AppPreference.I().getString("resident_id",""));
        map1.put("data", map);
        Logger.i(Tag, GsonUtil.ToGson(map1));
        OkHttpUtils.post()
                .tag(this)
                .url(url)
//                .addParams("info_type_id", "0")
//                .addParams("pageNo", mStart + "")
//                .addParams("pageSize", mPagerCount + "")
//                .addParams("info_typename", "1")
//                .addParams("token", SPUtils.getInstance().getString("token"))
//                .addParams("resident_id", SPUtils.getInstance().getString("id"))
                .addParams("params", GsonUtil.ToGson(map1))
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
                        Logger.i(Tag, "请求信息:" + response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(PostMenuActivity.this);
                            return;
                        }
                        MyResonseBean bean = GsonUtil.GsonToBean(response, MyResonseBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (mStart == 0) {
                                mList.clear();
                            }
                            mList.addAll(bean.getData().getList());
                            if (bean.getData().getList().size() < 20) {
                                mStart = mCurrentPage * mPagerCount + bean.getData().getList().size();
                            } else {
                                mCurrentPage++;
                                mStart = mCurrentPage * mPagerCount;
                            }
                            mDetailAdapter.setList(mList);
                            showFooterView();
                        } else {
                            mRefreshLayout.finishLoadMore();
                        }

                    }
                });
    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        mDetailAdapter.setAdapterOnItemClickListener(new PostMenuAdapter.AdapterOnItemClickListener() {
            @Override
            public void itemClick(int pos) {
                mPos = pos;
                Intent intent = new Intent(PostMenuActivity.this, DynamicsActivity.class);
                intent.putExtra("postId", mList.get(pos).getId());
                startActivityForResult(intent, POST_MENU_RECODE);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mStart = 0;
        mCurrentPage = 0;
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_MENU_RECODE && data != null) {
            PostDetailBean bean = (PostDetailBean) data.getSerializableExtra("postBean");
            if (!TextUtils.isEmpty(bean.getIs_agree())) {
                mList.get(mPos).setIs_agree(bean.getIs_agree());
            }
            if (!TextUtils.isEmpty(bean.getAgree_count())) {
                mList.get(mPos).setAgree_count(bean.getAgree_count());
            }
            if (!TextUtils.isEmpty(bean.getResponse_count())) {
                mList.get(mPos).setResponse_count(bean.getResponse_count());
            }
            mDetailAdapter.setList(mList);
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

}
