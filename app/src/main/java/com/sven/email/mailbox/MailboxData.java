package com.sven.email.mailbox;

public class MailboxData {
    private final String id;
    private final String from;
    private final String to;
    private final String subject;
    private final String content;
    private final String body;
    private final String image;
    private final long time;

    public MailboxData(String id, String from, String to, String subject, String content, String image, long time, String body) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.image = image;
        this.time = time;
        this.body = body;
    }
    public String getId() {
        return id;
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
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

    public long getTime() {
        return time;
    }
    public String getBody() {
        return body;
    }
}
