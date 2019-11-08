package com.ovu.lido.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.ovu.lido.R;
import com.ovu.lido.bean.FamilySituationInfo;
import com.ovu.lido.util.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditContactLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<FamilySituationInfo.DataBean.EmergencyContactsBean> contactsInfos;
    private int index = -1;

    public EditContactLvAdapter(Context mContext, List<FamilySituationInfo.DataBean.EmergencyContactsBean> contactsInfos) {
        this.mContext = mContext;
        this.contactsInfos = contactsInfos;
    }
//
//    public void deleteItem(int pos) {
//        Logger.e("wangw", pos + "删除的position");
//        contactsInfos.remove(pos);
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return contactsInfos == null ? 0 : contactsInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        EditContactViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.emergency_contacts_edit_item, parent, false);
            holder = new EditContactViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (EditContactViewHolder) view.getTag();
        }

        EditText contact_name_et = holder.contact_name_et;
        EditText contact_tel_et = holder.contact_tel_et;
        contact_name_et.setText(contactsInfos.get(position).getContact_name());
        contact_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextWatcher.onNameChange(position,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contact_tel_et.setText(contactsInfos.get(position).getContact_tel());
        contact_tel_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextWatcher.onTelChange(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public interface OnTextWatcher {
        void onNameChange(int position, String contactName);
        void onTelChange(int position, String tel);
    }

    private OnTextWatcher onTextWatcher;

    public void setOnTextWatcher(OnTextWatcher onNameTextWatcher) {
        this.onTextWatcher = onNameTextWatcher;
    }

    class EditContactViewHolder {
        @BindView(R.id.contact_name_et)
        EditText contact_name_et;
        @BindView(R.id.contact_tel_et)
        EditText contact_tel_et;

        EditContactViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
