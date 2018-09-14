package com.example.testsrivice_1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import java.io.IOException;

public class MyService extends Service {
    MediaPlayer mPlayer; //定义音乐播放器变量

    @Override
    public void onCreate() {
        super.onCreate();
        if(mPlayer==null)
        {
            mPlayer=MediaPlayer.create(getApplicationContext(),R.raw.huluwa);//创建音乐播放器
            mPlayer.setLooping(true);//重复播放
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mPlayer.start();
        if(intent!=null){
            Bundle bundle=intent.getExtras();
            int op=bundle.getInt("op");
            switch (op){
                case 1:
                    if(!mPlayer.isPlaying()){
                        mPlayer.start();
                    }break;
                case 2:if(mPlayer.isPlaying()){
                    mPlayer.pause();
                }break;
                case 3:
                    if(mPlayer!=null){
                        mPlayer.stop();
                        try {
                            mPlayer.prepare();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                        mPlayer.seekTo(0);
                        mPlayer.start();
                    }
                    break;
                case 0:
                    stopSelf();
                    break;

            }
        }
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer!=null)
        {
            mPlayer.stop();//停止
            mPlayer.release();//回收
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
