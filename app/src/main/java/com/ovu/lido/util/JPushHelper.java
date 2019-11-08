package com.ovu.lido.util;

import android.content.Context;

import static com.ovu.lido.util.TagAliasOperatorHelper.sequence;

public class JPushHelper {
    private static boolean isDebug = false;
    public static void setAlias(Context context, String alias) {
        //设置JPush别名
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.alias = isDebug ? alias + "dev" : alias;
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,tagAliasBean);
    }

    public static void delAlias(Context context, String alias) {
        //设置JPush别名
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.alias = isDebug ? alias + "dev" : alias;
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,tagAliasBean);
    }
}
