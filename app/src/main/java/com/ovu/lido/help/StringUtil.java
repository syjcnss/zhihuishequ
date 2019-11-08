package com.ovu.lido.help;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.ovu.lido.R;

import java.util.Locale;

public class StringUtil {

	public static final String EMPTY_STRING = "";
	public static final String UTF_8 = "UTF-8";
	public static final String DIVIDER_COMMA = ",";
	public static final String DIVIDER_AND = "&";
	public static final String DIVIDER_RMB = "¥";

	private static String getStatus(String str) {
		if (str.equals("0")) {
			return "审核中";
		} else if (str.equals("1")) {
			return "已通过";
		} else {
			return "未通过";
		}
	}

	public static SpannableStringBuilder getSpan(Context ctx, String str) {
		String s = getStatus(str);
		int color = 0;
		if (str.equals("2")) {// 未审核
			color = ctx.getResources().getColor(R.color.bg_red);
		} else if (str.equals("0")) {// 审核中
			color = ctx.getResources().getColor(R.color.text_green);
		} else if (str.equals("1")) {// 审核通过
			color = ctx.getResources().getColor(R.color.bg_blue);

		}
		SpannableStringBuilder style = new SpannableStringBuilder(s);
		style.setSpan(new ForegroundColorSpan(color), 0, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;
	}

	public static SpannableStringBuilder setSpan(Context ctx, String str, int start, int end, int color) {
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		style.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(color)), start, end,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置textview的背景颜色
		return style;
	}

	public static boolean isEmpty(CharSequence str) {
		if (str == null) {
			return true;
		}
		if (str.length() == 0) {
			return true;
		}
		if (str.toString().toUpperCase(Locale.getDefault()).equals("NULL")) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(CharSequence str) {
		return !isEmpty(str);
	}
}
