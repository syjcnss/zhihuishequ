package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.ClauseLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ClauseInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 装修协议
 */
public class ClauseActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.clause_wv)
    WebView clause_wv;
    @BindView(R.id.clause_lv)
    ListView clause_lv;

    private List<ClauseInfo.DataBean.ListDataBean> dataBeanList = new ArrayList<>();
    private ClauseLvAdapter mClauseLvAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_clause;
    }

    @Override
    protected void initView() {
        super.initView();
        clause_wv.getSettings().setJavaScriptEnabled(true);
        clause_wv.getSettings().setDefaultTextEncodingName("utf-8");
        clause_wv.getSettings().setSupportZoom(false);
        mClauseLvAdapter = new ClauseLvAdapter(mContext, dataBeanList);
        clause_lv.setAdapter(mClauseLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        clause_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        OkHttpUtils.post()
                .url(Constant.CLAUSE_URL)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ClauseInfo info = GsonUtil.GsonToBean(response, ClauseInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            clause_wv.loadData(info.getData().getDesc(), "text/html; charset=UTF-8", null);
                            dataBeanList.addAll(info.getData().getListData());
                            mClauseLvAdapter.notifyDataSetChanged();
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(mContext, ClauseDetailActivity.class).putExtra("id", String.valueOf(dataBeanList.get(position).getId())));
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
