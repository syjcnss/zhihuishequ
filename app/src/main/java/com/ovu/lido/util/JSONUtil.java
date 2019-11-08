package com.ovu.lido.util;

import android.app.Instrumentation;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

    /**
     * 将字符串转换成json对象
     *
     * @param text
     * @return
     */
    public static JSONObject toJsonObject(String text) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (null == jsonObject) {
                return new JSONObject();
            }
        }
        return jsonObject;
    }

    /**
     * 模拟遥控器按键
     *
     * 调用的时候直接: VirturlKeyPadCtr.simulateKeystroke(realCode);
     * @param KeyCode 传人的参数为你的遥控器的KEY值, 比如返回键就是KeyEvent.KEYCODE_BACK
     *
     */
    public static void simulateKeystroke(int KeyCode) {
        Instrumentation mInstrumentation = null;
        if (mInstrumentation == null) {
            mInstrumentation = new Instrumentation();
        }
        mInstrumentation.sendKeyDownUpSync(KeyCode);
    }

}
