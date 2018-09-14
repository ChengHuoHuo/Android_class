package com.example.testbroadcast;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneService extends Service {

    //声明录音机
    private MediaRecorder mRecorder;

    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void createRecorderFile() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath=absolutePath+"/recorder";
        File file=new File(filePath);
        if (!file.exists()){
            file.mkdir();
        }
    }
    private String getCurrentTime(){//获取当前时间，以其为名来保存录音
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        String str=format.format(date);
        return str;
    }
    //服务器开启
    @Override
    public void onCreate() {
        super.onCreate();
        if(mRecorder==null) {//获得电话管理器
            mRecorder = new MediaRecorder();//初始化录音对象
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置录音的输入源(麦克)
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置音频格式(3gp)
            createRecorderFile();//创建保存录音的文件夹
            mRecorder.setOutputFile("sdcard/recorder" + "/" + getCurrentTime() + ".3gp"); //设置录音保存的文件
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频编码
            try {
                mRecorder.prepare();//准备录音
                mRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
