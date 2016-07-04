package com.linhv.eventhub.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ManhNV on 06/22/2016.
 */
public class DbUtil extends SQLiteOpenHelper {
    public static final String QUESTION_TABLE_NAME = "Questions";
    private static final String CREATE_MEMBERS_TABLE_SCRIPT =
            "CREATE TABLE " + QUESTION_TABLE_NAME + "(" +
                    "Id Integer Primary Key ," +
                    "Content Integer ," +
                    "Active Text ," +
                    "Category Integer ," +
                    "Answer Integer" +
                    ")";



    public DbUtil(Context context) {
        super(context, "SuperB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_MEMBERS_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {

        }
    }
}
