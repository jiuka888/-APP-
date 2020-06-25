package com.example.lenovo.mywechat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;

import android.support.v4.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class WeixinFragment extends Fragment {
    List<FriendsInfo> friendsInfolist=new ArrayList<>();//全局

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_weixin,container,false);
        return view;
    }
    public void initFriendsInfoData(){
        int[] imageId=new int[]{R.drawable.image0,R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,
                R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image9,R.drawable.image10};
        java.lang.String[] nameId=new String[]{"布丁","望","璃乃","静流","莫妮卡","纯",
                "真步","咲恋","香织","玲奈","优依"};
        for (int k=0;k<=5;k++){
            for(int i=0;i<=10;i++){
                Random random=new Random();
                long r=random.nextLong();
                Date date=new Date(r);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:ss");
                String time=simpleDateFormat.format(date);
                FriendsInfo friendsInfo=new FriendsInfo(imageId[i],nameId[i],"ReceiveInfo"+random.nextInt(10000),time);
                friendsInfolist.add(friendsInfo);
            }
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFriendsInfoData();
        FriendsInfoAdapter friendsInfoAdapter=new FriendsInfoAdapter(getActivity(),R.layout.layout_item_msg,friendsInfolist);
        ListView listView=getActivity().findViewById(R.id.listview);
        listView.setAdapter(friendsInfoAdapter);//完成适配效果，有显示Item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendsInfo friendsInfo=friendsInfolist.get(position);
                //添加Intent跳转功能，在另外一个活动当中显示当前点击的Item数据
                Toast.makeText(getActivity(),"你点击了第"+(position+1)+"个Item"+friendsInfo.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
