package com.zqx.mytouchdragdemo;

import android.widget.Toast;

import static com.zqx.mytouchdragdemo.MyApp.context;


public class ToastUtil {
    private static Toast toast;

    public static void show(String text) {

        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();

    }

}
