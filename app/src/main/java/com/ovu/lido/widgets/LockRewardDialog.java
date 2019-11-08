package com.ovu.lido.widgets;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;

/**
 * 开门奖励弹框
 * 
 * @author zhanghui
 *
 */
public class LockRewardDialog extends Dialog implements View.OnClickListener {

	private Context mContext;
	private AnimationDrawable animaition;

	private String amount;
	private String sponsor;

	private ImageView bg_top;
	private ImageView btn_close;
	private ViewGroup amount_layout;
	private ViewGroup open_layout;
	private TextView text_11;
	private TextView text_22;
	private TextView text_33;
	private ImageView btn_open;
	private TextView tv_sponsor;
	private TextView tv_amount;
	private ImageView iv_icon;

	private int type;
	private String img;

	Timer timer;

	public LockRewardDialog(Context context, String amount, String sponsor, int type, String img) {
		super(context);
		mContext = context;
		this.amount = amount;
		this.sponsor = sponsor;
		this.type = type;
		this.img = img;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_lock_reward);
		Window window = getWindow();
		window.setBackgroundDrawableResource(R.color.transparent);
		window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		setCanceledOnTouchOutside(false);

		bg_top = (ImageView) findViewById(R.id.bg_top);
		btn_open = (ImageView) findViewById(R.id.btn_open);
		btn_close = (ImageView) findViewById(R.id.btn_close);
		amount_layout = (ViewGroup) findViewById(R.id.amount_layout);
		open_layout = (ViewGroup) findViewById(R.id.open_layout);
		text_11 = (TextView) findViewById(R.id.text_11);
		text_22 = (TextView) findViewById(R.id.text_22);
		text_33 = (TextView) findViewById(R.id.text_33);
		tv_sponsor = (TextView) findViewById(R.id.tv_sponsor);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);

		tv_sponsor.setText(sponsor);
		tv_amount.setText(amount);
		Glide.with(mContext)
				.load(img)
				.apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.img_def)
						.error(R.drawable.img_def))
				.into(iv_icon);

		btn_open.setOnClickListener(this);
		btn_close.setOnClickListener(this);

		initView(1);
	}

	private void initView(int type) {

		// type 1打开前 2 打开后

		bg_top.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
		btn_close.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
		amount_layout.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
		text_33.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
		open_layout.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
		text_11.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
		text_22.setVisibility(type == 1 ? View.VISIBLE : View.GONE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_close:
			dismiss();

			break;
		case R.id.btn_open:

			timer = new Timer();
			timer.schedule(new RemindTask(), 500);
			break;

		default:
			break;
		}

	}

	class RemindTask extends TimerTask {
		public void run() {

			((Activity) mContext).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					timer.cancel(); // Terminate the timer thread
					initView(2);
				}
			});

		}
	}
}
