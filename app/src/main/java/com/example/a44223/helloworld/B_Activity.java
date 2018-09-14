package com.example.a44223.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class B_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String aa=bundle.getString("a");
        String bb=bundle.getString("b");
        String cc=bundle.getString("c");
        double a=Double.parseDouble(aa);
        double b=Double.parseDouble(bb);
        double result=0;
        if(cc.equals("+"))
        {
            result=a+b;
        }
        if(cc.equals("-"))
        {
            result=a-b;
        }
        if(cc.equals("*"))
        {
            result=a*b;
        }
        if(cc.equals("/"))
        {
            result=a/b;
        }
        bundle.putDouble("result", result);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}
