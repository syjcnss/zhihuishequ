package com.ovu.lido.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.NeighDetailAdapter;
import com.ovu.lido.adapter.NeighborRvAdapter;
import com.ovu.lido.base.BaseFragment;
import com.ovu.lido.bean.GiveGoodBean;
import com.ovu.lido.bean.NeiBean;
import com.ovu.lido.bean.PostDetailBean;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.ui.DynamicsActivity;
import com.ovu.lido.ui.PersonalActivity;
import com.ovu.lido.ui.PostMenuActivity;
import com.ovu.lido.ui.PostedActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 邻里
 */
public class NeighborFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.all_tv)
    TextView all_tv;
    @BindView(R.id.second_tv)
    TextView second_tv;
    @BindView(R.id.hall_tv)
    TextView hall_tv;
    @BindView(R.id.neighbor_rv)
    RecyclerView neighbor_rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;

    private ArrayList<NeiBean.InfoListBean> mList = new ArrayList<>();
    private String postType = "";
    private static final int POST_DETAIL_CODE = 2;
    public static final int CODE_REFRESH_ALL = 4;
    private Dialog mDialog;
    private int mPageNo = 0;//页码
    private int mTotalCount;//总数量
    private int mStart = 0;//开始位置
    private NeighborRvAdapter mNeighborRvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll)
                .statusBarDarkFont(true,0.2f).init();
    }

    protected int setLayoutId() {
        return R.layout.activity_neighbourhood;
    }




    @Override
    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(getActivity());
        all_tv.setSelected(true);
        mNeighborRvAdapter = new NeighborRvAdapter(mContext, mList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        neighbor_rv.setLayoutManager(manager);
        neighbor_rv.setAdapter(mNeighborRvAdapter);

    }


    @Override
    protected void loadData() {
        mDialog.show();
        requestData();
    }

    private void requestData() {
        Map<String,String> map = new HashMap<>();
        OkGo.<String>post(Constant.QUERY_NEIGHBOR_LIST_URL)
                .params("info_type_id", "02")
                .params("start", mStart + "")
                .params("count", "20")
                .params("info_typename", postType)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("params", ViewHelper.getParams(map))
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "邻里列表-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(mDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            Type type = new TypeToken<NeiBean>() {
                            }.getType();
                            NeiBean info = new Gson().fromJson(response.body(), type);
                            if (mStart == 0) {
                                mList.clear();
                            }
                            mTotalCount = Integer.parseInt(info.getTotal_count());
                            List<NeiBean.InfoListBean> info_list = info.getInfo_list();
                            if (info_list != null) {
                                mList.addAll(info_list);
                                if (info_list.size() < 20) {
                                    mStart = mPageNo * 20 + info_list.size();
                                } else {
                                    mPageNo ++;
                                    mStart = mPageNo * 20;
                                }
                            }
                            mNeighborRvAdapter.notifyDataSetChanged();
                        }else {
                            showToast(errorMsg);
                        }
                    }
                });

    }

    @Override
    protected void setListener() {
        mNeighborRvAdapter.setOnChildClickListener(new NeighborRvAdapter.OnChildClickListener() {
            @Override
            public void onPraiseClick(int position) {
                //点赞、取消点赞  rest/info/operateInfo
                mDialog.show();
                NeiBean.InfoListBean info = mList.get(position);
                onPraise(info);
            }

            @Override
            public void onItemClick(View view) {
                int position = neighbor_rv.getChildAdapterPosition(view);
                NeiBean.InfoListBean info = mList.get(position);
                Intent intent = new Intent(getActivity(), DynamicsActivity.class);
                intent.putExtra("postId", info.getId());
                intent.putExtra("position", position + "");
                startActivityForResult(intent, POST_DETAIL_CODE);
            }

            @Override
            public void onIconClick(int position) {
                NeiBean.InfoListBean info = mList.get(position);
                Intent intent = new Intent(mContext, PersonalActivity.class);
                intent.putExtra("personId", info.getResident_id());
                intent.putExtra("typeId", info.getInfo_type_id());
                startActivity(intent);
            }
        });
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPageNo = 0;
                mStart = 0;
                requestData();
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mNeighborRvAdapter.getItemCount() < mTotalCount) {
                    requestData();
                } else {
                    Toast.makeText(mContext, "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishLoadMore();
                }
            }
        });

    }

    /**
     * 点赞/取消点赞
     */
    private void onPraise(final NeiBean.InfoListBean info) {
        String info_id = info.getId();
        String operate_type = info.getIs_agree().equals("1") ? "10" : "11";
        OkGo.<String>post(Constant.OPERATE_INFO)
                .params("info_id", info_id)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("operate_type", operate_type)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || mActivity.isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "点赞/取消-onSuccess: ");
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            int count = info.getIs_agree().equals("0") ? 1 : -1;
                            if (count == 1) {
                                info.setIs_agree("1");
                            } else {
                                info.setIs_agree("0");
                            }
                            info.setAgree_count((Integer.parseInt(info.getAgree_count())) + count + "");
                            mNeighborRvAdapter.notifyDataSetChanged();
                        }else {
                            showToast(errorMsg);
                        }
                    }
                });
    }


    @OnClick({R.id.all_tv, R.id.second_tv, R.id.hall_tv, R.id.posting_iv,
            R.id.my_posting_iv, R.id.my_reply_iv, R.id.my_like_iv})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.all_tv://全部
                postType = "";
                setTab(true,false,false);
                break;
            case R.id.second_tv://二手市场
                postType = "2";
                setTab(false,true,false);
                break;
            case R.id.hall_tv://议事大厅
                postType = "4";
                setTab(false,false,true);
                break;
            case R.id.posting_iv://发帖
                intent.setClass(mContext, PostedActivity.class);
                startActivity(intent);
                break;
            case R.id.my_posting_iv://我的发帖
                intent.setClass(mContext, PostMenuActivity.class);
                intent.putExtra("menuType", "1");
                startActivityForResult(intent, CODE_REFRESH_ALL);
                break;
            case R.id.my_reply_iv://我的回复
                intent.setClass(mContext, PostMenuActivity.class);
                intent.putExtra("menuType", "2");
                startActivityForResult(intent, CODE_REFRESH_ALL);
                break;
            case R.id.my_like_iv://我的点赞
                intent.setClass(mContext, PostMenuActivity.class);
                intent.putExtra("menuType", "3");
                startActivityForResult(intent, CODE_REFRESH_ALL);
                break;

        }
    }

    private void setTab(boolean flag1, boolean flag2, boolean flag3) {
        all_tv.setSelected(flag1);
        second_tv.setSelected(flag2);
        hall_tv.setSelected(flag3);
        refreshData();
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (mContext != null) {
            mPageNo = 0;
            mStart = 0;
            mDialog.show();
            requestData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case POST_DETAIL_CODE:
                    PostDetailBean bean = (PostDetailBean) data.getSerializableExtra("postBean");
                    String position = data.getStringExtra("position");
                    int pos = Integer.parseInt(position);
                    if (!TextUtils.isEmpty(bean.getIs_agree())) {
                        mList.get(pos).setIs_agree(bean.getIs_agree());
                    }
                    if (!TextUtils.isEmpty(bean.getAgree_count())) {
                        mList.get(pos).setAgree_count(bean.getAgree_count());
                        Logger.i(TAG, "点赞数量" + bean.getAgree_count());
                    }
                    if (!TextUtils.isEmpty(bean.getResponse_count())) {
                        mList.get(pos).setResponse_count(bean.getResponse_count());
                    }
                    mNeighborRvAdapter.notifyDataSetChanged();
                    break;
                case CODE_REFRESH_ALL:
                    refreshData();
                    break;

            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.NEIGHBOR_LIST) {
            refreshData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
