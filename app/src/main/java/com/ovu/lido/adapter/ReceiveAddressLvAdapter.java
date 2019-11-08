package com.ovu.lido.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.holder.ViewHolder;
import com.ovu.lido.bean.ReceiveAddressInfo;

import java.util.List;

public class ReceiveAddressLvAdapter extends CommonAdapter<ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean> {

    public ReceiveAddressLvAdapter(Context context, int layoutId, List<ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean) {
        holder.setText(R.id.contact_name_tv,bean.getContact_name());
        holder.setText(R.id.contact_phone_tv,bean.getContact_phone());
        StringBuilder stringBuilder = new StringBuilder();
        holder.setText(R.id.address_tv, stringBuilder.append(bean.getProvince_name()).append(bean.getCity_name()).append(bean.getDistrict_name()).append(bean.getDetail()).toString());
        CheckBox checkBox = holder.getView(R.id.is_default_cb);
        checkBox.setChecked(bean.getIs_default() == 1);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onChildClickListener.onCheckedChanged(isChecked,bean);
            }
        });

        holder.getView(R.id.delete_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClickListener.onDeleteClick(bean.getId());
            }
        });
        holder.getView(R.id.edit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClickListener.onEditClick(bean);
            }
        });
        holder.getView(R.id.item_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClickListener.onItemClickListener(bean);
            }
        });
    }

    public interface OnChildClickListener{
        void onDeleteClick(String addressId);
        void onEditClick(ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean);
        void onCheckedChanged(boolean isChecked,ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean);
        void onItemClickListener(ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean);
    }

    private OnChildClickListener onChildClickListener;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }
}
