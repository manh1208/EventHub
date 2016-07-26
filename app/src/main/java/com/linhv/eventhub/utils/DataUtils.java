package com.linhv.eventhub.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

//import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by ManhNV on 6/16/2016.
 */
public class DataUtils {
    public static String URL = "http://202.78.227.93:6996";
//public static String URL = "http://192.168.150.32:19291";


    private static DataUtils INSTANCE = null;
    public static GoogleApiClient mGoogleApiClient;
    private SharedPreferences mPreferences;
    private Context mContext;

    private DataUtils(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences("SuperB", 0);
    }

    public static DataUtils getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataUtils(context);
        }
        return INSTANCE;
    }

    public static String getFilePath(String name) {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name;
        return dir;
    }


    public static File getImageFileFromUri(Context context, Uri imageUri, String imagePath, int maxPicel, Bitmap.CompressFormat imageType, int imageQuality) {

        File f = new File(imagePath);
        try {
            f.createNewFile();
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            bitmap = DataUtils.scaleDown(bitmap, 2000, false);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(imageType, imageQuality /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static String getUpvoteString(int numOfUpvote) {
        String s = "";
        if (numOfUpvote > 0) {
            s += " | ";
            if (numOfUpvote > 999) {
                s += numOfUpvote / 1000 > 0 ? numOfUpvote / 1000 + "k" : "";
                numOfUpvote = numOfUpvote % 1000;
                s += numOfUpvote > 0 ? (numOfUpvote / 100 > 0 ? numOfUpvote / 100 : "") : "";
            } else {
                s += numOfUpvote;
            }
        }
        return s;
    }

    public static Date parseDatetime(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(s);
        return date;
    }

    public static String parseDatetimeAPI(String s,String format){
        s = s.replaceAll("\\D+","");
        java.sql.Date date = new java.sql.Date(Long.parseLong(s));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getTime(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse(s);

        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();
        long time = 0;
        time = currentDate.getTime() - date.getTime();
        String interval = "";
        long temp = 0;
        if ((temp = TimeUnit.SECONDS.convert(time, TimeUnit.MILLISECONDS)) < 60) {
            interval = temp + " second" + (temp > 1 ? "s" : "");
        } else if ((temp = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS)) < 60) {
            interval = temp + " minute" + (temp > 1 ? "s" : "");
        } else if ((temp = TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS)) < 24) {
            interval += temp + " hour" + (temp > 1 ? "s" : "");
        } else if ((temp = TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS)) < 30) {
            interval += temp + " day" + (temp > 1 ? "s" : "");
        } else {
            interval = s;
        }
        return interval;
    }

    public SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public static void getAlphaAmination(View v){
        if (v!=null) {
            AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.6f);
            animation1.setDuration(200);
            animation1.setRepeatCount(1);
            animation1.setRepeatMode(Animation.REVERSE);
            v.startAnimation(animation1);
        }
    }

    public void ConnectionError(){
        Toast.makeText(mContext, "Lỗi kết nối. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
    }

}
