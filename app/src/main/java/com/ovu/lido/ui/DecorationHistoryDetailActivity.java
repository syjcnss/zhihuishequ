package com.ovu.lido.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.WorkOrderDetailRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.DecorationHistoryDetailInfo;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 验收详情
 */
public class DecorationHistoryDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.pic_rv)
    RecyclerView pic_rv;
    @BindView(R.id.applytime)
    TextView applytime;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.comany_name)
    TextView comany_name;
    @BindView(R.id.feed)
    TextView feed;
    private WorkOrderDetailRvAdapter mRvAdapter;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_decoration_history_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        pic_rv.setLayoutManager(layoutManager);
        //问题2、ScrollView嵌套RecyclerView后滑动很缓慢，不流畅，没有惯性
        pic_rv.setHasFixedSize(true);
        pic_rv.setNestedScrollingEnabled(false);

    }

    @Override
    protected void loadData() {
        super.loadData();
        int id = getIntent().getIntExtra("id", -1);
        OkHttpUtils.get()
                .url(Constant.GET_DECORATION_DETAIL)
                .addParams("id", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "装修列表详情数据: " + response);
                        DecorationHistoryDetailInfo info = GsonUtil.GsonToBean(response, DecorationHistoryDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            DecorationHistoryDetailInfo.DataBean data = info.getData();
                            if (data != null){
                                refreshView(data);
                                String certificateUrl = data.getCertificateUrl();
                                if (certificateUrl != null) {
                                    String[] pic_url = certificateUrl.split(",");
                                    mRvAdapter = new WorkOrderDetailRvAdapter(mContext, pic_url);
                                    pic_rv.setAdapter(mRvAdapter);
                                    mRvAdapter.notifyDataSetChanged();
                                }
                            }
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    private void refreshView(DecorationHistoryDetailInfo.DataBean entity) {
        //审核状态
//        status.setText(StringUtil.getSpan(this, String.valueOf(entity.getApplyStatus())));
        //申请时间
        applytime.setText(entity.getCreateTime());
        //负责人姓名
        name.setText(entity.getWorkerName());
        //负责人电话
        phone.setText(entity.getWorkerTel());
        //装修公司名称
        comany_name.setText(entity.getDecorationCompany());
        //装修建议
        String suggest = entity.getSuggest();
        if (!TextUtils.equals(suggest,"")) {
            feed.setText(suggest);
        }

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
