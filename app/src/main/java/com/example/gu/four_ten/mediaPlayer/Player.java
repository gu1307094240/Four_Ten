package com.example.gu.four_ten.mediaPlayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by 31452 on 2016/6/24.
 */

/**
 * 播放音乐的实现
 */
public class Player implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;//媒体播放器
    private String vedioUrl;//视频地址
    private boolean pause;
    // 初始化播放器
    public Player(String vedioUrl) {
        this.vedioUrl = vedioUrl;
        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        }catch (Exception e){
            Log.e("mediaPayer","error",e);
        }
    }
//开始播放方法
    public void play(){
        playNet(0);
    }
//播放停止方法
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
//播放暂停方法
    public boolean pause(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            pause = true;
        }else {
                mediaPlayer.start();
                pause = false;
            }
        return pause;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("mediaPlayer", "onCompletion");
    }

    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Log.e("mediaPlayer","onPrepared");
    }

    private void playNet(int playPosition){
        try{
            mediaPlayer.reset();// 把各项参数恢复到初始状态
            /**
             * * 通过MediaPlayer.setDataSource()
             * * 的方法,将URL或文件路径以字符串的方式传入.使用setDataSource ()方法时,要注意以下三点:
             * * 1.构建完成的MediaPlayer 必须实现Null 对像的检查.
             * * 2.必须实现接收IllegalArgumentException 与IOException
             * * 等异常,在很多情况下,你所用的文件当下并不存在. 3.若使用URL 来播放在线媒体文件,该文件应该要能支持pragressive
             * *下载.
             * */

            mediaPlayer.setDataSource(vedioUrl);
            mediaPlayer.prepare();// 进行缓冲
            mediaPlayer.setOnPreparedListener(new MyPreparedListener(playPosition));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private final class MyPreparedListener implements MediaPlayer.OnPreparedListener{

        private int playPosition;

        public MyPreparedListener(int playPosition) {
            this.playPosition = playPosition;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
            if(playPosition>0){
                mediaPlayer.seekTo(playPosition);
            }
        }
    }
}
