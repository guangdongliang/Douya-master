/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.util;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "douya";

    private LogUtils() {}

    public static void d(String message) {
        Log.d(TAG, buildMessage(message));
    }

    public static void e(String message) {
        Log.e(TAG, buildMessage(message));
    }

    public static void i(String message) {
        Log.i(TAG, buildMessage(message));
    }

    public static void v(String message) {
        Log.v(TAG, buildMessage(message));
    }

    public static void w(String message) {
        Log.w(TAG, buildMessage(message));
    }

    public static void wtf(String message) {
        Log.wtf(TAG, buildMessage(message));
    }

    public static void println(String message) {
        Log.println(Log.INFO, TAG, message);
    }

    private static String buildMessage(String rawMessage) {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String fullClassName = caller.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        return className + "." + caller.getMethodName() + "(): " + rawMessage;
    }
}
