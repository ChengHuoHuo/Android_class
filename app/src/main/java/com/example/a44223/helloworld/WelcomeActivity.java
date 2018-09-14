package com.example.a44223.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String u=bundle.getString("username");
        String sex=bundle.getString("sex");
        String photo_uri=bundle.getString("photo_uri");
        EditText tv_username= (EditText) findViewById(R.id.tv_username);
        tv_username.setText(u);
        EditText tv_sex= (EditText)findViewById(R.id.tv_sex);
        tv_sex.setText(sex);
        ImageView iv_photo= (ImageView)findViewById(R.id.iv_photo);
        iv_photo.setImageURI( Uri.parse(photo_uri) );
    }
}
