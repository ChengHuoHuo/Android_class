package com.example.testbroadcast;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private String[] permissions = new String[]{
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    private List<String> mPermissionList = new ArrayList<>();



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "权限已申请", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "权限已拒绝", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == 123){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        Toast.makeText(MainActivity.this, "权限未申请", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    String recorder_path="";
    MediaPlayer mp=new MediaPlayer();
    ArrayList<String> recorderList =new ArrayList<String>();
    private  void getFileFromRecord(){
        recorderList =new ArrayList<String>();
        File sdpath= Environment.getExternalStorageDirectory(); //获得手机SD卡路径
        File path=new File(sdpath+"//recorder//"); //获得SD卡的recorder文件夹，返回以.3gp结尾的文件 (自定义文件过滤)
        File[ ] songFiles = path.listFiles( new MyFilter(".3gp") );
        for (File file :songFiles){
            recorderList.add(file.getAbsolutePath());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_single_choice,
                recorderList );
        ListView li=(ListView)findViewById(R.id.lv);
        li.setAdapter(adapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recorder_path=((TextView)view).getText().toString();
                try{
                    mp.reset(); //播放器重置
                    mp.setDataSource(recorder_path);
                    mp.prepare(); //准备播放
                    mp.start();
                }catch (Exception e){
                    e.printStackTrace();
                }

            } //end onItemClick
        }); //end setOnItemClick

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissionList.clear();
        for (int i = 0; i < permissions.length;i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            Toast.makeText(MainActivity.this, "已经授权", Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions,123);
        }

        getFileFromRecord();
        Button bt=(Button)findViewById(R.id.startService);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromRecord();
            }
        });

        final Intent intent=new Intent(MainActivity.this,PhoneService.class);
        Button btn=(Button)findViewById(R.id.pause);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });


        if (ActivityCompat.checkSelfPermission( MainActivity.this,
                Manifest.permission.PROCESS_OUTGOING_CALLS)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( MainActivity.this,
                    new String[] { Manifest.permission.PROCESS_OUTGOING_CALLS }, 123);
            return;
        }

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp!=null ){
            mp.stop();
            mp.release();
        }
    }



}
