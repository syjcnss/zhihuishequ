package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.ActivityBean;
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

public class MyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.lv_my_activity)
    ListView mLv_activity;
    private CommonAdapter<ActivityBean.DataBean> mCommonAdapter;
    private ArrayList<ActivityBean.DataBean> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_activity;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        mCommonAdapter = new CommonAdapter<ActivityBean.DataBean>(this, mList, R.layout.lv_activity_item) {
            @Override
            protected void convert(ViewHolder viewHolder, ActivityBean.DataBean item, final int position) {
                viewHolder.setText(R.id.tv_title, item.getActivity_name());
                RequestOptions requestOptions = new RequestOptions()
                        .error(R.drawable.activity_image_error)
                        .placeholder(R.drawable.activity_image_error);
                Glide.with(mContext)
                        .load(item.getActivity_img())
                        .apply(requestOptions)
                        .into((ImageView) viewHolder.getView(R.id.iv_content));
                if (item.isIs_end()) {
                    viewHolder.getView(R.id.iv_state).setSelected(false);
                    viewHolder.setText(R.id.tv_join, "已结束");
                    viewHolder.getView(R.id.tv_join).setSelected(false);
                    viewHolder.getView(R.id.tv_join).setEnabled(false);
                } else {
                    viewHolder.getView(R.id.iv_state).setSelected(true);
                    viewHolder.getView(R.id.tv_join).setSelected(true);
                    viewHolder.getView(R.id.tv_join).setEnabled(true);
                    if (item.isIs_enroll()) {
                        viewHolder.setText(R.id.tv_join, "已参加");
                        viewHolder.getView(R.id.tv_join).setSelected(false);
                    } else {
                        viewHolder.setText(R.id.tv_join, "去参加");
                        viewHolder.getView(R.id.tv_join).setSelected(true);
                    }


                }
                viewHolder.setText(R.id.tv_num, item.getEnrollCount() + "/" + item.getEnroll_limit());
            }
        };
        mLv_activity.setAdapter(mCommonAdapter);

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
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.MY_ACTIVITY_LIST)
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
                            AppUtil.toLogin(MyActivity.this);
                            return;
                        }
                        ActivityBean bean = GsonUtil.GsonToBean(response, ActivityBean.class);
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
        mLv_activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyActivity.this, ActivityDetailedActivity.class);
                intent.putExtra("activityId", mList.get(position).getId() + "");
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
}
