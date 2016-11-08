package com.example.gu.four_ten;

/*
全屏播放视频
 */
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayMovieActivity extends AppCompatActivity {

    private Uri uri1;
    private VideoView videoView;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.full_movie);

         Toast.makeText(this,"缓冲中...",Toast.LENGTH_LONG).show();
         Intent intent_accept = getIntent();
         Bundle bundle = intent_accept.getExtras(); //获得传过来的URL
         uri = bundle.getString("uri");
         uri1 = Uri.parse(uri);
         videoView = (VideoView)this.findViewById(R.id.videoView2);
        //设置视频播放控制器，控制播放
         videoView.setMediaController(new MediaController(this));
         videoView.setVideoURI(uri1);
         videoView.requestFocus();
    }

}
