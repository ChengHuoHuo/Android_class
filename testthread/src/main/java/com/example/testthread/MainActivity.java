package com.example.testthread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private int x=0;
    TextView tv;//在外面定义，在oncrate里面初始化，作为全局
    ImageSwitcher imageSwitcher;
    private int index=0; //图片序号
    private int[ ] images;//存放图片id

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            imageSwitcher.setImageResource(images[index]);
            index++;
            if (index >= images.length) {
                index = 0;
            }
            try {
                Thread.sleep(2000); //暂停2秒继续，try…catch省略
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
            imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            handler.postDelayed(this,2000);
        }
    };

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 123) {
//                imageSwitcher.setImageResource(images[msg.arg1]);
//                imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
//                imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
//        }
//        }
//    };
//    Timer timer = new Timer();
//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            while (index <= images.length) {
//                    Message msg = new Message();
//                    msg.what = 123;
//                    msg.arg1 = index;
//                    handler.sendMessage(msg);
//                    index++;
//                    if (index >= images.length) {
//                        index = 0;
//                    }
//                    try {
//                        Thread.sleep(2000); //暂停2秒继续，try…catch省略
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//    };

    //    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 123) {
//                imageSwitcher.setImageResource(images[msg.arg1]);
//                imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
//                imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
//            }
//        }
//    };
//
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  Button btn1=(Button)findViewById(R.id.button1);
   //     Button btn2=(Button)findViewById(R.id.button2);
    //    tv=(TextView)findViewById(R.id.textview);

        imageSwitcher=(ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() { //设置工厂
            @Override
            public View makeView() { //重写makeView
                ImageView imageView=new ImageView(MainActivity.this);
                imageView.setBackgroundColor(0xFFFFFFFF); //白色背景
                imageView.setScaleType(ImageView.ScaleType.CENTER); //居中显示
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) ); //定义布局
                return imageView;
            }
        });

        images = new int[]{
                R.drawable.cc,
                R.drawable.dd,
                R.drawable.ee,
        };

        imageSwitcher.setImageResource(images[index]); //必须放在工厂后面，否则空指针错
//设置动画：如淡入和淡出
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (index <= images.length) {
//                    Message msg = new Message();
//                    msg.what = 123;
//                    msg.arg1 = index;
//                    handler.sendMessage(msg);
//                    index++;
//                    if (index >= images.length) {
//                        index = 0;
//                    }
//                    try {
//                        Thread.sleep(2000); //暂停2秒继续，try…catch省略
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//
    /*    btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            timer.schedule(timerTask,1000,1000);
                handler.postDelayed(r,2000);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                timerTask.cancel();
//                timer.cancel();
                handler.removeCallbacks(r);
            }
        });*/
    }

}