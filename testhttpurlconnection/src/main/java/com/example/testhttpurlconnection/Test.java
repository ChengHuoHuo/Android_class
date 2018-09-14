package com.example.testhttpurlconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Test extends Activity {

    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);
        getNews();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    adapter = new NewsAdapter(Test.this,newsList);
                    lv.setAdapter((ListAdapter) adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(Test.this,NewsDisplayActivity.class);
                            intent.putExtra("news_url",news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

    }



    private void getNews(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    Log.d("msg","kkklll");
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    for(int i = 1;i<=20;i++) {

                        Document doc = Jsoup.connect("http://voice.hupu.com/nba/" + Integer.toString(i)).get();
//                        Log.d("msg","kkklll0000");
                        Elements titleLinks = doc.select("div.list-hd");    //解析来获取每条新闻的标题与链接地址
//                        Log.d("msg","kkklll22222");
                       // Elements descLinks = doc.select("div.list-content");//解析来获取每条新闻的简介
                        Elements timeLinks = doc.select("div.otherInfo");   //解析来获取每条新闻的时间与来源
//                        Log.d("msg","kkklll33333");
                        for(int j = 0;j < titleLinks.size();j++){
                            String title = titleLinks.get(j).select("a").text();
                            Log.d("msg",""+title);
                            String uri = titleLinks.get(j).select("a").attr("href");
                          //  String desc = descLinks.get(j).select("span").text();
                            String time = timeLinks.get(j).select("span.other-left").select("a").text();
                            Log.d("msg",""+time);
                            News news = new News(title,uri,null,time);
                            newsList.add(news);
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
