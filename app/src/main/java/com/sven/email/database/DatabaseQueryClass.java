package com.sven.email.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sven.email.util.Config;

public class DatabaseQueryClass {
    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
    }

    public DatabaseQueryClass(RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
    }

    public DisplayData getDisplayData() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_DISPLAY, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    DisplayData displayData = null;
                    do {
                        int idIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_ID);
                        int langIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_LANG);
                        int aniIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_ANIMATION);
                        int showCorresIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_SHOW_CORRES_NAMES);
                        int colorize_contactsIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_COLORIZE_CONTACTS);
                        int showContactIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_SHOW_CONTACT_PICTURES);
                        int showColorizeIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_COLORIZE_CONTACT_PICTURES);
                        int change_color_when_readIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_CHANGE_COLOR_WHEN_READ);
                        int threaded_viewIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_THREADED_VIEW);
                        int show_split_screenIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_SHOW_SPLIT_SCREEN);
                        int fixed_width_fontsIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_FIXED_WIDTH_FONTS);
                        int visible_email_actionsIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_VISIBLE_EMAIL_ACTIONS);
                        int auto_fit_emailsIndex = cursor.getColumnIndex(Config.COLUMN_DISPLAY_AUTO_FIT_EMAILS);

                        int id, ani, showCorres, colorize_contact, showContact, showColorize, change_col_read, thread_view, show_splite, fixed_wid, visible_email, auto_fit ;
                        String lang;
                        if(idIndex != -1
                            && langIndex != -1
                            && aniIndex != -1
                            && showCorresIndex != -1
                            && colorize_contactsIndex !=-1
                            && showContactIndex != -1
                            && showColorizeIndex != -1
                            && change_color_when_readIndex != -1
                            && threaded_viewIndex != -1
                            && show_split_screenIndex != -1
                            && fixed_width_fontsIndex != -1
                            && visible_email_actionsIndex != -1
                            && auto_fit_emailsIndex != -1) {
                            id = cursor.getInt(idIndex);
                            lang = cursor.getString(langIndex);
                            ani = cursor.getInt(aniIndex);
                            showCorres = cursor.getInt(showCorresIndex);
                            colorize_contact = cursor.getInt(colorize_contactsIndex);
                            showContact = cursor.getInt(showContactIndex);
                            showColorize = cursor.getInt(showColorizeIndex);
                            change_col_read = cursor.getInt(change_color_when_readIndex);
                            thread_view = cursor.getInt(threaded_viewIndex);
                            show_splite = cursor.getInt(show_split_screenIndex);
                            fixed_wid = cursor.getInt(fixed_width_fontsIndex);
                            visible_email = cursor.getInt(visible_email_actionsIndex);
                            auto_fit = cursor.getInt(auto_fit_emailsIndex);
                              displayData = new DisplayData(id, lang, ani, showCorres, colorize_contact, showContact, showColorize, change_col_read, thread_view, show_splite, fixed_wid, visible_email, auto_fit);

                        }
                    }   while (cursor.moveToNext());

                    return displayData;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return null;
    }

    public void insertDisplayData(DisplayData displayData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_DISPLAY_LANG, displayData.getLang());
        contentValues.put(Config.COLUMN_DISPLAY_ANIMATION, displayData.getAnimation());
        contentValues.put(Config.COLUMN_DISPLAY_SHOW_CORRES_NAMES, displayData.getCorres());
        contentValues.put(Config.COLUMN_DISPLAY_COLORIZE_CONTACTS, displayData.getcolorize_contacts());
        contentValues.put(Config.COLUMN_DISPLAY_SHOW_CONTACT_PICTURES, displayData.getshow_contact_pictures());

        contentValues.put(Config.COLUMN_DISPLAY_COLORIZE_CONTACT_PICTURES, displayData.getshow_contact_pictures());
        contentValues.put(Config.COLUMN_DISPLAY_CHANGE_COLOR_WHEN_READ, displayData.getchange_color_when_read());
        contentValues.put(Config.COLUMN_DISPLAY_THREADED_VIEW, displayData.getthreaded_view());
        contentValues.put(Config.COLUMN_DISPLAY_SHOW_SPLIT_SCREEN, displayData.getshow_split_screen());
        contentValues.put(Config.COLUMN_DISPLAY_FIXED_WIDTH_FONTS, displayData.getfixed_width_fonts());
        contentValues.put(Config.COLUMN_DISPLAY_VISIBLE_EMAIL_ACTIONS, displayData.getvisible_email_actions());
        contentValues.put(Config.COLUMN_DISPLAY_AUTO_FIT_EMAILS, displayData.getauto_fit_emails());
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_DISPLAY, null, contentValues);
        } catch (SQLiteException e){
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
    }

    public void updateDisplayData(String name, int val){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(name, val);

        try {
            sqLiteDatabase.update(Config.TABLE_DISPLAY, contentValues,"id = ?", new String[] {String.valueOf(1)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

    }
    public void updateLanguage(String val) {
        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("lang", val);

        try {
            sqLiteDatabase.update(Config.TABLE_DISPLAY, contentValues,"id = ?", new String[] {String.valueOf(1)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
    }

//    ---------interaction operation-------------
    public InteractionData getInteractionData() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_INTERACTION, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    InteractionData interactionData = null;
                    do {
                        int idIndex = cursor.getColumnIndex(Config.COLUMN_INTERACTION_ID);
                        int volIndex = cursor.getColumnIndex(Config.COLUMN_INTERACTION_VOLUME_KEY);
                        int returnIndex = cursor.getColumnIndex(Config.COLUMN_INTERACTION_RETURN_TO_LIST);
                        int showIndex = cursor.getColumnIndex(Config.COLUMN_INTERACTION_SHOW_NEXT_EMAIL);

                        int id, volume, return_list, show_next;
                        if(idIndex != -1
                                && volIndex != -1
                                && returnIndex != -1
                                && showIndex != -1) {
                            id = cursor.getInt(idIndex);
                            volume = cursor.getInt(volIndex);
                            return_list = cursor.getInt(returnIndex);
                            show_next = cursor.getInt(showIndex);
                            interactionData = new InteractionData(id, volume, return_list, show_next);

                        }
                    }   while (cursor.moveToNext());

                    return interactionData;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return null;
    }
    public void insertInteractionData(InteractionData interactionData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_INTERACTION_VOLUME_KEY, interactionData.getvolume());
        contentValues.put(Config.COLUMN_INTERACTION_RETURN_TO_LIST, interactionData.getreturn_list());
        contentValues.put(Config.COLUMN_INTERACTION_SHOW_NEXT_EMAIL, interactionData.getshow_next());
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_INTERACTION, null, contentValues);
        } catch (SQLiteException e){
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
    }
    public void updateInteractionData(String name, int val){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(name, val);

        try {
            sqLiteDatabase.update(Config.TABLE_INTERACTION, contentValues,"id = ?", new String[] {String.valueOf(1)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

    }

    public NotificationData getNotificationData() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_NOTIFICATION, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    NotificationData notificationData = null;
                    do {
                        int idIndex = cursor.getColumnIndex(Config.COLUMN_NOTIFICATION_ID);
                        int quietIndex = cursor.getColumnIndex(Config.COLUMN_NOTIFICATION_QUIET_TIME);
                        int disible_notificationIndex = cursor.getColumnIndex(Config.COLUMN_NOTIFICATION_DISIBLE_NOTIFICATIONS);

                        int id, quiet_time, desible_notifications;
                        if(idIndex != -1
                                && quietIndex != -1
                                && disible_notificationIndex != -1) {
                            id = cursor.getInt(idIndex);
                            quiet_time = cursor.getInt(quietIndex);
                            desible_notifications = cursor.getInt(disible_notificationIndex);
                            notificationData = new NotificationData(id, quiet_time, desible_notifications);

                        }
                    }   while (cursor.moveToNext());

                    return notificationData;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return null;
    }
    public void insertNotificationData(NotificationData notificationData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_NOTIFICATION_QUIET_TIME, notificationData.getquiet_time());
        contentValues.put(Config.COLUMN_NOTIFICATION_DISIBLE_NOTIFICATIONS, notificationData.getdesible_notifications());
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_NOTIFICATION, null, contentValues);
        } catch (SQLiteException e){
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
    }
    public void updateNotificationData(String name, int val){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(name, val);

        try {
            sqLiteDatabase.update(Config.TABLE_NOTIFICATION, contentValues,"id = ?", new String[] {String.valueOf(1)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

    }
}
