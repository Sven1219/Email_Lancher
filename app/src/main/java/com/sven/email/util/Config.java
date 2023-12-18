package com.sven.email.util;

public class Config {
    public static final String DATABASE_NAME = "email_launcher.db";

    //table_names
    public static final String TABLE_DISPLAY = "display";
    public static final String TABLE_INTERACTION = "interaction";
    public static final String TABLE_NOTIFICATION = "notification";

//    ---------------display_field----------
    public static final String COLUMN_DISPLAY_ID = "id";
    public static final String COLUMN_DISPLAY_LANG = "lang";
    public static final String COLUMN_DISPLAY_ANIMATION = "animation";
    public static final String COLUMN_DISPLAY_SHOW_CORRES_NAMES = "show_corres_names";
    public static final String COLUMN_DISPLAY_COLORIZE_CONTACTS = "colorize_contacts";
    public static final String COLUMN_DISPLAY_SHOW_CONTACT_PICTURES = "show_contact_pictures";
    public static final String COLUMN_DISPLAY_COLORIZE_CONTACT_PICTURES = "colorize_contact_picutes";
    public static final String COLUMN_DISPLAY_CHANGE_COLOR_WHEN_READ = "change_color_when_read";
    public static final String COLUMN_DISPLAY_THREADED_VIEW = "threaded_view";
    public static final String COLUMN_DISPLAY_SHOW_SPLIT_SCREEN = "show_split_screen";
    public static final String COLUMN_DISPLAY_FIXED_WIDTH_FONTS = "fixed_width_fonts";
    public static final String COLUMN_DISPLAY_VISIBLE_EMAIL_ACTIONS = "visible_email_actions";
    public static final String COLUMN_DISPLAY_AUTO_FIT_EMAILS = "auto_fit_emails";


    //------------------interaction_table_field-------------
    public static final String COLUMN_INTERACTION_ID = "id";
    public static final String COLUMN_INTERACTION_VOLUME_KEY = "volume_key";
    public static final String COLUMN_INTERACTION_RETURN_TO_LIST = "return_to_list";
    public static final String COLUMN_INTERACTION_SHOW_NEXT_EMAIL = "show_next_email";

    //-----------------notification_table_field--------
    public static final String COLUMN_NOTIFICATION_ID = "id";
    public static final String COLUMN_NOTIFICATION_QUIET_TIME = "quiet_time";
    public static final String COLUMN_NOTIFICATION_DISIBLE_NOTIFICATIONS = "disible_notifications";

}
