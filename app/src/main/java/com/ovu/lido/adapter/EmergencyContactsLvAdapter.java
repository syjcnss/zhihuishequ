package com.ovu.lido.adapter;

import android.content.Context;

import com.ovu.lido.R;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.EmergencyContactsInfo;
import com.ovu.lido.bean.FamilySituationInfo;

import java.util.List;

public class EmergencyContactsLvAdapter extends CommonAdapter<FamilySituationInfo.DataBean.EmergencyContactsBean> {
    public EmergencyContactsLvAdapter(Context context, List<FamilySituationInfo.DataBean.EmergencyContactsBean> data, int itemLayoutId) {
        super(context, data, itemLayoutId);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FamilySituationInfo.DataBean.EmergencyContactsBean item, int position) {
        viewHolder.setText(R.id.contact_name_tv,item.getContact_name());
        viewHolder.setText(R.id.contact_tel_tv,item.getContact_tel());

    }
}
