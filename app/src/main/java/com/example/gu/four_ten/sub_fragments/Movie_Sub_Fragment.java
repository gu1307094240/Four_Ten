package com.example.gu.four_ten.sub_fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.widget.VideoView;

import com.example.gu.four_ten.PlayMovieActivity;
import com.example.gu.four_ten.R;
import com.example.gu.four_ten.common.CollectInfo;
import com.example.gu.four_ten.common.CollectSqlite;
import com.example.gu.four_ten.common.Share;
import com.example.gu.four_ten.common.CommonURL;
import com.example.gu.four_ten.entity.MovieEntity;
import com.example.gu.four_ten.json.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 31452 on 2016/6/25.
 */
/**
 * 使用Fragment嵌套Fragment的模式实现界面滑动
 * 每个一级Framgment下面有十个相同布局不同视频页面数据的二级Fragment
 * 通过传递过来的“num”不同，获得不同页面数据并加载到布局容器中并返回view给一级Fragment
 */
public class Movie_Sub_Fragment extends Fragment implements View.OnClickListener {

    private int num;
    private View view;
    private JsonUtils utils;


    private VideoView videoView;
    private TextView movieTitle;
    private TextView description;
    private TextView category;
    private ImageView feed;
    private ImageButton playmovie;

    private String shareURL,title;
    private ImageButton movieShareQq;
    private ImageButton movieShareWechat;
    private ImageButton movieShareOther;

    private CheckBox keep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 获得从一级Fragment换过来的num参数
         */
        Bundle args = getArguments();
        num = args.getInt("num");

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_movie, (ViewGroup) getActivity().findViewById(R.id.viewPager_text), false);
        /**
        * 初始化布局控件
        */
        movieTitle = (TextView)view.findViewById(R.id.movieTitle);
        description = (TextView)view.findViewById(R.id.description);
        category = (TextView)view.findViewById(R.id.category);
        feed = (ImageView)view.findViewById(R.id.feed);
        playmovie = (ImageButton)view.findViewById(R.id.playmovie);

        movieShareQq = (ImageButton)view.findViewById(R.id.movieShareqq);
        movieShareWechat = (ImageButton)view.findViewById(R.id.movieSharewechat);
        movieShareOther = (ImageButton)view.findViewById(R.id.movieShareOther);
        keep = (CheckBox)view.findViewById(R.id.keep);
        /**
         * 给不同按钮加载监听事件
         */
        movieShareWechat.setOnClickListener(this);
        movieShareQq.setOnClickListener(this);
        movieShareOther.setOnClickListener(this);
        keep.setOnClickListener(this);
         /**
         * 通过调用JsonUtils的getMovieIdEntity方法获得ItemList中的数据集合
         * 其中根据传入的不同的movie_URL得到不同的ItemList数据集合
         */

        utils = new JsonUtils();
        utils.getMovieEntity(CommonURL.movie_URL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MovieEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(MovieEntity movieEntity) {
                List<MovieEntity.ItemListBean> list = movieEntity.getItemList();
                title = list.get(num).getData().getTitle();
                movieTitle.setText(title);
                description.setText(list.get(num).getData().getDescription());
                category.setText(list.get(num).getData().getCategory());
                Picasso.with(getActivity()).load(list.get(num).getData().getCover().getFeed()).resize(1000,600).into(feed);
                final String uri = Uri.parse(list.get(num).getData().getPlayUrl()).toString();
                shareURL = list.get(num).getData().getWebUrl().getRaw();
                playmovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),PlayMovieActivity.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
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
            case R.id.movieShareqq:
                share.shareQQFriend("电影", shareURL, Share.TEXT, null);
                break;
            case R.id.imageSharewechat:
                share.shareWeChatFriend("电影", shareURL, Share.TEXT, null);
                break;
            case R.id.movieShareOther:
                share.shareOthers("电影", shareURL,Share.TEXT,null);
                break;
            case R.id.keep:
                CollectSqlite sqlite = new CollectSqlite(this.getContext());
                CollectInfo info = new CollectInfo();
                info.setTag("movie");
                info.setTitle(title);
                info.setUrl(shareURL);
                if (keep.isChecked()){
                    sqlite.save(info);
                }else{
                    sqlite.delete(title);
                }
                break;
        }
    }
}
