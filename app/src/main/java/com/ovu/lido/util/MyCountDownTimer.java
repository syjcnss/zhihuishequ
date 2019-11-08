package com.ovu.lido.util;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCountDownTimer extends CountDownTimer {
    private TextView tv;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.tv = view;
    }

    /**
     * 计时过程
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        tv.setEnabled(false);
        String textStr = "重新获取" + millisUntilFinished / 1000 + "s";
        tv.setText(textStr);
    }

    /**
     * 计时完毕
     */
    @Override
    public void onFinish() {
        tv.setText("重新获取");
        tv.setEnabled(true);
    }
}
