package com.sven.email.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.sven.email.util.Config;

public class DatabaseHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "email.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper databaseHelper;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db != null) {
            db.execSQL("CREATE TABLE " + Config.TABLE_DISPLAY + "("
                    + Config.COLUMN_DISPLAY_ID + " INTEGER PRIMARY KEY, "
                    + Config.COLUMN_DISPLAY_LANG + " TEXT, "
                    + Config.COLUMN_DISPLAY_ANIMATION + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_SHOW_CORRES_NAMES + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_COLORIZE_CONTACTS + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_SHOW_CONTACT_PICTURES + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_COLORIZE_CONTACT_PICTURES + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_CHANGE_COLOR_WHEN_READ + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_THREADED_VIEW + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_SHOW_SPLIT_SCREEN + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_FIXED_WIDTH_FONTS + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_VISIBLE_EMAIL_ACTIONS + " ENUM(0,1), "
                    + Config.COLUMN_DISPLAY_AUTO_FIT_EMAILS + " ENUM(0,1) "
                    + ")");

            db.execSQL("CREATE TABLE " + Config.TABLE_INTERACTION + "("
                    + Config.COLUMN_INTERACTION_ID + " INTEGER PRIMARY KEY, "
                    + Config.COLUMN_INTERACTION_VOLUME_KEY +  " ENUM(0,1), "
                    + Config.COLUMN_INTERACTION_RETURN_TO_LIST + " ENUM(0,1), "
                    + Config.COLUMN_INTERACTION_SHOW_NEXT_EMAIL + " ENUM(0,1) "
                    + ")");
            db.execSQL("CREATE TABLE " + Config.TABLE_NOTIFICATION + "("
                    + Config.COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY, "
                    + Config.COLUMN_NOTIFICATION_QUIET_TIME + " ENUM(0,1), "
                    + Config.COLUMN_NOTIFICATION_DISIBLE_NOTIFICATIONS + " ENUM(0,1) "
                    + ")");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_DISPLAY);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_INTERACTION);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_NOTIFICATION);
        onCreate(db);
    }
}
