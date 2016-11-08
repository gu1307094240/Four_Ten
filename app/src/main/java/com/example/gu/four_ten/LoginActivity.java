package com.example.gu.four_ten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gu.four_ten.common.CommonURL;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 31452 on 2016/6/26.
 */

/**
 * 通过volly框架实现用户登陆控制模块
 * 先判空，将用户名，密码上传到用户登录服务器，判断是否存在该用户，存在返回“ok”，不存在返回“null”
 * 当接受到“true”信息时，登陆到主界面，并提示登陆成功，否则，提示用户“账号或密码不正确 ”
 */
public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button register;
    private EditText username,password;
    private CheckBox remember;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        preferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        editor = preferences.edit();
        /**
         * 初始化控件
         */
        username = (EditText)this.findViewById(R.id.username);
        password = (EditText)this.findViewById(R.id.password);
        login = (Button)this.findViewById(R.id.login);
        register = (Button)this.findViewById(R.id.register);
        remember = (CheckBox)this.findViewById(R.id.cbRemember);

        String rememberName = preferences.getString("username","");
        if (rememberName==null||rememberName.equals("")){
            remember.setChecked(false);
        }else{
            remember.setChecked(true);
            username.setText(rememberName);
        }
        /**
         * 按钮设置监听事件
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().equals("")||username.getText().toString().equals("")) {

                    Toast.makeText(LoginActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    //new 一个请求
                    RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonURL.login_URL , new Response.Listener<String>() {
                        //这里是返回正确反馈的接口（只要请求成功反馈的数据都这这里）
                        //数据处理反馈（可以这这里处理服务器返回的数据）
                        @Override
                        public void onResponse(String s) {
                            if (s.equals("true")) {
                                if (remember.isChecked()){
                                    editor.putString("username",username.getText().toString());
                                    editor.commit();
                                }else{
                                    editor.remove("username");
                                    editor.commit();
                                }
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, Main.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "账号或密码不正确 ！", Toast.LENGTH_LONG).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                        //volley 有专门处理error的库
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("TAG", volleyError.getMessage(), volleyError);
                        }
                    })
                    {
                        //向服务器发送数据
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("username", username.getText().toString().trim());
                            map.put("password", password.getText().toString().trim());
                            return map;
                        }

                    };
                    //把请求添加到请求队列里面
                    mQueue.add(stringRequest);

                }
            }


        });
        //register按钮设置监听，点击触发跳转到RegisterActivity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
