package com.example.testhttpurlconnection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    TextView tv;

    Handler handler = new Handler();
    String loginUrl = "http://10.0.2.2:8080/MyServer/logincheck.jsp";

    public String login(String username, String psd) {
        String msg = ""; //服务器返回结果
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.connect();
//post请求传递参数
            String data = "username=" + username+"&psd=" +psd; //参数之间用&连接
//向服务器发送数据(输出流)
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
            writer.write(data);
            writer.close();

            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
//接收服务器返回的信息（输入流）
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    msg+=line+"\n";
                }
//关闭缓冲区和连接
                bufferedReader.close();
                httpURLConnection.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    } //end login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText psd=(EditText)findViewById(R.id.psd);
        Button btn1=(Button)findViewById(R.id.btn_login);
          btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username1=username.getText().toString();
                        String psd1=psd.getText().toString();
                        final String r = login(username1,psd1);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("结果："+r);
                            }
                        });
                    }
                }).start();
            }
        });

        tv=(TextView)findViewById(R.id.result);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }



  /*  public String crawler(String httpUrl) {
        String msg = ""; //存放服务器返回信息
        try {

//创建URL对象
            URL url = new URL(httpUrl);
//创建HttpURLConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
//设置连接相关属性
            httpURLConnection.setConnectTimeout(5000); //设置连接超时为5秒
            httpURLConnection.setRequestMethod("GET"); //设置请求方式(默认为get)
            httpURLConnection.setRequestProperty("Charset", "UTF-8"); //设置uft-8字符集
//建立到连接(可省略)
            httpURLConnection.connect();
//获得服务器反馈状态信息
//200：表示成功完成(HTTP_OK)， 404：请求资源不存在(HTTP_NOT_FOUND)
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
//接收服务器返回的信息（输入流）
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    msg+=line+"\n";
                }
//关闭缓冲区和连接
                bufferedReader.close();
                httpURLConnection.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    } //end scawler

*/
}
