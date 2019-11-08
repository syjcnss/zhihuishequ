package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.ovu.lido.R;

import java.util.List;

public class SurveyOptionsLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> optionNames;

    public SurveyOptionsLvAdapter(Context mContext, List<String> optionNames) {
        this.mContext = mContext;
        this.optionNames = optionNames;
    }

    @Override
    public int getCount() {
        return optionNames == null ? 0 : optionNames.size();
    }

    @Override
    public Object getItem(int position) {
        return optionNames.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.survey_options_lv_item,parent,false);
            holder = new ViewHolder();
            holder.survey_options_cb = view.findViewById(R.id.survey_options_cb);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.survey_options_cb.setText(optionNames.get(position));
        return view;
    }

    class ViewHolder {
        CheckBox survey_options_cb;
    }
}
