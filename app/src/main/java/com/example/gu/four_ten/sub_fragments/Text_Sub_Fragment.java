package com.example.gu.four_ten.sub_fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gu.four_ten.R;
import com.example.gu.four_ten.common.CollectInfo;
import com.example.gu.four_ten.common.CollectSqlite;
import com.example.gu.four_ten.common.DBOpenHandler;
import com.example.gu.four_ten.common.Share;
import com.example.gu.four_ten.common.CommonURL;
import com.example.gu.four_ten.entity.TextEntity;
import com.example.gu.four_ten.entity.TextIdEntity;
import com.example.gu.four_ten.json.JsonUtils;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 31452 on 2016/6/22.
 */
/**
 * 使用Fragment嵌套Fragment的模式实现界面滑动
 * 每个一级Framgment下面有十个相同布局不同文章页面数据的二级Fragment
 * 通过传递过来的“num”不同，获得不同页面数据并加载到布局容器中并返回view给一级Fragment
 */
public class Text_Sub_Fragment extends Fragment implements View.OnClickListener{

    private int num;
    private View view;
    private JsonUtils utils;
    private JsonUtils utils1;
    private int textId;
    private TextView texts;
    private TextView summary;
    private TextView author;
    private TextView titles;
    private TextView times;
    private ImageButton shareQQ;
    private ImageButton shareWechat;
    private ImageButton shareOther;
    private CheckBox keep;
    private String getTexts;
    String getTitles;
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





//        preferences = getActivity().getSharedPreferences("keep", getActivity().MODE_PRIVATE);
//        editor = preferences.edit();
        /**
         * 获得从一级Fragment换过来的num参数
         */
        Bundle args = getArguments();
        num = args.getInt("num");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_text, (ViewGroup)getActivity().findViewById(R.id.viewPager_text), false);
        /**
         * 初始化布局控件
         */
        texts = (TextView)view.findViewById(R.id.texts);
        summary = (TextView)view.findViewById(R.id.summary);
        author = (TextView)view.findViewById(R.id.author);
        titles = (TextView)view.findViewById(R.id.titles);
        times = (TextView)view.findViewById(R.id.lookData);
        shareQQ = (ImageButton)view.findViewById(R.id.testShareqq);
        shareWechat = (ImageButton)view.findViewById(R.id.testSharewechat);
        shareOther = (ImageButton)view.findViewById(R.id.testShareOther);
        keep = (CheckBox)view.findViewById(R.id.keep);
        /**
        * 给不同按钮加载监听事件
        */
        shareQQ.setOnClickListener(this);
        shareWechat.setOnClickListener(this);
        shareOther.setOnClickListener(this);
        keep.setOnClickListener(this);

        utils1 = new JsonUtils();
        utils = new JsonUtils();
        /**
         * 通过调用JsonUtils的getTextEntity方法获得ResultBeen中的数据集合
         * 主要是为了获得其中的textId
         */
        utils1.getTextEntity(CommonURL.text_URL).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TextEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(TextEntity textEntity) {
                List<TextEntity.ResultBean> resultBeanList = textEntity.getResult();
                textId = resultBeanList.get(num).getId();
                /**
                 * 通过调用JsonUtils的getMusicEntity方法获得DataBeen中的数据集合
                 * 其中根据传入的不同的textId得到不同的数据集合
                 */
                utils.getTextIdEntity(CommonURL.text_son_URL + textId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TextIdEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TextIdEntity text_id_entity) {
                        getTexts = text_id_entity.getText();
                        getTitles = text_id_entity.getTitle();
                        String getAuthor = text_id_entity.getAuthor();
                        String getSummary = text_id_entity.getSummary();
                        int getTimes = text_id_entity.getTimes();
                        String getTime = Integer.toString(getTimes);
                        texts.setText(getTexts+"\n\n");
                        titles.setText(getTitles);
                        author.setText(getAuthor+"  著");
                        summary.setText(getSummary);
                        times.setText(getTime+" 人 已阅");
                    }
                });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        if(viewGroup!=null){
            viewGroup.removeAllViewsInLayout();
        }
        return view;
    }
    /**
     * 设置分享按钮监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Share share = new Share(getActivity());
        switch (v.getId()){
            case R.id.testShareqq:
                share.shareQQFriend("美文", CommonURL.text_son_URL+textId, Share.TEXT, null);
                break;
            case R.id.testSharewechat:
                share.shareWeChatFriend("美文", CommonURL.text_son_URL + textId, Share.TEXT, null);
                break;
            case R.id.testShareOther:
                share.shareOthers("美文", CommonURL.text_son_URL + textId, Share.TEXT, null);
                break;
            case R.id.keep:
//                SQLiteDatabase db = getActivity().openOrCreateDatabase("collect.db", Context.MODE_PRIVATE, null);
//                db.execSQL("create table if not exists table_collect(id integer primary key autoincrement,tag text not null,title text not null,url text not null)");
                CollectSqlite sqlite = new CollectSqlite(this.getContext());
                CollectInfo info = new CollectInfo();
                info.setTag("text");
                info.setTitle(getTitles);
                info.setUrl(CommonURL.text_son_URL + textId);
                if (keep.isChecked()){
                    sqlite.save(info);
                }else{
                    sqlite.delete(getTitles);
                }

//                List<CollectInfo> lists = sqlite.findAll();
//                for (CollectInfo list:lists
//                     ) {
//                    Toast.makeText(getActivity(),list.getTitle(),Toast.LENGTH_LONG).show();
//                }
//                db.close();
                break;
//                SQLiteDatabase db = getActivity().openOrCreateDatabase("collect.db", Context.MODE_PRIVATE, null);
//                db.execSQL("create table if not exists collection(_id integer primary key autoincrement,tag text not null,title text not null)");
//                if (keep.isChecked()){
//                    ContentValues values = new ContentValues();
//                    values.put("tag", "text");
//                    values.put("title",getTitles);
//                    db.insert("collection", null, values);
//                }else {
//                    db.delete("collection","title=?",new String[]{getTitles});
//                }
//
//                Cursor c = db.query("collection",null,null,null,null,null,"_id");
//                if (c!=null){
//                    String [] columns = c.getColumnNames();
//                    while (c.moveToNext()){
//                        for (String column:columns
//                             ) {
//                            Toast.makeText(getActivity(),c.getString(c.getColumnIndex(column)),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    c.close();
//                }
//                db.close();

//                if (keep.isChecked()){
//                    editor.putString("text","gullingfeng");
//                    editor.commit();
//                }else{
//                    editor.remove("text");
//                    editor.commit();
//                }

        }
    }
}
