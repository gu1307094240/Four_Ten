package com.example.gu.four_ten.json;

import com.example.gu.four_ten.entity.ImageEntity;
import com.example.gu.four_ten.entity.ImageIdEntity;
import com.example.gu.four_ten.entity.MovieEntity;
import com.example.gu.four_ten.entity.MusicEntity;
import com.example.gu.four_ten.entity.MusicIdEntity;
import com.example.gu.four_ten.entity.TextEntity;
import com.example.gu.four_ten.entity.TextIdEntity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by 31452 on 2016/6/19.
 */

/**
 * Json解析数据类
 * 用于解析网络数据
 */
public class JsonUtils {
    private OkHttpClient client;

    /**
     * 用于解析文章二级页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<TextIdEntity> getTextIdEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<TextIdEntity>() {
            @Override
            public void call(final Subscriber<? super TextIdEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                TextIdEntity text_id_entity = new Gson().fromJson(json_value, TextIdEntity.class);
                                if(text_id_entity !=null){
                                    subscriber.onNext(text_id_entity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 用于解析图片二级页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<ImageEntity> getImageEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<ImageEntity>() {
            @Override
            public void call(final Subscriber<? super ImageEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                ImageEntity imageEntity = new Gson().fromJson(json_value, ImageEntity.class);
                                if(imageEntity!=null){
                                    subscriber.onNext(imageEntity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 用于解析图片主页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<ImageIdEntity> getImageIdEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<ImageIdEntity>() {
            @Override
            public void call(final Subscriber<? super ImageIdEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                ImageIdEntity imageIdEntity = new Gson().fromJson(json_value, ImageIdEntity.class);
                                if(imageIdEntity!=null){
                                    subscriber.onNext(imageIdEntity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 用于解析音乐二级页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<MusicEntity> getMusicEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<MusicEntity>() {
            @Override
            public void call(final Subscriber<? super MusicEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                MusicEntity musicEntity = new Gson().fromJson(json_value, MusicEntity.class);
                                if(musicEntity!=null){
                                    subscriber.onNext(musicEntity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 用于解析音乐主页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<MusicIdEntity> getMusicIdEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<MusicIdEntity>() {
            @Override
            public void call(final Subscriber<? super MusicIdEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                MusicIdEntity musicIdEntity = new Gson().fromJson(json_value, MusicIdEntity.class);
                                if(musicIdEntity!=null){
                                    subscriber.onNext(musicIdEntity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 用于解析视频主页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<MovieEntity> getMovieEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<MovieEntity>() {
            @Override
            public void call(final Subscriber<? super MovieEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                MovieEntity movieEntity = new Gson().fromJson(json_value, MovieEntity.class);
                                if(movieEntity!=null){
                                    subscriber.onNext(movieEntity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    public JsonUtils(){
        client = new OkHttpClient();
    }
    /**
     * 用于解析文章主页面的数据List，得到相关数据
     * @param json
     * @return
     */
    public Observable<TextEntity> getTextEntity(final String json) {
        return Observable.create(new Observable.OnSubscribe<TextEntity>() {
            @Override
            public void call(final Subscriber<? super TextEntity> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(json).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String json_value = response.body().string();
                                TextEntity textEntity = new Gson().fromJson(json_value, TextEntity.class);
                                if(textEntity!=null){
                                    subscriber.onNext(textEntity);
                                }

                            }

                        }
                    });
                }
            }
        });
    }

}
