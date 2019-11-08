package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.PaymentInfo;

import java.util.List;

public class PaymentAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<PaymentInfo.DataBean.ListItemBean> paymentInfos;

    public PaymentAdapter(Context mContext, List<PaymentInfo.DataBean.ListItemBean> paymentInfos) {
        this.mContext = mContext;
        this.paymentInfos = paymentInfos;
    }

    @Override
    public int getGroupCount() {
        return paymentInfos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return paymentInfos.get(groupPosition).getBillList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return paymentInfos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return paymentInfos.get(groupPosition).getBillList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.payment_elv_group_item, parent, false);
        TextView group_title_tv = view.findViewById(R.id.group_title_tv);
        TextView group_describe_tv = view.findViewById(R.id.group_describe_tv);
        TextView group_amount_tv = view.findViewById(R.id.group_amount_tv);
        ImageView arrow = view.findViewById(R.id.arrow);
        group_title_tv.setText(paymentInfos.get(groupPosition).getType_name());
        group_amount_tv.setText(String.valueOf(paymentInfos.get(groupPosition).getSumAmountMany()));

        if (isExpanded){
            arrow.setImageResource(R.drawable.down_arrow);
        }else {
            arrow.setImageResource(R.drawable.right_arrow);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.payment_elv_child_item,parent,false);
        TextView child_time_tv = view.findViewById(R.id.child_time_tv);
        TextView child_money_tv = view.findViewById(R.id.child_money_tv);
        ImageView btn_child_img = view.findViewById(R.id.btn_child_img);
        PaymentInfo.DataBean.ListItemBean.BillListBean order = paymentInfos.get(groupPosition).getBillList().get(childPosition);
        btn_child_img.setSelected(order.isIs_select());
        child_time_tv.setText(order.getBegin_time()+"至"+order.getEnd_time());
        child_money_tv.setText("需缴纳"+ order.getAmount() + "元");
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
