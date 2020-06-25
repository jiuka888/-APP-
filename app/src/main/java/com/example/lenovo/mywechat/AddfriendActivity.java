package com.example.lenovo.mywechat;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*内容提取器，用于提取另外一个APP的信息*/
public class AddfriendActivity extends AppCompatActivity {
    private String newId;//定义新的id，存储新信息
    TextView textView;//声明TextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        Button addData = (Button) findViewById(R.id.add_data1);
        textView= (TextView) findViewById(R.id.text1);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://com.example.lenovo.mywechat/Student/15");
                ContentValues values = new ContentValues();
                values.put("stuName", "尹坚蓉");
                values.put("stuNumber", "36");
                values.put("stuAge", 22);
                values.put("stuAddress", "广东");
                values.put("stuColleage", "广东理工学院");
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
                values.clear();
                values.put("stuName", "尹坚蓉");
                values.put("stuNumber", "36");
                values.put("stuAge", 22);
                values.put("stuAddress", "广东");
                values.put("stuColleage", "广东理工学院");
                Uri newUri1 = getContentResolver().insert(uri, values);
                newId = newUri1.getPathSegments().get(1);
            }
        });
        Button queryData = (Button) findViewById(R.id.query_data1);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://com.example.lenovo.mywechat/Student");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                StringBuilder content=new StringBuilder();//转义字符
                content.append("id"+"\t\t\t"+"stuName"+"\t\t\t"+"stuNumber"+"\t\t\t"+"stuAge"+"\t\t\t"+"stuAddress"+"\t\t\t"+"stuColleage"+"\n");
                if (cursor.moveToFirst()) {
                    do{
                        int id=cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("stuName"));
                        String number = cursor.getString(cursor.getColumnIndex("stuNumber"));
                        int age = cursor.getInt(cursor.getColumnIndex("stuAge"));
                        String address = cursor.getString(cursor.getColumnIndex("stuAddress"));
                        String colleage = cursor.getString(cursor.getColumnIndex("stuColleage"));
                        content.append(id+"\t\t\t"+name+"\t\t"+number+"\t\t"+age+"\t\t"+address+"\t\t"+colleage+"\n");
                    }while (cursor.moveToNext());
                    cursor.close();
                }
                textView.setText(content.toString());
            }
        });
        Button updateData = (Button) findViewById(R.id.update_data1);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据
                Uri uri = Uri.parse("content://com.example.lenovo.mywechat/Student");
                ContentValues values = new ContentValues();
                values.put("stuColleage", "广东理工学院信息技术学院");
                getContentResolver().update(uri, values, "stuColleage=?", new String[]{"广东理工学院"});
            }
        });
        Button deleteData = (Button) findViewById(R.id.delete_data1);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
                Uri uri = Uri.parse("content://com.example.lenovo.mywechat/Student");
                getContentResolver().delete(uri,"stuColleage=?", new String[]{"广东理工学院信息技术学院"});
            }
        });
    }
}
