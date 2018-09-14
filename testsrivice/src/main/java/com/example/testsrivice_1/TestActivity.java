package com.example.testsrivice_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends Activity {
    int op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button bt1= (Button)findViewById(R.id.play);
        Button bt2= (Button)findViewById(R.id.pause);
        Button bt3= (Button)findViewById(R.id.replay);
        Button bt4= (Button)findViewById(R.id.stopservice);
        Button bt5= (Button)findViewById(R.id.exit);
        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Intent intent = new Intent( TestActivity.this, MyService.class );
                switch(v.getId()){
                    case R.id.play: op=1; break;
                    case R.id.pause: op=2; break;
                    case R.id.replay: op=3; break;
                    case R.id.stopservice: op=0; break;
                    case R.id.exit: op=-1; break;
                }
                if(op!=-1){
                    Bundle bundle = new Bundle();
                    bundle.putInt("op", op);
                    intent.putExtras(bundle);
                    startService(intent);
                }
                else{
                    new AlertDialog.Builder(TestActivity.this).setTitle("提示")
                            .setMessage("确认要退出吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface arg0,int arg1) {
                                    stopService(intent);
                                    finish();
                                }})
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                                }
                            }).show();
                }
            }
        };
        bt1.setOnClickListener(listener);
        bt2.setOnClickListener(listener);
        bt3.setOnClickListener(listener);
        bt4.setOnClickListener(listener);
        bt5.setOnClickListener(listener);
    }

}
