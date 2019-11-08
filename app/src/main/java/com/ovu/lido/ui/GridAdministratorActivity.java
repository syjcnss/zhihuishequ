package com.ovu.lido.ui;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.GridKeeperLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.GridAdministratorInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 -- 网格管理员
 */
public class GridAdministratorActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.grid_keeper_lv)
    ListView grid_keeper_lv;
    @BindView(R.id.no_data_ll)
    LinearLayout no_data_ll;

    private List<GridAdministratorInfo.ListBean> listBeans = new ArrayList<>();
    private GridKeeperLvAdapter mGridKeeperLvAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_grid_administrator;
    }

    @Override
    protected void initView() {
        super.initView();
        mGridKeeperLvAdapter = new GridKeeperLvAdapter(mContext, listBeans);
        grid_keeper_lv.setAdapter(mGridKeeperLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mGridKeeperLvAdapter.setOnPhoneBtnClickListener(new GridKeeperLvAdapter.OnPhoneBtnClickListener() {
            @Override
            public void onClick(View view, int position) {
                String tel = listBeans.get(position).getGridkeeper_tel();
                ViewHelper.toDialView(mContext,tel);
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        OkHttpUtils.get()
                .url(Constant.GET_GRID_KEEPER_URL)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            GridAdministratorInfo info = GsonUtil.GsonToBean(response, GridAdministratorInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                listBeans.addAll(info.getList());
                                mGridKeeperLvAdapter.notifyDataSetChanged();
                                setNoDataView();
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    private void setNoDataView() {
        int size = listBeans == null ? 0 : listBeans.size();
        no_data_ll.setVisibility(size > 0 ? View.GONE : View.VISIBLE);
    }

    @OnClick({R.id.back_iv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
