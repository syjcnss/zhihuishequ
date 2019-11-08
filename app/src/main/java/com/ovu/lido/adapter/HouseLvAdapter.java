package com.ovu.lido.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.ChangeHousingBean;

import java.util.List;

public class HouseLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChangeHousingBean.ExtraEstatesBean> extraEstatesBeans;

    public HouseLvAdapter(Context mContext, List<ChangeHousingBean.ExtraEstatesBean> extraEstatesBeans) {
        this.mContext = mContext;
        this.extraEstatesBeans = extraEstatesBeans;
    }

    @Override
    public int getCount() {
        return extraEstatesBeans == null ? 0 : extraEstatesBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return extraEstatesBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.lv_change_housing_item,parent,false);
            holder = new ViewHolder();
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_housing = view.findViewById(R.id.tv_housing);
            holder.tv_floor = view.findViewById(R.id.tv_floor);
            holder.iv_choose = view.findViewById(R.id.iv_choose);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ChangeHousingBean.ExtraEstatesBean dataBean = extraEstatesBeans.get(position);
        holder.tv_name.setText(dataBean.getHuman_name());
        holder.tv_housing.setText(dataBean.getCommunity_name());
        holder.tv_floor.setText(String.valueOf(dataBean.getRoom_no()));
        String identity = dataBean.getIdentity();
        holder.iv_choose.setVisibility(TextUtils.equals(identity,"0") ? View.VISIBLE : View.GONE);
        return view;
    }

    class ViewHolder {
        TextView tv_name;
        TextView tv_housing;
        TextView tv_floor;
        ImageView iv_choose;
    }
}
