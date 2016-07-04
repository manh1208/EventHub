package com.linhv.eventhub.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.linhv.eventhub.utils.QuickSharePreferences;

/**
 * Created by ManhNV on 6/15/2016.
 */
public class CustomTextViewLight extends TextView {

    public CustomTextViewLight(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextViewLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(),
                QuickSharePreferences.FONT_LIGHT);
        setTypeface(face);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }
}
