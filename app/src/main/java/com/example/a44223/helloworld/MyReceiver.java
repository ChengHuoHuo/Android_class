package com.example.a44223.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("wust.csq.mybroadcast")) {
            Bundle bundle = intent.getExtras();
            String aa = bundle.getString("a");
            String bb = bundle.getString("b");
            String cc= bundle.getString("c");
            double a = Double.parseDouble(aa);
            double b = Double.parseDouble(bb);
            double result = 0;
            if (cc.equals("+")) {
                result = a + b;
            }
            if (cc.equals("-")) {
                result = a - b;
            }
            if (cc.equals("*")) {
                result = a * b;
            }
            if (cc.equals("/")) {
                result = a / b;
            }
            Toast.makeText(context, aa + cc+ bb + "=" + result, Toast.LENGTH_SHORT).show();
        }
    }
}
