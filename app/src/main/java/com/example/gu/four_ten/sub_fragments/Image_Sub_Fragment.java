package com.example.gu.four_ten.sub_fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gu.four_ten.R;
import com.example.gu.four_ten.common.CollectInfo;
import com.example.gu.four_ten.common.CollectSqlite;
import com.example.gu.four_ten.common.Share;
import com.example.gu.four_ten.common.CommonURL;
import com.example.gu.four_ten.entity.ImageEntity;
import com.example.gu.four_ten.entity.ImageIdEntity;
import com.example.gu.four_ten.json.JsonUtils;
import com.squareup.picasso.Picasso;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 31452 on 2016/6/22.
 */

/**
 * 使用Fragment嵌套Fragment的模式实现界面滑动
 * 每个一级Framgment下面有十个相同布局不同图片页面数据的二级Fragment
 * 通过传递过来的“num”不同，获得不同页面数据并加载到布局容器中并返回view给一级Fragment
 */

public class Image_Sub_Fragment extends Fragment implements View.OnClickListener{

    private int num;
    private View view;
    private JsonUtils utils;
    private JsonUtils utils1;
    private int imageId;
    private ImageView image1;
    private TextView imageTitle;
    private TextView authorbrief;
    private TextView imageTimes;
    private TextView text1;
    private String imageUrl,title;
    private ImageButton imageShareQq;
    private ImageButton imageShareWechat;
    private ImageButton imageShareOther;
    private CheckBox keep;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 获得从一级Fragment换过来的num参数
         */
        Bundle args = getArguments();
        num = args.getInt("num");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_image, (ViewGroup)getActivity().findViewById(R.id.viewPager_text), false);
        /**
         * 初始化布局控件
         */
        image1 = (ImageView)view.findViewById(R.id.image1);
        imageTitle = (TextView)view.findViewById(R.id.imageTitle);
        authorbrief = (TextView)view.findViewById(R.id.authorbrief);
        imageTimes = (TextView)view.findViewById(R.id.imageTimes);
        text1 = (TextView)view.findViewById(R.id.text1);

        imageShareQq = (ImageButton)view.findViewById(R.id.imageShareqq);
        imageShareWechat = (ImageButton)view.findViewById(R.id.imageSharewechat);
        imageShareOther = (ImageButton)view.findViewById(R.id.imageShareweother);
        keep = (CheckBox)view.findViewById(R.id.keep);
        /**
         * 给不同按钮加载监听事件
         */
        imageShareQq.setOnClickListener(this);
        imageShareWechat.setOnClickListener(this);
        imageShareOther.setOnClickListener(this);
        keep.setOnClickListener(this);
        /**
         * 通过调用JsonUtils的getImageIdEntity方法获得ResultBeen中的数据集合
         * 主要是为了获得其中的imageId
         */
        utils = new JsonUtils();
        utils1 = new JsonUtils();
        utils1.getImageIdEntity(CommonURL.image_ID_URL).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ImageIdEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ImageIdEntity imageIdEntity) {
                List<ImageIdEntity.ResultBean> idList = imageIdEntity.getResult();
                imageId = idList.get(num).getId();
                /**
                 * 通过调用JsonUtils的getImageEntity方法获得ResultBeen中的数据集合
                 * 其中根据传入的不同的imageId得到不同的ResultBeen数据集合
                 */
                utils.getImageEntity(CommonURL.image_URL+imageId).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ImageEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ImageEntity imageEntity) {
                        imageUrl = "http://images.shigeten.net/" + imageEntity.getImage1();
                        Picasso.with(getActivity()).load(imageUrl).resize(1000, 800).into(image1);
                        title = imageEntity.getTitle();
                        imageTitle.setText(title);
                        authorbrief.setText(imageEntity.getAuthorbrief());
                        String getTime = Integer.toString(imageEntity.getTimes());
                        imageTimes.setText(getTime+" 人 已阅");
                        text1.setText(imageEntity.getText1()+"\n");

                    }
                });
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            case R.id.imageShareqq:

                share.shareQQFriend("美图", imageUrl, Share.TEXT, null);
                break;
            case R.id.imageSharewechat:

                share.shareWeChatFriend("美图", imageUrl, Share.TEXT, null);
                break;
            case R.id.imageShareweother:
                share.shareOthers("美图", imageUrl, Share.TEXT, null);
            case R.id.keep:
//                SQLiteDatabase db = getActivity().openOrCreateDatabase("collect.db", Context.MODE_PRIVATE, null);
//                db.execSQL("create table if not exists table_collect(id integer primary key autoincrement,tag text not null,title text not null,url text not null)");
                CollectSqlite sqlite = new CollectSqlite(this.getContext());
                CollectInfo info = new CollectInfo();
                info.setTag("image");
                info.setTitle(title);
                info.setUrl(imageUrl);
                if (keep.isChecked()){
                    sqlite.save(info);
                }else{
                    sqlite.delete(title);
                }
//                db.close();
                break;
        }
    }
}
