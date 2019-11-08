package com.ovu.lido.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.fragment.BasicInformationFragment;
import com.ovu.lido.fragment.InspectionRoomFragment;
import com.ovu.lido.fragment.JoinListFragment;
import com.ovu.lido.fragment.JoinWebFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 贴心管家 -- 入伙管理
 */
public class OccupationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.radio_join)
    RadioGroup mRadioGroup;
    @BindView(R.id.top_text)
    TextView top_text;
    private FragmentManager mSupportFragmentManager;
    //当前显示的fragment
    private Fragment mCurrntShowFragment;
    private BasicInformationFragment mBasicInformationFragment;
    private InspectionRoomFragment mInspectionRoomFragment;
    private int is_join;// 0未提交 1已提交
    private int is_check;// 0未提交 1已提交
    private String line;
    private JoinWebFragment mJoinWebFragment;
    private JoinListFragment mJoinListFragment;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true,0.2f)
                .init();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_occupation;
    }

    @Override
    protected void initView() {
        super.initView();
        is_join = getIntent().getIntExtra("is_join", 0);
        is_check = getIntent().getIntExtra("is_check", 0);
        line = getIntent().getStringExtra("line");
        mSupportFragmentManager = getSupportFragmentManager();
        loadFragment();
        mRadioGroup.check(R.id.btn_info);

    }

    private void loadFragment() {
        mBasicInformationFragment = new BasicInformationFragment();
        mInspectionRoomFragment = new InspectionRoomFragment();
        mJoinWebFragment = JoinWebFragment.newInstance(line);
        mJoinListFragment = new JoinListFragment();
        getJoinMsg();
    }

    private void getJoinMsg() {
        switch (is_join){
            case 0:
                top_text.setVisibility(View.GONE);
                ctrlFragment(mBasicInformationFragment);
                break;
            case 1:
                top_text.setVisibility(View.GONE);
                ctrlFragment(mJoinWebFragment);
                break;
        }
    }

    private void getCheckMsg() {
        switch (is_check){
            case 0:
                top_text.setVisibility(View.VISIBLE);
                ctrlFragment(mInspectionRoomFragment);
                break;
            case 1:
                top_text.setVisibility(View.GONE);
                ctrlFragment(mJoinListFragment);
                break;
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.back_iv,R.id.top_text})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.top_text:
                doTopTextView();
                break;
        }
    }

    private void doTopTextView() {
        switch (status) {
            case 0:
                // 编辑-->取消
                top_text.setText("取消");
                status = 2;
                mInspectionRoomFragment.isShowSelectBtn(true);
                break;
            case 1:
                // 删除-->编辑
                top_text.setText("编辑");
                status = 0;
                mInspectionRoomFragment.removeView();
                break;
            case 2:
                // 取消-->编辑
                top_text.setText("编辑");
                status = 0;
                mInspectionRoomFragment.isShowSelectBtn(false);
                break;
        }
    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.btn_info://基本信息
                getJoinMsg();
                break;
            case R.id.btn_inspect://入伙验房
                getCheckMsg();
                break;
        }
    }

    /**
     * @param fragment 当前要显示的fragment
     */
    private void ctrlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();

        if (null != mCurrntShowFragment) {
            fragmentTransaction.hide(mCurrntShowFragment);
        }

        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.join_content, fragment);

        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();
        mCurrntShowFragment = fragment;
    }

    public int status = 0;// 编辑状态 0 编辑 1删除 2取消
    public void setStatus(int status) {
        this.status = status;
        switch (status) {
            case 0:
                top_text.setText("编辑");
                break;
            case 1:
                top_text.setText("删除");
                break;
            case 2:
                top_text.setText("取消");
                break;

            default:
                break;
        }

    }

}
