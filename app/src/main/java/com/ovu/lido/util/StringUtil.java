package com.ovu.lido.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return TextUtils.isEmpty(sb.toString()) ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static List<String> stringToList(String str, String separator) {
        return Arrays.asList(str.split(separator));
    }

    public static String getPetName(String str, String separator) {
        List<String> pets = new ArrayList<>();
        for (String pet :
                stringToList(str,separator)) {
            switch (pet) {
                case "0":
                    pets.add("大型犬");
                    break;
                case "1":
                    pets.add("小型犬");
                    break;
                case "2":
                    pets.add("猫");
                    break;
                case "3":
                    pets.add("其他");
                    break;
            }
        }
        return listToString(pets,"、");
    }
}
