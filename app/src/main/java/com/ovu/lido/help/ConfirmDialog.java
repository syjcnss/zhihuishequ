package com.ovu.lido.help;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ovu.lido.R;

public class ConfirmDialog extends Dialog implements View.OnClickListener {

	private TextView dialog_confirm_title;
	private TextView dialog_confirm_text;
	private OnConfirmListener mListener;

	public ConfirmDialog(Context context, OnConfirmListener listener) {
		super(context);
		mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_confirm);
		Window window = getWindow();
		window.setBackgroundDrawableResource(R.color.transparent);
		window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
		setCanceledOnTouchOutside(false);
		dialog_confirm_title = (TextView) findViewById(R.id.dialog_confirm_title);
		dialog_confirm_text = (TextView) findViewById(R.id.dialog_confirm_text);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		findViewById(R.id.btn_ok).setOnClickListener(this);
	}

	@Override
	public void setTitle(CharSequence title) {
		dialog_confirm_title.setText(title);
	}

	public void setContentText(CharSequence title) {
		dialog_confirm_text.setText(title);
	}
	
	public void setCancelVisible(int flag) {
		findViewById(R.id.btn_cancel).setVisibility(flag);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_cancel) {
			dismiss();
			if (mListener != null) {
				mListener.onCancel();
			}
		} else if (id == R.id.btn_ok) {
			if (mListener != null) {
				mListener.onConfirm();
			}
			dismiss();
		} else {
		}
	}

	public interface OnConfirmListener {
		void onConfirm();

		void onCancel();
	}

}
