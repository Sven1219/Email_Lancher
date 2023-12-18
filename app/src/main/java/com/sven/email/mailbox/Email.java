package com.sven.email.mailbox;

import android.graphics.Bitmap;

import java.util.Calendar;

public class Email {
    public String id;
    public String from = null;
    public String to = null;
    public String subject = null;
    public String mailcontent = null;
    public String body = null;
    public String image = null;
    public long time;

    public Email (String id, String from, String to, String subject, String mailcontent,String image, long time, String body) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.mailcontent = mailcontent;
        this.image = image;
        this.time = time;
        this.body = body;
    }
    public String getId() {
        return this.id;
    }
    public String getFrom() {
        return this.from;
    }
    public String getTo() {
        return this.to;
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

    public long getTime() {
        return this.time;
    }
    public String getBody() {
        return this.body;
    }
}
