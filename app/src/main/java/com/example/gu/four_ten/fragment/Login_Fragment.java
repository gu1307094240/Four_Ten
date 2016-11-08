package com.example.gu.four_ten.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gu.four_ten.Collect_Activity;
import com.example.gu.four_ten.LoginActivity;
import com.example.gu.four_ten.Main;
import com.example.gu.four_ten.R;

import java.util.zip.Inflater;

/**
 * Created by 31452 on 2016/6/28.
 */
/**
 * 使用Fragment嵌套Fragment的模式实现界面滑动
 * 主Activity下有五个一级Fragment，这是其中之一，Login_Fragment
 */
public class Login_Fragment extends Fragment {

    private Button checkLogin,btnCollect;
    private View view;
//    SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.register, (ViewGroup)getActivity().findViewById(R.id.viewPager_text), false);
        //初始化布局控件
        checkLogin = (Button)view.findViewById(R.id.checkLogin);
        btnCollect = (Button)view.findViewById(R.id.btn_myCollection);

//        preferences = getActivity().getSharedPreferences("keep", Context.MODE_PRIVATE);
//        System.out.println(preferences.getString("text","")+"-----------------------------");
//        test.setText(preferences.getString("text",""));
        //设置按钮监听事件
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collect_Activity.class);
                startActivity(intent);
            }
        });
        checkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }
}
