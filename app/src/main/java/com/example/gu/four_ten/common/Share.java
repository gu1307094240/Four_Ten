package com.example.gu.four_ten.common;

/**
 * Created by 31452 on 2016/6/28.
 */

import android.content.Context;

import java.util.List;

        import android.annotation.SuppressLint;
        import android.content.ComponentName;
import android.content.Intent;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.provider.MediaStore;
        import android.widget.Toast;


public class Share {

    private Context context;
    /** 文本类型 */
    public static int TEXT = 0;
    /** 图片类型 */
    public static int DRAWABLE = 1;

    public Share(Context context) {
        this.context = context;
    }

    /**
     * 分享到QQ好友
     *
     * @param msgTitle
     *            （分享标题）
     * @param msgText
     *            （分享内容）
     * @param type
     *            （分享类型）
     * @param drawable
     *            （分享图片，若分享类型为AndroidShare.TEXT，则可以为null）
     */
    public void shareQQFriend(String msgTitle, String msgText, int type,
                              Drawable drawable) {

        shareMsg("com.tencent.mobileqq",
                "com.tencent.mobileqq.activity.JumpActivity", "QQ", msgTitle,
                msgText, type, drawable);
    }

    /**
     * 分享到微信好友
     *
     * @param msgTitle
     *            （分享标题）
     * @param msgText
     *            （分享内容）
     * @param type
     *            （分享类型）
     * @param drawable
     *            （分享图片，若分享类型为AndroidShare.TEXT，则可以为null）
     */
    public void shareWeChatFriend(String msgTitle, String msgText, int type,
                                  Drawable drawable) {

        shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI", "微信",
                msgTitle, msgText, type, drawable);
    }

    /**
     * 分享到其它
     *
     * @param msgTitle
     *            （分享标题）
     * @param msgText
     *            （分享内容）
     * @param type
     *            （分享类型）
     * @param drawable
     *            （分享图片，若分享类型为AndroidShare.TEXT，则可以为null）
     */
    public void shareOthers(String msgTitle, String msgText, int type,
                            Drawable drawable) {

        shareMsg("", "", "其他", msgTitle, msgText, type, drawable);
    }

    /**
     * 点击分享的代码
     *
     * @param packageName
     *            （包名）
     * @param activityName
     *            （类名）
     * @param appname
     *            （应用名）
     * @param msgTitle
     *            （标题）
     * @param msgText
     *            （内容）
     * @param type
     *            （发送类型：text or pic 微信朋友圈只支持pic）
     */
    @SuppressLint("NewApi")
    private void shareMsg(String packageName, String activityName,
                          String appname, String msgTitle, String msgText, int type,
                          Drawable drawable) {
        if (!packageName.isEmpty() && !isAvilible(context, packageName)) {// 判断APP是否存在
            Toast.makeText(context, "请先安装" + appname, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent intent = new Intent("android.intent.action.SEND");
        if (type == Share.TEXT) {
            intent.setType("text/plain");
        } else if (type == Share.DRAWABLE) {
            intent.setType("image/png");
            //将项目图片转换为uri
            BitmapDrawable bd = (BitmapDrawable) drawable;
            Bitmap bt = bd.getBitmap();
            final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                    context.getContentResolver(), bt, null, null));
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }

        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!packageName.isEmpty()) {
            intent.setComponent(new ComponentName(packageName, activityName));
            context.startActivity(intent);
        } else {
            context.startActivity(Intent.createChooser(intent, msgTitle));
        }
    }

    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }
}
