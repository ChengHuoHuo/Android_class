package com.example.testsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int DB_VERSION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1=findViewById(R.id.button);
        Button btn2=findViewById(R.id.button2);
        Button btn3=findViewById(R.id.button3);
        Button btn4=findViewById(R.id.button4);
        Button btn5=findViewById(R.id.button5);
        Button btn6=findViewById(R.id.button6);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.button:
                        createdb();
                        break;
                    case R.id.button2:
                    {
                        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,DB_VERSION);
                        SQLiteDatabase db=helper.getWritableDatabase();
                        Person person = new Person();
                        person.name = "wustzz";
                        person.age = 39;
                        db.execSQL("INSERT INTO person VALUES (NULL,?,?)",
                                new Object[ ] { person.name, person.age } );
                        db.close(); //数据操作完毕一定要记得关闭数据库连接
                        Toast.makeText(getApplicationContext(), "记录添加成功", Toast.LENGTH_SHORT).show();
                    }
                    case R.id.button3:
                    {
                        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,DB_VERSION);
                        SQLiteDatabase db=helper.getWritableDatabase();
//删除数据
                        db.execSQL( "Delete from person where age<?", new Object[ ]{ "22" } );
                        db.close(); //关闭数据库连接
                        Toast.makeText(getApplicationContext(), "记录删除成功", Toast.LENGTH_SHORT).show();
                    }
                    case R.id.button4:
                    {
                        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,DB_VERSION);
                        SQLiteDatabase db=helper.getWritableDatabase();
//更新数据
                        db.execSQL("Update person set age=? where name like ?",
                                new Object[ ]{ 20,"wust%" } );
                        db.close(); //关闭数据库连接
                        Toast.makeText(getApplicationContext(), "记录修改成功", Toast.LENGTH_SHORT).show();
                    }
                    case R.id.button5:
                    {
                        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,DB_VERSION);
                        SQLiteDatabase db=helper.getWritableDatabase();
                        Cursor cursor = db.rawQuery( "SELECT * FROM person where age>'?'", new String[]{"10"} );
                        TextView tv=(TextView)findViewById(R.id.textView1); //简单用TextView显示一下数据
                        tv.setText("查询到"+cursor.getCount()+"条记录(当前数据库版本号="+DB_VERSION+")");
                        while (cursor.moveToNext()) { //遍历结果集
                            int id = cursor.getInt(cursor.getColumnIndex("id") );
                            String name = cursor.getString(cursor.getColumnIndex("name") );
                            int age = cursor.getInt(cursor.getColumnIndex("age") );
                            tv.setText(tv.getText()+"\n"+"id="+id+",name="+name+",age="+age);
                        }
                        cursor.close(); //关闭cursor
                        db.close(); //关闭数据库连接
                    }
                    case R.id.button6:
                    {
                        DB_VERSION++; //版本号递增一下
//更新test.db数据库
                        DBHelper helper = new DBHelper(getApplicationContext(),
                                "test.db", null,DB_VERSION);
                        SQLiteDatabase db=helper.getWritableDatabase();
                        db.close();

                    }
                }
            }
        };
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
    }


    private void createdb() {
        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,DB_VERSION);
//调用getWritableDatabase()或getReadableDatabase()才会真正创建或打开
        SQLiteDatabase db=helper.getWritableDatabase();
        db.close(); //操作完成后关闭数据库连接
        Toast.makeText(getApplicationContext(), "数据库创建成功", Toast.LENGTH_SHORT).show();
    }




}
