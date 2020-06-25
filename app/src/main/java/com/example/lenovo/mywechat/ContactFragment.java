package com.example.lenovo.mywechat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;


public class ContactFragment extends Fragment implements View.OnClickListener {
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button=(Button)getActivity().findViewById(R.id.button_contact);
        button.setOnClickListener(this);//为按钮设置点击事件监听器
    }

    @Override
    public void onClick(View view) {
        /*在MainActivity活动的ContactFragment上跳转到ContactActivity这个活动*/
        Toast.makeText(getActivity(), "你点击了通讯录内容提取器", Toast.LENGTH_LONG).show();
        Intent intent01 = new Intent(getActivity(), ContactActivity.class);//构建Intent
        startActivity(intent01);//启动目标活动
    }
}
