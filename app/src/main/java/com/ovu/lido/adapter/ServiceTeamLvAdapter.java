package com.ovu.lido.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ovu.lido.R;
import com.ovu.lido.bean.ServiceTeamInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.TaskHelper;
import com.ovu.lido.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class ServiceTeamLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<ServiceTeamInfo.InfoListBean> teamInfos;
    private int pos;

    public ServiceTeamLvAdapter(Context mContext, List<ServiceTeamInfo.InfoListBean> teamInfos) {
        this.mContext = mContext;
        this.teamInfos = teamInfos;
    }

    @Override
    public int getCount() {
        return teamInfos == null ? 0 : teamInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return teamInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.service_team_lv_item, parent, false);
            holder.pic_iv = view.findViewById(R.id.pic_iv);
            holder.name_tv = view.findViewById(R.id.name_tv);
            holder.post_tv = view.findViewById(R.id.post_tv);
            holder.satisfaction_tv = view.findViewById(R.id.satisfaction_tv);
            holder.radio_group = view.findViewById(R.id.radio_group);
            holder.is_agree_btn = view.findViewById(R.id.is_agree_btn);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ServiceTeamInfo.InfoListBean infoListBean = teamInfos.get(position);

        Glide.with(mContext).load(infoListBean.getUrl()).into(holder.pic_iv);//图片
        holder.name_tv.setText(infoListBean.getName());//姓名
        holder.post_tv.setText(infoListBean.getPosition());//职位
        holder.satisfaction_tv.setText(infoListBean.getPercentageSatisfactions());
        String type = infoListBean.getType();
        switch (type) {
            case "0"://不满意
                holder.radio_group.check(R.id.neighbor_rb);
                break;
            case "1"://一般
                holder.radio_group.check(R.id.soso_rb);
                break;
            case "2"://满意
                holder.radio_group.check(R.id.good_rb);
                break;

        }
        boolean isVisible = infoListBean.getCreateName().equals("1");
        holder.is_agree_btn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        holder.is_agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(mContext, "本月已评价");
            }
        });

        pos = position;

        if (TextUtils.isEmpty(type)) {
            holder.radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String satisfaction_states = "";
                    switch (checkedId) {
                        case R.id.good_rb:
                            satisfaction_states = "2";
                            break;
                        case R.id.soso_rb:
                            satisfaction_states = "1";
                            break;
                        case R.id.neighbor_rb:
                            satisfaction_states = "0";
                            break;
                    }
                    userReviews(infoListBean.getId(), satisfaction_states);
                }
            });
        }

        return view;
    }

    /**
     * 用户对服务进行（满意、一般、不满意）评价
     */
    private void userReviews(int propertyTeamId, String satisfaction_states) {
        OkHttpUtils.post()
                .url(Constant.USER_REVIEWS_URL)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("property_team_id", String.valueOf(propertyTeamId))
                .addParams("satisfaction_states", satisfaction_states)
                .addParams("is_assess", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangw", "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("wangw", "onResponse: ");
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        String errorCode = info.getErrorCode();
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            Intent intent = new Intent(mContext, EnterMethodActivity.class);
                            TaskHelper.finishAffinity((Activity) mContext, intent);
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.token_error), Toast.LENGTH_SHORT).show();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            teamInfos.get(pos).setCreateName("1");
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, info.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    class ViewHolder {
        ImageView pic_iv;
        TextView name_tv;
        TextView post_tv;
        TextView satisfaction_tv;
        RadioGroup radio_group;
        Button is_agree_btn;
    }

}
