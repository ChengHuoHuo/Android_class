package com.example.a44223.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends Activity {
   // private String msg = "";
    ImageView iv;

    Uri photo_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        /*     Button btn1 = (Button) findViewById(R.id.btn_reg1);
        btn1.setOnClickListener(view -> {
            msg = "";
            msg += "\n用户名=" + u.getText().toString();
            msg += "\n手机=" + p.getText().toString();
            msg += "\n密码=" + psd.getText().toString();
            RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
            RadioButton s = (RadioButton) findViewById(sex.getCheckedRadioButtonId());
            if (s != null) {
                msg += "\n性别=" + s.getText();
            }

            msg+="\n爱好";
            CheckBox cb1=(CheckBox)findViewById(R.id.checkBox2) ;
            if(cb1.isChecked()){
                msg+=cb1.getText().toString()+" ";
            }
            CheckBox cb2=(CheckBox)findViewById(R.id.checkBox3) ;
            if(cb2.isChecked()){
                msg+=cb2.getText().toString()+" ";
            }
            CheckBox cb3=(CheckBox)findViewById(R.id.checkBox4) ;
            if(cb3.isChecked()){
                msg+=cb3.getText().toString()+" ";
            }

            msg+="\n专业=";
            Spinner m=(Spinner)findViewById(R.id.spinner);
            msg+=m.getSelectedItem().toString();

            Log.d(msg, "获得的值：" + msg);
                });
*/

        iv=(ImageView)findViewById(R.id.photo);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
            }
        });

        Button btn1 = (Button) findViewById(R.id.btn_reg1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText u = (EditText) findViewById(R.id.username);
              //  EditText p = (EditText) findViewById(R.id.phone);
             //   EditText psd = (EditText) findViewById(R.id.psd);
                RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
                RadioButton s = (RadioButton) findViewById(sex.getCheckedRadioButtonId());


                Intent intent=new Intent(RegisterActivity.this,WelcomeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("username",u.getText().toString());
                bundle.putString("sex",s.getText().toString());
                bundle.putString("photo_uri",photo_uri.toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        photo_uri=data.getData();

        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                photo_uri=data.getData();
                iv.setImageURI(data.getData());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



}


