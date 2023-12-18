package com.sven.email.database;

public class DisplayData {
    private int id;
    private String lang;
    private int animation;
    private int show_corres_names;
    private int colorize_contacts;
    private int show_contact_pictures;
    private int colorize_contact_pictures;
    private int change_color_when_read;
    private int threaded_view;
    private int show_split_screen;
    private int fixed_width_fonts;
    private int visible_email_actions;
    private int auto_fit_emails;


    public DisplayData(int id, String lang, int animation, int show_corres_names, int colorize_contacts, int show_contact_pictures, int colorize_contact_pictures, int change_color_when_read, int threaded_view, int show_split_screen, int fixed_width_fonts, int visible_email_actions, int auto_fit_emails) {
        this.id = id;
        this.lang = lang;
        this.animation = animation;
        this.show_corres_names = show_corres_names;
        this.colorize_contacts = colorize_contacts;
        this.show_contact_pictures = show_contact_pictures;
        this.colorize_contact_pictures = colorize_contact_pictures;
        this.change_color_when_read = change_color_when_read;
        this.threaded_view = threaded_view;
        this.show_split_screen = show_split_screen;
        this.fixed_width_fonts = fixed_width_fonts;
        this.visible_email_actions = visible_email_actions;
        this.auto_fit_emails = auto_fit_emails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getCorres() {
        return show_corres_names;
    }

    public void setCorres(int show_corres_names) {
        this.show_corres_names = show_corres_names;
    }

    public int getcolorize_contacts() {
        return colorize_contacts;
    }

    public void setcolorize_contacts(int colorize_contacts) {
        this.colorize_contacts = colorize_contacts;
    }

    public int getshow_contact_pictures() {
        return show_contact_pictures;
    }

    public void setshow_contact_pictures(int show_contact_pictures) {
        this.show_contact_pictures = show_contact_pictures;
    }

    public int getcolorize_contact_pictures() {
        return colorize_contact_pictures;
    }

    public void setcolorize_contact_pictures(int colorize_contact_pictures) {
        this.colorize_contact_pictures = colorize_contact_pictures;
    }
    public int getchange_color_when_read() {
        return change_color_when_read;
    }

    public void setchange_color_when_read(int change_color_when_read) {
        this.change_color_when_read = change_color_when_read;
    }

    public int getthreaded_view() {
        return threaded_view;
    }

    public void setthreaded_view(int threaded_view) {
        this.threaded_view = threaded_view;
    }

    public int getshow_split_screen() {
        return show_split_screen;
    }

    public void setshow_split_screen(int show_split_screen) {
        this.show_split_screen = show_split_screen;
    }

    public int getfixed_width_fonts() {
        return fixed_width_fonts;
    }

    public void setfixed_width_fonts(int fixed_width_fonts) {
        this.fixed_width_fonts = fixed_width_fonts;
    }

    public int getvisible_email_actions() {
        return visible_email_actions;
    }

    public void setvisible_email_actions(int visible_email_actions) {
        this.visible_email_actions = visible_email_actions;
    }

    public int getauto_fit_emails() {
        return auto_fit_emails;
    }

    public void setauto_fit_emails(int auto_fit_emails) {
        this.auto_fit_emails = auto_fit_emails;
    }

}
