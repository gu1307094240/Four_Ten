package com.example.gu.four_ten;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by 31452 on 2016/6/27.
 */

/**
 * 通过volly框架实现用户注册控制模块
 * 先判空，将用户名，密码上传到用户注册服务器，判断是否注册成功，成功返回“ok”，不成功返回“null”
 * 当接受到“ok”信息时，跳转到登陆界面，并提示注册成功，否则，提示用户“注册失败 ！ ”
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText registerUsername,registerPassword1,registerPassword2;
    private Button registerConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        /**
         * 初始化控件
         */
        registerUsername = (EditText)this.findViewById(R.id.registerUsername);
        registerPassword1 = (EditText)this.findViewById(R.id.registerPassword1);
        registerPassword2 = (EditText)this.findViewById(R.id.registerPassword2);
        registerConfirm = (Button)this.findViewById(R.id.confirmRegister);
        /**
         * 按钮设置监听事件
         */
        registerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (registerPassword1.getText().toString().equals("")||registerUsername.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"账户名密码不能为空 ！", Toast.LENGTH_SHORT).show();
                }else {
                    if(registerPassword1.getText().toString().equals(registerPassword2.getText().toString())){
                        //new 一个请求
                        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonURL.register_URL, new Response.Listener<String>() {
                            //这里是返回正确反馈的接口（只要请求成功反馈的数据都这这里）
                            //数据处理反馈（可以这这里处理服务器返回的数据）
                            @Override
                            public void onResponse(String s) {
                                if(s.equals("true")){
                                    Toast.makeText(RegisterActivity.this, "注册成功 ！", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"注册失败 ！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            //volley 有专门处理error的库
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e("TAG", volleyError.getMessage(), volleyError);
                            }
                        }){
                            //向服务器发送数据
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("username",registerUsername.getText().toString().trim());
                                map.put("password",registerPassword1.getText().toString().trim());
                                return map;
                            }

                        };
                        //把请求添加到请求队列里面
                        mQueue.add(stringRequest);

                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//跳转到的activity若已在栈中存在，则将其上的activity都销掉。
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegisterActivity.this,"两次输入的密码不一致 ！",Toast.LENGTH_SHORT).show();
                    }

                }



            }
        });

    }
}
