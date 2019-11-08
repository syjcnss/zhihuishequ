package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.FamilyMemberInfo;

import java.util.List;

public class FamilyMemberListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FamilyMemberInfo> familyMemberInfos;

    public FamilyMemberListAdapter(Context mContext, List<FamilyMemberInfo> familyMemberInfos) {
        this.mContext = mContext;
        this.familyMemberInfos = familyMemberInfos;
    }

    @Override
    public int getCount() {
        return familyMemberInfos == null ? 0 : familyMemberInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return familyMemberInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        FamilyViewHolder holder;
        if (view == null){
            holder = new FamilyViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.family_member_lv_item,parent,false);
            holder.item_name = view.findViewById(R.id.item_name);
            holder.item_delete = view.findViewById(R.id.item_delete);
            view.setTag(holder);
        }else {
            holder = (FamilyViewHolder) view.getTag();
        }
        holder.item_name.setText(familyMemberInfos.get(position).getFamilyMemberName());
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteBtnClickListener.onClick(v,position);
            }
        });
        return view;
    }

    class FamilyViewHolder{
        TextView item_name;
        Button item_delete;
    }

    public interface OnDeleteBtnClickListener{
        void onClick(View view, int position);
    }

    public OnDeleteBtnClickListener mOnDeleteBtnClickListener;

    public void setmOnDeleteBtnClickListener(OnDeleteBtnClickListener mOnDeleteBtnClickListener) {
        this.mOnDeleteBtnClickListener = mOnDeleteBtnClickListener;
    }
}
