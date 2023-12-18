package com.sven.email;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sven.email.database.DatabaseHelper;
import com.sven.email.database.DatabaseQueryClass;
import com.sven.email.database.DisplayData;
import com.sven.email.database.InteractionData;
import com.sven.email.database.NotificationData;
import com.sven.email.util.Config;

public class Email_Launcher extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase database = openOrCreateDatabase("email.db", MODE_PRIVATE, null);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        boolean exists_display = false;
        boolean exists_interaction = false;
        boolean exists_notification = false;

        try{
            cursor = sqLiteDatabase.query(Config.TABLE_DISPLAY, null, null, null, null, null, null, null);
            exists_display = (cursor != null && cursor.getCount() > 0);
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_INTERACTION, null, null, null, null, null, null, null);
            exists_interaction = (cursor != null && cursor.getCount() > 0);
        }catch(Exception e) {
            Log.d("asdf","asdfsdf");
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_NOTIFICATION, null, null, null, null, null, null, null);
            exists_notification = (cursor != null && cursor.getCount() > 0);
        }catch(Exception e) {
            Log.d("asdf","asdfsdf");
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }

        if(!exists_display) {
            DisplayData displayData = new DisplayData(1, "en", 1, 1,1,1, 1,1,1,1,1,1,1 );
            DatabaseQueryClass databaseQueryClass;
            databaseQueryClass = new DatabaseQueryClass(this);
            databaseQueryClass.insertDisplayData(displayData);
        }
        if(!exists_interaction) {
            InteractionData interactionData = new InteractionData(1, 1, 1,1);
            DatabaseQueryClass databaseQueryClass;
            databaseQueryClass = new DatabaseQueryClass(this);
            databaseQueryClass.insertInteractionData(interactionData);
        }
        if(!exists_notification) {
            NotificationData notificationData = new NotificationData(1, 1, 1);
            DatabaseQueryClass databaseQueryClass;
            databaseQueryClass = new DatabaseQueryClass(this);
            databaseQueryClass.insertNotificationData(notificationData);
        }
    }
}
