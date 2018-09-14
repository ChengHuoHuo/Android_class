package com.example.a44223.helloworld;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class A_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_);

        
        RadioGroup rg=(RadioGroup)findViewById(R.id.radio_ysf) ;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                EditText et1=(EditText)findViewById(R.id.btn_a);
                EditText et2=(EditText)findViewById(R.id.btn_b);
                RadioButton btn=(RadioButton)findViewById(checkedId);
                String a=et1.getText().toString();
                String b=et2.getText().toString();
                String c=btn.getText().toString();
                //Intent intent=new Intent(A_Activity.this,B_Activity.class);
                Intent intent=new Intent("wust.csq.mybroadcast");
                Bundle bundle=new Bundle();
                bundle.putString("a", a);
                bundle.putString("b", b);
                bundle.putString("c", c);
                intent.putExtras(bundle);
                intent.setComponent( new ComponentName("com.example.a44223.helloworld","com.example.a44223.helloworld.MyReceiver"));
                sendBroadcast(intent);
               // startActivityForResult(intent, 100); //关键语句
            }
        });
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==100)
            if(resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Double s=bundle.getDouble("result");
            EditText tv=(EditText)findViewById(R.id.sum);
            String ss=s.toString();
            tv.setText(ss);
            }
        super.onActivityResult(requestCode, resultCode, data);
    } */
}
