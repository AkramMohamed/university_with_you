package com.example.universitywithyou;

public class Post {
    private int id_post ;
    private String title , text_post, picture , directedTo , time ;
    private int validPco , commentPco ;

    public Post() {
    }

    public Post(int id_post, String title, String text_post, String picture, String directedTo, String time, int validPco, int commentPco) {
        this.id_post = id_post;
        this.title = title;
        this.text_post = text_post;
        this.picture = picture;
        this.directedTo = directedTo;
        this.time = time;
        this.validPco = validPco;
        this.commentPco = commentPco;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText_post() {
        return text_post;
    }

    public void setText_post(String text) {
        this.text_post = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDirectedTo() {
        return directedTo;
    }

    public void setDirectedTo(String directedTo) {
        this.directedTo = directedTo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getValidPco() {
        return validPco;
    }

    public void setValidPco(int validPco) {
        this.validPco = validPco;
    }

    public int getCommentPco() {
        return commentPco;
    }

    public void setCommentPco(int commentPco) {
        this.commentPco = commentPco;
    }
}
