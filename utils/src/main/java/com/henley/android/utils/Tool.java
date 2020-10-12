package com.henley.android.utils;

import android.app.Application;
import android.content.Context;

/**
 * Tool
 *
 * @author Henley
 * @since 2020/5/26 16:38
 */
public final class Tool {

    private static Context context;

    public static void init(Application application) {
        Tool.context = application;
    }

    public static Context getContext() {
        if (Tool.context == null) {
            throw new IllegalArgumentException("Please invoke Tool.init(context) first!");
        }
        return Tool.context;
    }

}