package com.example.universitywithyou;

public class Post {
    private String id_post ;
    private String title , text_post, picture , directedTo , time ;
    private int viewsPco, commentPco ;
    private Boolean byDirector ;

    public Post() {
    }

    public Post(String id_post, String title, String text_post, String picture, String directedTo, String time, int viewsPco, int commentPco, Boolean byDirector) {
        this.id_post = id_post;
        this.title = title;
        this.text_post = text_post;
        this.picture = picture;
        this.directedTo = directedTo;
        this.time = time;
        this.viewsPco = viewsPco;
        this.commentPco = commentPco;
        this.byDirector = byDirector;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
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

    public int getViewsPco() {
        return viewsPco;
    }

    public void setViewsPco(int viewsPco) {
        this.viewsPco = viewsPco;
    }

    public int getCommentPco() {
        return commentPco;
    }

    public void setCommentPco(int commentPco) {
        this.commentPco = commentPco;
    }

    public Boolean getByDirector() {
        return byDirector;
    }

    public void setByDirector(Boolean byDirector) {
        this.byDirector = byDirector;
    }
}
