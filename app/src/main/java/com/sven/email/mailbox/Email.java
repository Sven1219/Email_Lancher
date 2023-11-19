package com.sven.email.mailbox;

import android.graphics.Bitmap;

import java.util.Calendar;

public class Email {
    public String from = null;
    public String subject = null;
    public String mailcontent = null;
    public String image = null;
    public String time = null;

    public Email (String from, String subject, String mailcontent,String image, String time) {
        this.from = from;
        this.subject = subject;
        this.mailcontent = mailcontent;
        this.image = image;
        this.time = time;
    }
    public String getFrom() {
        return this.from;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.mailcontent;
    }
    public String getImage() {
        return this.image;
    }

    public String getTime() {
        return this.time;
    }
}
