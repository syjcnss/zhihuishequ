package com.ovu.lido.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.WorkOrderDetailInfo;

import java.util.List;

public class WorkOrderDetailLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<WorkOrderDetailInfo> workOrderDetailInfos;

    public interface OnChildViewClickListener{
        void onClick(View view);
    }

    private OnChildViewClickListener onChildViewClickListener;

    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    public WorkOrderDetailLvAdapter(Context mContext, List<WorkOrderDetailInfo> workOrderDetailInfos) {
        this.mContext = mContext;
        this.workOrderDetailInfos = workOrderDetailInfos;
    }

    @Override
    public int getCount() {
        return workOrderDetailInfos == null ? 0 : workOrderDetailInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return workOrderDetailInfos.get(position);
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
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.work_order_detail_lv_item,parent,false);
            holder.line_top = view.findViewById(R.id.line_top);
            holder.line_bottom = view.findViewById(R.id.line_bottom);
            holder.time_tv = view.findViewById(R.id.time_tv);
            holder.type_tv = view.findViewById(R.id.type_tv);
            holder.content_tv = view.findViewById(R.id.content_tv);
            holder.evaluate_tv = view.findViewById(R.id.evaluate_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }


        if (position == 0) {
            holder.line_top.setVisibility(View.INVISIBLE);
            holder.time_tv.setTextColor(Color.parseColor("#3fc03e"));
            holder.type_tv.setTextColor(Color.parseColor("#3fc03e"));
            holder.content_tv.setTextColor(Color.parseColor("#3fc03e"));
        } else {
            holder.line_top.setVisibility(View.VISIBLE);
            holder.time_tv.setTextColor(Color.parseColor("#999999"));
            holder.type_tv.setTextColor(Color.parseColor("#999999"));
            holder.content_tv.setTextColor(Color.parseColor("#999999"));
        }
        if (position == getCount() - 1) {
            holder.line_bottom.setVisibility(View.INVISIBLE);
        } else {
            holder.line_bottom.setVisibility(View.VISIBLE);
        }

//        WorkOrderDetailInfo info = workOrderDetailInfos.get(position);
//        String type = info.getType();
//        if (type.equals("已完成")){
//            holder.evaluate_tv.setVisibility(View.VISIBLE);
//        }else {
//            holder.evaluate_tv.setVisibility(View.INVISIBLE);
//        }
//        holder.evaluate_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onChildViewClickListener.onClick(v);
//            }
//        });
//        holder.time_tv.setText(info.getTime());
//        holder.type_tv.setText(type);
//        holder.content_tv.setText(info.getContent());
        return view;
    }

    class ViewHolder {
        View line_top;
        View line_bottom;
        TextView time_tv;
        TextView type_tv;
        TextView content_tv;
        TextView evaluate_tv;
    }
}
