package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.GiveGoodBean;
import com.ovu.lido.bean.MyResonseBean;
import com.ovu.lido.ui.PersonalActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class PostMenuAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private ArrayList<MyResonseBean.DataBean.ListBean> mList;
    private static String TAG = "NeighDetailAdapter";
    private boolean mFlag = true;


    public PostMenuAdapter(Context context, ArrayList<MyResonseBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;

    }


    public void setList(ArrayList<MyResonseBean.DataBean.ListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_neighborhood_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_content.setText(mList.get(position).getContent());
        viewHolder.tv_title.setText(mList.get(position).getTitle());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_head)
                .error(R.drawable.default_head)
                .transform(new GlideCircleTransform(mContext));
        Glide.with(mContext)
                .load(mList.get(position).getIcon_url())
                .apply(options)
                .into(viewHolder.iv_head);
        viewHolder.tv_name.setText(mList.get(position).getNick_name());
        viewHolder.tv_date.setText(mList.get(position).getCreate_time().substring(0, 10));
        if (mList.get(position).getInfo_typename() != null) {
            if (mList.get(position).getInfo_typename().equals("2")) {
                viewHolder.tv_type.setText("二手市场");
            } else if (mList.get(position).getInfo_typename().equals("4")) {
                viewHolder.tv_type.setText("议事大厅");
            }
        }
        viewHolder.tv_comment_count.setText(TextUtils.isEmpty(mList.get(position).getResponse_count()) ? "0" : mList.get(position).getResponse_count());
        viewHolder.tv_good_count.setText(mList.get(position).getAgree_count().equals("") ? "0" : mList.get(position).getAgree_count());


        if (mList.get(position).getIs_agree().equals("1")) {
            viewHolder.iv_good_state.setSelected(true);
        } else {
            viewHolder.iv_good_state.setSelected(false);
        }
        viewHolder.iv_good_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = mList.get(position).getIs_agree().equals("1") ? "10" : "11";
                OkHttpUtils.post()
                        .tag(this)
                        .url(HttpAddress.GIVE_GOOD)
                        .addParams("info_id", mList.get(position).getId())
                        .addParams("token", AppPreference.I().getString("token", ""))
                        .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                        .addParams("operate_type", info)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Logger.i(TAG, response);
                                if (AppUtil.isToken(response)) {
                                    AppUtil.toLogin(mContext);
                                    return;
                                }
                                GiveGoodBean bean = GsonUtil.GsonToBean(response, GiveGoodBean.class);
                                if (bean.getErrorCode().equals("0000")) {
                                    int count = mList.get(position).getIs_agree().equals("0") ? 1 : -1;
                                    if (count == 1) {
                                        mList.get(position).setIs_agree("1");
                                    } else {
                                        mList.get(position).setIs_agree("0");
                                    }
                                    mList.get(position).setAgree_count((Integer.parseInt(mList.get(position).getAgree_count())) + count + "");
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
        viewHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemClick(position);
            }
        });
        viewHolder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mFlag) {
                    return;
                }
                Intent intent = new Intent(mContext, PersonalActivity.class);
                intent.putExtra("personId", mList.get(position).getResident_id());
                intent.putExtra("typeId", mList.get(position).getInfo_type_id());
                mContext.startActivity(intent);
            }
        });
        ArrayList<String> mImageList = new ArrayList<>();
        mImageList.clear();
        if (!TextUtils.isEmpty(mList.get(position).getImgs())) {
            String[] imgUrl = mList.get(position).getImgs().split(",");
            for (int i = 0; i < imgUrl.length; i++) {
                mImageList.add(imgUrl[i]);
            }
            viewHolder.mGridAdapter.setList(mImageList);
        } else {
            viewHolder.mGridAdapter.setList(mImageList);
        }
        return convertView;
    }


    @Override
    public void onClick(final View v) {


    }

    public void setFlag(boolean flag) {
        this.mFlag = flag;
        notifyDataSetChanged();
    }


    public interface AdapterOnItemClickListener {
        void itemClick(int pos);
    }

    public AdapterOnItemClickListener mListener;

    public void setAdapterOnItemClickListener(AdapterOnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    class ViewHolder {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_date;
        TextView tv_type;
        TextView tv_content;
        TextView tv_title;
        // RecyclerView rv_img = convertView.findViewById(R.id.rv_neigh_img);
        GridView rv_img;
        ImageView iv_good_state;
        TextView tv_comment_count;
        TextView tv_good_count;
        RelativeLayout rl;
        ImgGridAdapter mGridAdapter;

        public ViewHolder(View view) {
            iv_head = view.findViewById(R.id.iv_neighbourhood_head);
            tv_name = view.findViewById(R.id.tv_neigh_name);
            tv_date = view.findViewById(R.id.tv_neigh_date);
            tv_type = view.findViewById(R.id.tv_neigh_type);
            tv_content = view.findViewById(R.id.tv_neigh_content);
            tv_title = view.findViewById(R.id.tv_neigh_title);
            // RecyclerView rv_img = convertView.findViewById(R.id.rv_neigh_img);
            rv_img = view.findViewById(R.id.rv_neigh_img);
            iv_good_state = view.findViewById(R.id.iv_good_state);
            tv_comment_count = view.findViewById(R.id.tv_comment_count);
            tv_good_count = view.findViewById(R.id.tv_good_count);
            rl = view.findViewById(R.id.rl);
            //将adapter定义在此，优化滑动效果(核心)
            mGridAdapter = new ImgGridAdapter(mContext);
            //在此设置适配器，数据源在getView中添加(核心)
            rv_img.setAdapter(mGridAdapter);
            view.setTag(this);
        }
    }
}
