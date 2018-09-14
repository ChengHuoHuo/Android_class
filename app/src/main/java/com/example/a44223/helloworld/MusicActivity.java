package com.example.a44223.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends Activity {
    MediaPlayer mp=new MediaPlayer();
    String song_path="";
    private int currentPosition;
    ArrayList<String> list = new ArrayList<String>(); //音乐列表
    ListView li;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        if (ActivityCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MusicActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }




        File sdpath = Environment.getExternalStorageDirectory(); //获得手机SD卡路径
        File path = new File(sdpath + "//mp3//"); //获得SD卡的mp3文件夹
//返回以.mp3结尾的文件 (自定义文件过滤)
        File[] songFiles = path.listFiles(new MyFilter(".mp3"));
        for (File file : songFiles) {

            list.add(file.getAbsolutePath()); //获取文件的绝对路径
        }


        ArrayAdapter<String> adapter= new ArrayAdapter<String>(
                MusicActivity.this,
                android.R.layout.simple_list_item_single_choice,
                list );

        li = (ListView) findViewById(R.id.musicListView);
        li.setAdapter(adapter);
        li.setChoiceMode(ListView.CHOICE_MODE_SINGLE); //单选
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                song_path =((TextView)view).getText().toString();
                ImageView play=(ImageView)findViewById(R.id.play_pause);
                play.setImageResource(R.drawable.pause);
                try{
                    mp.reset(); //重置
                    mp.setDataSource(song_path);
                    mp.prepare(); //准备
                    mp.start(); //播放


                }catch (Exception e){ }
            }
        });

        ImageView play=(ImageView)findViewById(R.id.play_pause);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( song_path.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "先选收歌曲先听听",
                            Toast.LENGTH_SHORT).show();

                }
                if( mp.isPlaying() ){
                    mp.pause(); //暂停
                    ImageView stop=findViewById(R.id.play_pause);
                    stop.setImageResource(R.drawable.play);
                }else if( !song_path.isEmpty() ) {
                    mp.start(); //继续播放
                    ImageView stop=findViewById(R.id.play_pause);
                    stop.setImageResource(R.drawable.pause);
                }

            }
        });

        ImageView perious=(ImageView)findViewById(R.id.previous);
        perious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMusicposition(--currentPosition);

            }
        });

        ImageView next=(ImageView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMusicposition(++currentPosition);


            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                changeMusicposition(++currentPosition);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp!=null ){
            mp.stop();
            mp.release();
        }
        Toast.makeText(getApplicationContext(), "退出啦", Toast.LENGTH_SHORT).show();
    }

    public void changeMusicposition(int position){
        if(position<0){
            currentPosition=position=list.size()-1;
        }else if(position>list.size()-1){
            currentPosition=position=0;
        }

        li.setItemChecked(currentPosition,true);
        song_path=list.get(position);
        try{
            mp.reset(); //重置
            mp.setDataSource(song_path);
            mp.prepare(); //准备
            mp.start(); //播放


        }catch (Exception e){ }


    }


}
