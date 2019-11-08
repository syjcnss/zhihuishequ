package com.ovu.lido.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.ovu.lido.R;
import com.ovu.lido.bean.FamilySituationInfo;
import com.ovu.lido.util.ViewHelper;

import java.util.List;

public class KinsmanSwipeCardsAdapter extends BaseCardAdapter {
    private Context mContext;
    private List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanListBeans;

    public KinsmanSwipeCardsAdapter(Context mContext, List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanListBeans) {
        this.mContext = mContext;
        this.kinsmanListBeans = kinsmanListBeans;
    }

    public void setData(List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanListBeans){
        this.kinsmanListBeans = kinsmanListBeans;
    }

    @Override
    public int getCount() {
        return kinsmanListBeans == null ? 0 : kinsmanListBeans.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if (kinsmanListBeans == null || kinsmanListBeans.size() == 0) {
            return;
        }
        TextView kinsman_name_tv = cardview.findViewById(R.id.kinsman_name_tv);
        TextView work_unit_tv = cardview.findViewById(R.id.work_unit_tv);
        TextView birth_date_tv = cardview.findViewById(R.id.birth_date_tv);
        TextView link_tel_tv =  cardview.findViewById(R.id.link_tel_tv);
        TextView idcard_tv = cardview.findViewById(R.id.idcard_tv);
        TextView kinsman_relationship_tv = cardview.findViewById(R.id.kinsman_relationship_tv);

        FamilySituationInfo.DataBean.KinsmanListBean bean = kinsmanListBeans.get(position);
        kinsman_name_tv.setText(bean.getKinsman_name());
        work_unit_tv.setText(bean.getWork_unit());
        birth_date_tv.setText( ViewHelper.getDisplayData(bean.getBirth_date(),"yyyy-MM-dd","yyyy.MM.dd"));
        link_tel_tv.setText(bean.getLink_tel());
        idcard_tv.setText(bean.getIdentification_number());
        kinsman_relationship_tv.setText(bean.getKinsman_relationship());

    }

    /**
     * 如果可见的卡片数是3，则可以不用实现这个方法
     * @return
     */
    @Override
    public int getVisibleCardCount() {
        return 3;
    }
}
