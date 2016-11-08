package com.example.gu.four_ten.common;


/**
 * Created by 31452 on 2016/6/19.
 */

/**
 * 网络数据地址及服务器地址
 */
public class CommonURL {
    public static final String text_URL = "http://api.shigeten.net/api/Novel/GetNovelList";//文章主页面的数据地址
    public static final String text_son_URL = "http://api.shigeten.net/api/Novel/GetNovelContent?id=";//文章二级页面的数据地址
    public static final String image_ID_URL = "http://api.shigeten.net/api/Critic/GetCriticList";//图片主页面的数据地址
    public static final String image_URL = "http://api.shigeten.net/api/Diagram/GetDiagramContent?id=";//图片二级页面的数据地址
    public static final String music_ID_URL = "http://v3.wufazhuce.com:8000/api/related/music/list";//音乐主页面的数据地址
    public static final String music_URL = "http://v3.wufazhuce.com:8000/api/music/detail/";//音乐二级页面的数据部分地址
    public static final String movie_URL = "http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=weekly&udid=86dd10ded74f477e96f67b635c68b0c8518b8487&vc=113&vn=2.2.1&deviceModel=Custom%20Phone%20-%204.4.4%20-%20API%2019%20-%20768x1280&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=19";//视频页面数据地址
    public static final String music_URL1 = "http://111.178.233.41/music.wufazhuce.com/lp7Uzdzm0q1_RyxnuHpHQSInfo9t?wsiphost=local";//音乐播放地址（其一）
    public static final String music_URL2 = "http://58.51.150.67/music.wufazhuce.com/lvTXS6GevKeJNGiIs_aNKfd7rBH3?wsiphost=local";//音乐播放地址（其二）
    public static final String register_URL = "http://192.168.0.114:8080/ssh/registerAndroid";//服务器注册地址
    public static final String login_URL = "http://192.168.0.114:8080/ssh/loginAndroid";//服务器登录地址
}
