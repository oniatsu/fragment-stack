package io.github.oniatsu.fragmentstack.sample.utils;

import android.content.Context;
import android.widget.Toast;

public final class Logger {

    private Logger() {}

    public static void i(String message) {
        android.util.Log.i(">>>>>>>>>>>>>>>>>>>>>", message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
