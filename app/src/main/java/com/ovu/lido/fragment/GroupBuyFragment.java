package com.ovu.lido.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.GroupByBean;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.ui.OrderDetailedActivity;
import com.ovu.lido.ui.ProductDetailedActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class GroupBuyFragment extends Fragment {
    @BindView(R.id.lv_group_buy)
    ListView mLv_group_buy;
    private CommonAdapter<GroupByBean.DataBean> mCommonAdapter;
    private ArrayList<GroupByBean.DataBean> mList = new ArrayList<>();
    private Context mContext;
    private Dialog mDialog;
    private Unbinder unbinder;
    private String TAG = "wangw";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_buy, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        loadData();
        return view;
    }

    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(getActivity());
        mCommonAdapter = new CommonAdapter<GroupByBean.DataBean>(getActivity(), mList, R.layout.lv_group_buy_item) {
            @Override
            protected void convert(ViewHolder viewHolder, GroupByBean.DataBean item, final int position) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.group_buy_image_error)
                        .error(R.drawable.group_buy_image_error);
                Glide.with(mContext)
                        .load(item.getCommodity_img())
                        .apply(requestOptions)
                        .into((ImageView) viewHolder.getView(R.id.iv_content));
                TextView textView = viewHolder.getView(R.id.tv_own_price);
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.setText(R.id.tv_name, item.getG_p_name());
                viewHolder.setText(R.id.tv_price, "¥" + AppUtil.TwoPoint(item.getG_p_price()));
                viewHolder.setText(R.id.tv_own_price, "¥" + AppUtil.TwoPoint(item.getOriginal_price()));
                if (item.isIs_end()) {
                    viewHolder.getView(R.id.tv_join).setSelected(false);
                    viewHolder.setText(R.id.tv_join, "已结束");
                    viewHolder.getView(R.id.tv_join).setEnabled(false);
                } else {
                    viewHolder.getView(R.id.tv_join).setEnabled(true);
                    if (item.isIs_enroll()) {
                        viewHolder.getView(R.id.tv_join).setSelected(false);
                        viewHolder.setText(R.id.tv_join, "已参团");
                    } else {
                        if (item.getEnrollCount() >= item.getEnroll_limit()) {
                            viewHolder.getView(R.id.tv_join).setSelected(false);
                            viewHolder.setText(R.id.tv_join, "人数已满");
                            viewHolder.getView(R.id.tv_join).setEnabled(false);
                        } else {
                            viewHolder.getView(R.id.tv_join).setEnabled(true);
                            viewHolder.setText(R.id.tv_join, "参团");
                            viewHolder.getView(R.id.tv_join).setSelected(true);
                        }
                    }
                }
                viewHolder.getView(R.id.tv_join).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderDetailedActivity.class);
                        intent.putExtra("product", mList.get(position));
                        startActivity(intent);
                    }
                });

            }
        };
        mLv_group_buy.setAdapter(mCommonAdapter);
    }

    public void loadData() {
        mDialog.show();
        getData();
    }

    public void getData() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.GET_GROUP_PURCHASE)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        GroupByBean bean = GsonUtil.GsonToBean(response, GroupByBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            mList.addAll(bean.getData());
                            mCommonAdapter.setDatas(mList);
                        } else {
                          ToastUtil.show(mContext,bean.getErrorMsg());
                        }
                    }
                });

    }

    private void setListener() {
        mLv_group_buy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProductDetailedActivity.class);
                intent.putExtra("groupById", mList.get(position).getId() + "");
                startActivity(intent);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.GROUP_BUY_LIST) {
            getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }


}

