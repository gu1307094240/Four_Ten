package com.example.gu.four_ten;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.example.gu.four_ten.fragment.Image_Fragment;
import com.example.gu.four_ten.fragment.Login_Fragment;
import com.example.gu.four_ten.fragment.Movie_Fragment;
import com.example.gu.four_ten.fragment.Music_Fragment;
import com.example.gu.four_ten.fragment.Text_Fragment;

/**
 * Created by 31452 on 2016/6/21.
 */

/**
 * 程序主程序
 * 通过点击底部菜单栏不同按钮跳转到不同的一级Fragment界面，再通过左右滑动实现浏览不同二级Fragment页面
 * 并且在不同页面中通过点击，滑动触发不同的响应事件
 */
public class Main extends AppCompatActivity implements View.OnClickListener{

    private ImageView text_button;
    private ImageView image_button;
    private ImageView music_button;
    private ImageView movie_button;
    private ImageView login_button;
    private Text_Fragment fg1;
    private Music_Fragment fg3;
    private Movie_Fragment fg4;
    private Image_Fragment fg2;
    private Login_Fragment fg5;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SQLiteDatabase db = this.openOrCreateDatabase("collect.db", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists table_collect(id integer primary key autoincrement,tag text not null,title text not null,url text not null)");
        db.close();
        //FragmentManager能够实现管理activity中fragment. 通过调用activity的getFragmentManager()取得它的实例。
        fManager = getSupportFragmentManager();
        initView();
        //模拟一次点击，即进去后选择第一项
        text_button.performClick();

    }

    //UI组件初始化与事件绑定
    private void initView() {
        text_button = (ImageView) this.findViewById(R.id.text_button);
        image_button = (ImageView) this.findViewById(R.id.image_button);
        music_button = (ImageView) this.findViewById(R.id.music_button);
        movie_button = (ImageView) this.findViewById(R.id.movie_button);
        login_button = (ImageView) this.findViewById(R.id.avatar_bt);

        text_button.setOnClickListener(this);
        image_button.setOnClickListener(this);
        music_button.setOnClickListener(this);
        movie_button.setOnClickListener(this);
        login_button.setOnClickListener(this);

    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){

        if(fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if(fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if(fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if(fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
        if(fg5 != null) {
            fragmentTransaction.hide(fg5);
        }
    }


    //点击按钮监听事件
    @Override
    public void onClick(View v) {
//        FragmentTransaction对fragment进行添加,移除,替换,以及执行其他动作
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        hideAllFragment(fragmentTransaction);

        restartButton();
        switch (v.getId()){
            case R.id.text_button:
                //通过点击按钮改变按钮样式
                text_button.setImageResource(R.drawable.text_change);
                if(fg1 == null){
                    //如果fg1为第一次加载，new一个对象
                    fg1 = new Text_Fragment();
                    //将其中一个一级Fragment添加到FragmentTransaction中
                    fragmentTransaction.add(R.id.text_frameLayout,fg1);
                }else{
                    //如果fg1不为第一次加载，则直接从fragmentTransaction展示出来
                    fragmentTransaction.show(fg1);
                }

                break;
            case R.id.image_button:
                image_button.setImageResource(R.drawable.image_change);
                if(fg2 == null){
                    fg2 = new Image_Fragment();
                    fragmentTransaction.add(R.id.text_frameLayout,fg2);
                }else{
                    fragmentTransaction.show(fg2);
                }
                break;
            case R.id.music_button:
                music_button.setImageResource(R.drawable.music_change);
                if(fg3 == null){
                    fg3 = new Music_Fragment();
                    fragmentTransaction.add(R.id.text_frameLayout,fg3);
                }else{
                    fragmentTransaction.show(fg3);
                }
                break;
            case R.id.movie_button:
                movie_button.setImageResource(R.drawable.movie_change);
                if(fg4 == null){
                    fg4 = new Movie_Fragment();
                    fragmentTransaction.add(R.id.text_frameLayout,fg4);
                }else{
                    fragmentTransaction.show(fg4);
                }
                break;
            case R.id.avatar_bt:
                login_button.setImageResource(R.drawable.avatar_change);
                if(fg5 == null){
                    fg5 = new Login_Fragment();
                    fragmentTransaction.add(R.id.text_frameLayout,fg5);
                }else{
                    fragmentTransaction.show(fg5);
                }
                break;
        }
        fragmentTransaction.commit();
    }
    //重置所有imageView的选中状态
    private void restartButton(){
        text_button.setImageResource(R.drawable.text_button);
        image_button.setImageResource(R.drawable.image);
        music_button.setImageResource(R.drawable.music_button);
        movie_button.setImageResource(R.drawable.movie_button);
        login_button.setImageResource(R.drawable.avatar);
    }

    //重写返回按钮方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build=new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                finish();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .show();
                break;

            default:
                break;
        }
        return false;
    }
}
