package com.sven.email.mailbox;

public class MailboxData {
    private final String from;
    private final String subject;
    private final String content;
    private final String image;
    private final String time;

    public MailboxData(String from, String subject, String content, String image, String time) {
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.image = image;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }
}
