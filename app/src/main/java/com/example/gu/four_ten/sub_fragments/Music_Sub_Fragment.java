package com.example.gu.four_ten.sub_fragments;

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

import com.example.gu.four_ten.R;
import com.example.gu.four_ten.common.CollectInfo;
import com.example.gu.four_ten.common.CollectSqlite;
import com.example.gu.four_ten.common.Share;
import com.example.gu.four_ten.common.CommonURL;
import com.example.gu.four_ten.entity.MusicEntity;
import com.example.gu.four_ten.entity.MusicIdEntity;
import com.example.gu.four_ten.json.JsonUtils;
import com.example.gu.four_ten.mediaPlayer.Player;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 31452 on 2016/6/21.
 */
/**
 * 使用Fragment嵌套Fragment的模式实现界面滑动
 * 每个一级Framgment下面有十个相同布局不同音乐页面数据的二级Fragment
 * 通过传递过来的“num”不同，获得不同页面数据并加载到布局容器中并返回view给一级Fragment
 */
public class Music_Sub_Fragment extends Fragment implements View.OnClickListener{

    private int num;
    private View view;
    private JsonUtils utils;
    private JsonUtils utils1;
    private int musicId;
    private String webUrl,title;

    private ImageButton playButton;
    private ImageView cover;
    private ImageView musicAvatar;
    private TextView authorName;
    private TextView desc;
    private TextView storyTitle;
    private TextView story_author;
    private TextView story;

    private Player player;
    private String url;
    private ImageButton musicShareQq;
    private ImageButton musicShareWechat;
    private ImageButton musicShareOther;

    private CheckBox keep;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 获得从一级Fragment换过来的num参数
         */
        Bundle args = getArguments();
        num = args.getInt("num");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_music, (ViewGroup)getActivity().findViewById(R.id.viewPager_text), false);
        /**
         * 初始化布局控件
         */
        playButton = (ImageButton) view.findViewById(R.id.playButton);
        cover = (ImageView)view.findViewById(R.id.cover);
        musicAvatar = (ImageView)view.findViewById(R.id.music_Avatar);
        authorName = (TextView)view.findViewById(R.id.authorName);
        desc = (TextView)view.findViewById(R.id.desc);
        storyTitle = (TextView)view.findViewById(R.id.story_title);
        story_author = (TextView)view.findViewById(R.id.story_author);
        story = (TextView)view.findViewById(R.id.story);
        musicShareQq = (ImageButton)view.findViewById(R.id.musicShareqq);
        musicShareWechat = (ImageButton)view.findViewById(R.id.musicSharewechat);
        musicShareOther = (ImageButton)view.findViewById(R.id.musicShareOther);
        keep = (CheckBox)view.findViewById(R.id.keep);
        /**
        * 给不同按钮加载监听事件
        */
        musicShareQq.setOnClickListener(this);
        musicShareWechat.setOnClickListener(this);
        musicShareOther.setOnClickListener(this);
        keep.setOnClickListener(this);

        utils = new JsonUtils();
        utils1 = new JsonUtils();
        /**
         * 通过调用JsonUtils的getMusicIdEntity方法获得DataBeen中的数据集合
         * 主要是为了获得其中的musicId
         */
        utils1.getMusicIdEntity(CommonURL.music_ID_URL).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MusicIdEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MusicIdEntity musicIdEntity) {
                List<MusicIdEntity.DataBean> idList = musicIdEntity.getData();
                try{
                    musicId = Integer.parseInt(idList.get(idList.size()-num-1).getId());
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                /**
                 * 通过调用JsonUtils的getMusicEntity方法获得DataBeen中的数据集合
                 * 其中根据传入的不同的musicId得到不同的DataBeen数据集合
                 */
                utils.getMusicEntity(CommonURL.music_URL+musicId+"?").observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MusicEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MusicEntity musicEntity) {
                        Picasso.with(getActivity()).load(musicEntity.getData().getCover()).resize(1000, 800).into(cover);
                        Picasso.with(getActivity()).load(musicEntity.getData().getAuthor().getWeb_url()).resize(150,150).into(musicAvatar);
                        authorName.setText(musicEntity.getData().getAuthor().getUser_name());
                        desc.setText(musicEntity.getData().getAuthor().getDesc());
                        title = musicEntity.getData().getStory_title();
                        storyTitle.setText(title);
                        story_author.setText(musicEntity.getData().getCharge_edt());
                        story.setText(musicEntity.getData().getLyric()+"\n\n");
                        webUrl = musicEntity.getData().getWeb_url();
                        if(num % 2==0){
                            url = CommonURL.music_URL1;
                        }else {
                            url = CommonURL.music_URL2;
                        }


                        player = new Player(url);
                        playButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean pause = player.pause();
                                if (pause) {
                                    playButton.setImageResource(R.drawable.playbutton);
                                    player.stop();
                                } else {
                                    playButton.setImageResource(R.drawable.stopbutton);
                                    player.play();
                                }
                            }
                        });
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
            case R.id.musicShareqq:
                share.shareQQFriend("音乐", webUrl, Share.TEXT, null);
                break;
            case R.id.musicSharewechat:
                share.shareWeChatFriend("音乐", webUrl, Share.TEXT, null);
                break;
            case R.id.musicShareOther:
                share.shareOthers("音乐", webUrl, Share.TEXT, null);
                break;
            case R.id.keep:
                CollectSqlite sqlite = new CollectSqlite(this.getContext());
                CollectInfo info = new CollectInfo();
                info.setTag("music");
                info.setTitle(title);
                info.setUrl(webUrl);
                if (keep.isChecked()){
                    sqlite.save(info);
                }else{
                    sqlite.delete(title);
                }
                break;
        }
    }
}