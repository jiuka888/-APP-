package com.example.lenovo.mywechat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/*用于跳转到各个活动*/
public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    public void Button1() {
        Button Group = findViewById(R.id.a1);//找到id为a1的按钮控件
        Button AddFriend = findViewById(R.id.q1);
        Button Scan = findViewById(R.id.u1);
        Button Money = findViewById(R.id.d1);
        Button Help = findViewById(R.id.h1);
        Group.setOnClickListener(this);//为a1按钮设置点击事件监听器
        AddFriend.setOnClickListener(this);//为q1按钮设置点击事件监听器
        Scan.setOnClickListener(this);//为u1按钮设置点击事件监听器
        Money.setOnClickListener(this);//为d1按钮设置点击事件监听器
        Help.setOnClickListener(this);//为h1按钮设置点击事件监听器
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Button1();
    }
   public void onClick(View v) {//事件触发后执行这个方法
        switch (v.getId()) {//根据控件ID判断点击的是哪个按钮
            case R.id.a1:
                 /*在NotificationActivity活动上跳转到Main2Activity这个活动*/
                Intent intent = new Intent(NotificationActivity.this,
                        Main2Activity.class);//构建Intent
                startActivity(intent);//启动目标活动
                Toast.makeText(NotificationActivity.this,
                        "你点击了朋友圈", Toast.LENGTH_LONG).show();
                break;
                case R.id.q1:
                    Intent intent0 = new Intent(NotificationActivity.this, Main2Activity.class);
                    startActivity(intent0);
                    Toast.makeText(NotificationActivity.this, "你点击了添加朋友", Toast.LENGTH_LONG).show();
                    break;
                case R.id.u1:
                    Intent intent1 = new Intent(NotificationActivity.this, Main2Activity.class);
                    startActivity(intent1);
                    Toast.makeText(NotificationActivity.this, "你点击了扫一扫", Toast.LENGTH_LONG).show();
                    break;
                case R.id.d1:
                    Intent intent2 = new Intent(NotificationActivity.this, Main2Activity.class);
                    startActivity(intent2);
                    Toast.makeText(NotificationActivity.this, "你点击了收付款", Toast.LENGTH_LONG).show();
                    break;
                case R.id.h1:
                    Intent intent3 = new Intent(NotificationActivity.this, Main2Activity.class);
                    startActivity(intent3);
                    Toast.makeText(NotificationActivity.this, "你点击了帮助与反馈", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.itemGroup:
                Intent intent0 = new Intent(NotificationActivity.this, GroupActivity.class);
                startActivity(intent0);
                Toast.makeText(NotificationActivity.this, "你点击了发起群聊", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemAddFriend:
                Intent intent1 = new Intent(NotificationActivity.this, AddfriendActivity.class);
                startActivity(intent1);
                Toast.makeText(NotificationActivity.this, "你点击了添加朋友", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemScan:
                Intent intent2 = new Intent(NotificationActivity.this, ScanActivity.class);
                startActivity(intent2);
                Toast.makeText(NotificationActivity.this, "你点击了扫一扫", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemMoney:
                Intent intent3 = new Intent(NotificationActivity.this, MoneyActivity.class);
                startActivity(intent3);
                Toast.makeText(NotificationActivity.this, "你点击了收付款", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemHelp:
                 /*在NotificationActivity活动上跳转到HelpActivity这个活动*/
                Intent intent4 = new Intent(NotificationActivity.this,
                        HelpActivity.class);//构建Intent4
                startActivity(intent4);//启动目标活动
                Toast.makeText(NotificationActivity.this,
                        "你点击了帮助与反馈", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }

}
