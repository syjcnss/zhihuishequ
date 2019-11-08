package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.SatisfactionSurveyHistoryInfo;

import java.util.List;

/**
 * 在线社区 -- 满意度调查 -- 历史
 */
public class SatisfactionSurveyHistoryLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<SatisfactionSurveyHistoryInfo.InfoHistorylistBean> historyInfos;

    public SatisfactionSurveyHistoryLvAdapter(Context mContext, List<SatisfactionSurveyHistoryInfo.InfoHistorylistBean> historyInfos) {
        this.mContext = mContext;
        this.historyInfos = historyInfos;
    }

    @Override
    public int getCount() {
        return historyInfos == null ? 0 : historyInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return historyInfos.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.satisfaction_survey_history_lv_item,parent,false);
            holder.title_tv = view.findViewById(R.id.title_tv);
            holder.time_tv = view.findViewById(R.id.time_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title_tv.setText(historyInfos.get(position).getVote_time2());
        holder.time_tv.setText(historyInfos.get(position).getVote_time());
        return view;
    }

    class ViewHolder {
        TextView title_tv;
        TextView time_tv;
    }
}
