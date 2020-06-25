package com.example.lenovo.mywechat;

import android.annotation.SuppressLint;
//import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import java.lang.reflect.Field;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.widget.Toast;


/*  主要用于BottomNavigationView + Fragment 底部导航栏的实现
    和定义广播方法、通知方法（用于在Fragment界面调用MainActivity的方法）*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WeixinFragment weixinfragment;//声明四个界面Fragment
    private ContactFragment contactfragment;//
    private FindFragment findfragment;//
    private ProfilFragment peofilfragment;//
    private Fragment[] fragments;//用于储存四个Fragment
    private int lastfragment;//用于记录上个选择的Fragment
    private IntentFilter intentFilter;//声明 IntentFilter 过滤器
    private NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;///声明内部类，用于动态广播
    NotificationManager manager;//通知 管理器用于管理通知Notification对象，可以管理多个对象


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_weixin:
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_contact_list:
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_find:
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
                case R.id.navigation_profil:
                    if (lastfragment != 3) {
                        switchFragment(lastfragment, 3);
                        lastfragment = 3;
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }

    private void initFragment() {

        weixinfragment = new WeixinFragment();
        contactfragment = new ContactFragment();
        findfragment = new FindFragment();
        peofilfragment = new ProfilFragment();
        fragments = new Fragment[]{weixinfragment, contactfragment, findfragment, peofilfragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, weixinfragment).show(weixinfragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        Receiver();

    }

    private void Receiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
        registerReceiver(networkChangeBroadcastReceiver, intentFilter);
    }

    class NetworkChangeBroadcastReceiver extends BroadcastReceiver {//内部类

        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context,"网络状态改变了",Toast.LENGTH_LONG).show();
            RelativeLayout layout_1 = (RelativeLayout) findViewById(R.id.rl_error_item);
            ConnectivityManager connectionManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//实例化
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();//调用getActiveNetworkInfo()
            if (networkInfo != null && networkInfo.isAvailable()) {//判断有无网络
                Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show();
                layout_1.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
            } else {
                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
                layout_1.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeBroadcastReceiver);
    }
    public void Notification() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//通过getSystemService获得通知管理器对象
        Button sendNotice = findViewById(R.id.button_profil);
        Button sendNotice1 = findViewById(R.id.button_profil1);
        Button sendNotice2 = findViewById(R.id.button_profil2);
        sendNotice.setOnClickListener(this);//为按钮设置点击事件监听器
        sendNotice1.setOnClickListener(this);//为按钮设置点击事件监听器
        sendNotice2.setOnClickListener(this);//为按钮设置点击事件监听器
    }

    public void onClick(View v) {//事件触发后执行这个方法
        switch (v.getId()) {//根据控件ID判断点击的是哪个按钮
            /*case R.id.send_notice://如果是点击发送通知的按钮
                Intent intent = new Intent(this, NotificationActivity.class);//构建Intent对象，用于活动跳转
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);//构建PendingIntent对象，用于点击通知后做跳转
                Notification notification = new NotificationCompat.Builder(MainActivity.this)//android7.0以下创建通知对象
                        .setContentTitle("This is content title")//通知标题
                        //通知的详细文本信息
                        .setContentText("This is content text  This is content text This is content text This is content text This is content text This is content text This is content text")
                        .setWhen(System.currentTimeMillis())//什么时候弹出通知信息
                        .setSmallIcon(R.mipmap.ic_launcher)//设置小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置大图标
                        .setContentIntent(pi)//设置跳转需要用到的PendingIntent对象
                       // .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/XXX")))//声音
                        // .setVibrate(new long[]{0, 1000, 1000, 1000,1000,1000})//震动，每隔一秒震动一次
                        // .setLights(Color.GREEN, 1000, 1000)//呼吸灯每隔一秒闪烁一次
                        // .setDefaults(NotificationCompat.DEFAULT_ALL)
                        //设置多行文本信息
                         //.setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                        //通知里面引入超大图片
                        //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.image)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)//设置优先级
                        .setAutoCancel(true)//设置点击通知后通知是否消失，true为消失
                        .build();
                manager.notify(1, notification);//通知管理器管理通知并发送通知
                 break;*/
            case R.id.button_profil://点击发送通知的按钮
                Toast.makeText(MainActivity.this, "夏洛克发来信息", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, NotificationActivity.class);//构建Intent对象，用于活动跳转
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);//构建PendingIntent对象，用于点击通知后做跳转
                Notification notification = new NotificationCompat.Builder(this, "channel")//创建通知对象，第二个参数表示通知渠道的id，用于将当前的通知和渠道进行绑定
                        .setContentTitle("福尔摩斯：")//通知标题
                        .setContentText("这是常识,我亲爱的华生!")//通知的详细文本信息
                        .setWhen(System.currentTimeMillis())//什么时候弹出通知信息
                        .setSmallIcon(R.mipmap.ic_launcher)//设置小图标
                        .setContentIntent(pi)//设置跳转需要用到的PendingIntent对象
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置大图标
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                                (BitmapFactory.decodeResource(getResources(), R.drawable.image)))//通知里面引入超大图片
                        .build();
                manager.notify(1, notification);//通知管理器管理通知并发送通知
                break;
            case R.id.button_profil1://点击发送通知的按钮
                Toast.makeText(MainActivity.this, "夏洛克发来信息", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(this, NotificationActivity.class);//构建Intent对象，用于活动跳转
                PendingIntent pi1 = PendingIntent.getActivity(this, 0, intent1, 0);//构建PendingIntent对象，用于点击通知后做跳转
                Notification notification1 = new NotificationCompat.Builder(this, "channel")//创建通知对象，第二个参数表示通知渠道的id，用于将当前的通知和渠道进行绑定
                        .setContentTitle("福尔摩斯：")//通知标题
                        .setContentText("华生,你找到了盲点!")//通知的详细文本信息
                        .setWhen(System.currentTimeMillis())//什么时候弹出通知信息
                        .setSmallIcon(R.mipmap.ic_launcher)//设置小图标
                        .setContentIntent(pi1)//设置跳转需要用到的PendingIntent对象
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置大图标
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                                (BitmapFactory.decodeResource(getResources(), R.drawable.image)))//通知里面引入超大图片
                        .build();
                manager.notify(1, notification1);//通知管理器管理通知并发送通知
                break;
            case R.id.button_profil2://点击发送通知的按钮
                Toast.makeText(MainActivity.this, "夏洛克发来信息", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, NotificationActivity.class);//构建Intent对象，用于活动跳转
                PendingIntent pi2 = PendingIntent.getActivity(this, 0, intent2, 0);//构建PendingIntent对象，用于点击通知后做跳转
                Notification notification2 = new NotificationCompat.Builder(this, "channel")//创建通知对象，第二个参数表示通知渠道的id，用于将当前的通知和渠道进行绑定
                        .setContentTitle("福尔摩斯：")//通知标题
                        .setContentText("排除所有不可能的，剩下的就是事实!")//通知的详细文本信息
                        .setWhen(System.currentTimeMillis())//什么时候弹出通知信息
                        .setSmallIcon(R.mipmap.ic_launcher)//设置小图标
                        .setContentIntent(pi2)//设置跳转需要用到的PendingIntent对象
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置大图标
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                                (BitmapFactory.decodeResource(getResources(), R.drawable.image)))//通知里面引入超大图片
                        .build();
                manager.notify(1, notification2);//通知管理器管理通知并发送通知
                break;
            default:
                break;
        }
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemGroup:
                /* 在MainActivity这个活动的基础上打开GroupActivity这个活动*/
                Intent intent0 = new Intent(MainActivity.this,
                        GroupActivity.class);//构建Intent
                startActivity(intent0);//启动目标活动GroupActivity
                Toast.makeText(MainActivity.this,
                        "你点击了发起群聊", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemAddFriend:
                Intent intent1 = new Intent(MainActivity.this, AddfriendActivity.class);
                startActivity(intent1);
                Toast.makeText(MainActivity.this, "你点击了添加朋友", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemScan:
                Intent intent2 = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent2);
                Toast.makeText(MainActivity.this, "你点击了扫一扫", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemMoney:
                Intent intent3 = new Intent(MainActivity.this, MoneyActivity.class);
                startActivity(intent3);
                Toast.makeText(MainActivity.this, "你点击了收付款", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemHelp:
                Intent intent4 = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent4);
                Toast.makeText(MainActivity.this, "你点击了帮助与反馈", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }

}