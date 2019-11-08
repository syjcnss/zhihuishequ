package com.ovu.lido.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.MessageInfo;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MessageInfo.DataBean> messageInfos;

    public MessageListAdapter(Context mContext, List<MessageInfo.DataBean> messageInfos) {
        this.mContext = mContext;
        this.messageInfos = messageInfos;
    }

    @Override
    public int getCount() {
        return messageInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return messageInfos.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.message_list_item,parent,false);
            holder = new ViewHolder();
            holder.message_title = view.findViewById(R.id.message_title);
            holder.message_time = view.findViewById(R.id.message_time);
            holder.message_content = view.findViewById(R.id.message_content);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        MessageInfo.DataBean bean = messageInfos.get(position);
        holder.message_title.setText(getMessageTitle(bean));
        holder.message_time.setText(bean.getMessage_time());
        holder.message_content.setText(bean.getMessage_content());

        return view;
    }

    @NonNull
    private String getMessageTitle(MessageInfo.DataBean bean) {
        int message_type = bean.getMessage_type();
        String message_title = "未知";
        switch (message_type){
            case 1:
                message_title = "缴费状态";
                break;
            case 3:
                message_title = "通知信息";
                break;
            case 4:
                message_title = "活动信息";
                break;
            case 5:
                message_title = "充值";
        }
        return message_title;
    }

    class ViewHolder {
        TextView message_title;
        TextView message_time;
        TextView message_content;
    }
}
