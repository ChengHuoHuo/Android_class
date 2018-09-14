package com.example.testcontentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

   MediaPlayer myPlayer=new MediaPlayer();

    public static List<Mp3Info> getMp3Infos(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.TITLE);
        List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();




        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Mp3Info mp3Info = new Mp3Info();
            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));//音乐id
            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));//文件路径

                mp3Info.setId(id);
                mp3Info.setTitle(title);
                mp3Info.setArtist(artist);
                mp3Info.setDuration(duration);
                mp3Info.setUrl(url);
                mp3Infos.add(mp3Info);
            }

        return mp3Infos;
    }

    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }

        String[] from = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA };
        int[] to = { R.id.music_id,
                R.id.music_title,
                R.id.music_artist,
                R.id.music_duration,
                R.id.music_url };

        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.TITLE); //按标题排序

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.list_row, cursor, from, to);



        ListView li=(ListView)findViewById(R.id.listview1);
        li.setAdapter(adapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView music_url= (TextView)view.findViewById(R.id.music_url);
                try {
                    MediaMetadataRetriever mmr=new MediaMetadataRetriever();
                    mmr.setDataSource(music_url.getText().toString());
                    String duration=mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    int during_time=Integer.parseInt(duration);
                    TextView tv=(TextView)findViewById(R.id.music_duration);
                    tv.setText(formatTime(during_time));

                    myPlayer.reset();
                    myPlayer.setDataSource(music_url.getText().toString());
                    myPlayer.prepare();
                    myPlayer.start();
                } catch (Exception e) {
                }
            }

        });


    }

   @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myPlayer!=null ){
            myPlayer.stop();
            myPlayer.release();
        }
    }

}
