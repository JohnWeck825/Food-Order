package com.example.foodorder.function;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class ContactFunction{
    public static void onClickOpenFacebook(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/183670301827578/"));
//        if (isPackageInstalled(context, "com.facebook.katana")) {
            context.startActivity(intent);
//        } else {
//            // Hiển thị thông báo cho người dùng
//            Toast.makeText(context, "Bạn cần cài đặt ứng dụng Facebook", Toast.LENGTH_SHORT).show();
//        }

//        Intent intent;
//        try {
//            String urlFacebook = "https://www.facebook.com/groups/183670301827578/";
//            PackageManager packageManager = context.getPackageManager();
//            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
//            if (versionCode >= 3002850) { //newer versions of fb app
//                urlFacebook = "https://www.facebook.com/groups/183670301827578/";
//            }
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlFacebook));
//        } catch (Exception e) {
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/183670301827578/"));
//        }
//        context.startActivity(intent);
    }
    public static void onClickOpenHotline(Context context){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0387874514"));
        context.startActivity(intent);
    }
    public static void onClickOpenGmail(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox"));
        context.startActivity(intent);
    }
    public static void onClickOpenYoutube(Context context){
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=fvowi8bhvnA&ab_channel=B%E1%BA%A3ooSADBoy")));
    }
    public static void onClickOpenZalo(Context context){
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
    }

    private static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
