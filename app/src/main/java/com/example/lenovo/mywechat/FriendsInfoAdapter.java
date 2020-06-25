package com.example.lenovo.mywechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2020-04-20.
 */

public class FriendsInfoAdapter extends ArrayAdapter<FriendsInfo> {
    int resourceId;

    public FriendsInfoAdapter(Context context, int resource, List<FriendsInfo> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {//渲染Item方法
        FriendsInfo friendsInfo=getItem(position);
        View view;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view=convertView;
        }
        ImageView image=view.findViewById(R.id.image);
        TextView name=view.findViewById(R.id.name);
        TextView info=view.findViewById(R.id.info);
        TextView time=view.findViewById(R.id.time);
        image.setImageResource(friendsInfo.getImageId());
        name.setText(friendsInfo.getName());
        info.setText(friendsInfo.getInfo());
        time.setText(friendsInfo.getTime());
        return view;
    }

}
