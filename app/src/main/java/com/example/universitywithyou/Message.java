package com.example.universitywithyou;

import java.util.Calendar;

public class Message {

    private int id_message ;
    private String message_text , picture , sender ;
    private String time  ;
    private Boolean seen ;

    public Message() {
    }

    public Message(int id_message, String message_text, String picture, String sender, String time,Boolean seen) {
        this.id_message = id_message;
        this.message_text = message_text;
        this.picture = picture;
        this.sender = sender;
        this.time = time;
        this.seen = seen;
    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
