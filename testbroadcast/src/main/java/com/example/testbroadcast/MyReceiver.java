package com.example.testbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            String phoneNum = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Toast.makeText(context, "监听到拨打电话:" + phoneNum, Toast.LENGTH_LONG).show();
        }
        Intent intent1 = new Intent(context, PhoneService.class);
        if (intent.getAction().equals("BC_")) {
            Toast.makeText(context, "结束录音", Toast.LENGTH_LONG).show();
            context.stopService(intent1);
        } else {
            Toast.makeText(context, "开始录音", Toast.LENGTH_LONG).show();
            context.startService(intent1);
        }
    }
}
