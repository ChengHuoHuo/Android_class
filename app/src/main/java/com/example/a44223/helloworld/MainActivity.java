package com.example.a44223.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private void init(){
        Button btn1=(Button)findViewById(R.id.btn_test);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=(TextView)findViewById(R.id.msg);
                tv.setText("w武汉科技大学");
                Toast.makeText(getApplicationContext(),"设置成功",Toast.LENGTH_SHORT).show();
            }
        });
        final Button btn2=(Button)findViewById(R.id.btn_exit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.bird)
                        .setTitle("提示：")
                        .setMessage("确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.bird)
                    .setTitle("提示：")
                    .setMessage("确定退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(MainActivity.this,"选中"+item.getTitle(),
                Toast.LENGTH_SHORT).show();
        switch(item.getItemId()){
            case R.id.main_menu1:
                setContentView(R.layout.activity);
                return true;
            case R.id.main_menu2:
                setContentView(R.layout.testactivity);
                return true;
            case R.id.main_menu3:
                setContentView((R.layout.activity_main));
                init();
                return true;
            default:
                return false;
        }
    }

}
