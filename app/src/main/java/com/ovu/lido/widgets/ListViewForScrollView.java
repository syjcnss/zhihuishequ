package com.ovu.lido.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ovu.lido.R;

public class ListViewForScrollView extends ListView {
    private View footer;// 底部布局
    private boolean isLoading;// 判断是否正在加载中
    private View no_more_footer;


    public ListViewForScrollView(Context context) {
        super(context);
        initView(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.listview_footer_view, null);
//        no_more_footer = inflater.inflate(R.layout.no_more_layout, null);
        footer.findViewById(R.id.loading_layout).setVisibility(View.GONE);
//        no_more_footer.findViewById(R.id.no_more_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
//        this.addFooterView(no_more_footer);
    }

    /**
     * 加载完成，1.设置标志；2.隐藏footer
     */
    public void loadComplete() {
        if (footer == null) {
            return;
        }
        isLoading = false;
        footer.findViewById(R.id.loading_layout).setVisibility(View.GONE);
    }

    /**
     * 没有更多可以加载的数据，1.设置标志；2.提示用户数据已经全部加载
     */
    public void noMoreDataLoad(){
        if (footer == null) {
            return;
        }
        isLoading = false;
        footer.findViewById(R.id.loading_layout).setVisibility(View.VISIBLE);
        TextView loading_text = footer.findViewById(R.id.loading_text);
        loading_text.setText("已加载全部");
        footer.findViewById(R.id.loading_bar).setVisibility(View.GONE);
    }

    /**
     * 开始加载，1.设置标志；2.显示footer
     */
    public void startLoading() {
        if (footer == null) {
            return;
        }
        isLoading = true;
        footer.findViewById(R.id.loading_layout).setVisibility(VISIBLE);
    }

    public boolean isLoading() {
        return isLoading;
    }
}
