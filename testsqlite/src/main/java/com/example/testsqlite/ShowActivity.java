package com.example.testsqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT id as _id, name,age FROM person",null);
                String[] from = { "_id", "name", "age" };
        int[] to = { R.id.txtID, R.id.txtName, R.id.txtAge };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listview, cursor, from, to);
//cursor.close();
        ListView li=(ListView)findViewById(R.id.listView1);
        registerForContextMenu( li );
        li.setAdapter(adapter);
        TextView tv=(TextView)findViewById(R.id.textView1);
        tv.setText("查询到"+cursor.getCount()+"条记录");


        Button bt1= (Button)findViewById(R.id.button7); //注意id值
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(ShowActivity.this,InsertActivity.class);
                startActivityForResult(intent, 100);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
            if(resultCode==RESULT_OK){
                onCreate(null);
            }
        if(requestCode==200)
            if(resultCode==RESULT_OK){
                onCreate(null);
            }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        getMenuInflater().inflate(R.menu.manage, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.delete:
                delete(item);
                return true;
            case R.id.update :
                update(item);
                return true;
            default:
                return false;
        }
    }



    private void update(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Intent intent =new Intent(ShowActivity.this, UpdateActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("id", String.valueOf(info.id));
        bundle.putString("username",((TextView)info.targetView.findViewById(R.id.txtName)).getText().toString());
        bundle.putString("age",((TextView)info.targetView.findViewById(R.id.txtAge)).getText().toString());
        intent.putExtras(bundle);
        startActivityForResult(intent, 200);
    }

    private void delete(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(info.id>0){
            new AlertDialog.Builder(this).setTitle("删除" + info.id)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,1);
                            SQLiteDatabase db=helper.getWritableDatabase();
                            db.execSQL( "Delete from person where id=?", new Object[ ]{ info.id } );
                            db.close();
                            Toast.makeText(getApplicationContext(), "记录删除成功", Toast.LENGTH_SHORT).show();
                            onCreate(null); //重新加载一次Activity，刷新
                        }
                    })
                    .setNegativeButton("取消", null) .show();
        }

    }



}
