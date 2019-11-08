package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.VoteLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.VoteInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 --- 投票
 */
public class VoteActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.vote_lv)
    ListView vote_lv;

    private List<VoteInfo.VoteListBean> voteListBeans = new ArrayList<>();
    private VoteLvAdapter mVoteLvAdapter;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_vote;
    }

    @Override
    protected void onResume() {
        super.onResume();
        voteListBeans.clear();
        OkHttpUtils.post()
                .url(Constant.QUERY_VOTE_LIST_URL)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            VoteInfo info = GsonUtil.GsonToBean(response, VoteInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                voteListBeans.addAll(info.getVote_list());
                                mVoteLvAdapter.notifyDataSetChanged();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mVoteLvAdapter = new VoteLvAdapter(mContext, voteListBeans);
        vote_lv.setAdapter(mVoteLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        vote_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VoteInfo.VoteListBean listBean = voteListBeans.get(position);
                String vote_id = listBean.getVote_id();
                Intent intent = new Intent();
                if ("1".equals(listBean.getVote_ever())){
                    intent.setClass(mContext,VoteResultActivity.class);
                }else {
                    intent.setClass(mContext,VoteDetailActivity.class);
                }
                intent.putExtra("type","02");
                intent.putExtra("vote_id", vote_id);
                startActivity(intent);
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

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
