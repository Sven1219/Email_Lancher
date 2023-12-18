package com.sven.email.database;

public class InteractionData {
    private int id;
    private int volume;
    private int return_list;
    private int show_next;

    public InteractionData(int id, int volume, int return_list, int show_next) {
        this.id = id;
        this.volume = volume;
        this.return_list = return_list;
        this.show_next = show_next;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getvolume() {
        return volume;
    }

    public void setvolume(int animation) {
        this.volume = volume;
    }


    public int getreturn_list() {
        return return_list;
    }

    public void setreturn_list(int return_list) {
        this.return_list = return_list;
    }

    public int getshow_next() {
        return show_next;
    }

    public void setshow_next(int show_next) {
        this.show_next = show_next;
    }

}

