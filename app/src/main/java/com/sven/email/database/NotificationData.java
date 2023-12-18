package com.sven.email.database;

public class NotificationData {
    private int id;
    private int quiet_time;
    private int desible_notifications;

    public NotificationData(int id, int quiet_time, int desible_notifications) {
        this.id = id;
        this.quiet_time = quiet_time;
        this.desible_notifications = desible_notifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getquiet_time() {
        return quiet_time;
    }

    public void setquiet_time(int animation) {
        this.quiet_time = quiet_time;
    }


    public int getdesible_notifications() {
        return desible_notifications;
    }

    public void setdesible_notifications(int desible_notifications) {
        this.desible_notifications = desible_notifications;
    }

}
