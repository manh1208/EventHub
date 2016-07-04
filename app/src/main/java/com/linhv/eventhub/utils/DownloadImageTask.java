package com.linhv.eventhub.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

//import com.henchman.superb.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ManhNV on 6/23/2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Context mcontext;

    public DownloadImageTask(ImageView bmImage,Context context) {
        this.bmImage = bmImage;
        this.mcontext = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay;
        Bitmap mIcon1 = null;
        if (urls[0].length()>0) {
            urldisplay = urls[0];
//            try {
//                URL url= new URL(urldisplay);
//                mIcon1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon1 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else{

//            mIcon1 = BitmapFactory.decodeResource(mcontext.getResources(),R.drawable.image_default_avatar);
        }

        return mIcon1;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}
