package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.JoinQuestionListInfo;

import java.util.List;

public class JoinQuestionLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<JoinQuestionListInfo.DataBean> questionListInfos;

    public JoinQuestionLvAdapter(Context mContext, List<JoinQuestionListInfo.DataBean> questionListInfos) {
        this.mContext = mContext;
        this.questionListInfos = questionListInfos;
    }

    @Override
    public int getCount() {
        return questionListInfos == null ? 0 : questionListInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return questionListInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        QuestionLvViewHolder holder;
        if (view == null){
            holder = new QuestionLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.join_question_lv_item,parent,false);
            holder.item_text = view.findViewById(R.id.item_text);
            view.setTag(holder);
        }else {
            holder = (QuestionLvViewHolder) view.getTag();
        }
        JoinQuestionListInfo.DataBean info = questionListInfos.get(position);
        holder.item_text.setText(info.getCheckTypeName() + ":" + info.getContent());
        return view;
    }

    class QuestionLvViewHolder{
        TextView item_text;
    }
}
