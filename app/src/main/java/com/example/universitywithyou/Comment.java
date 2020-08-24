package com.example.universitywithyou;

import java.io.Serializable;

public class Comment implements  Serializable {
    private String id_comment, id_user;
    private int id_post;
    private String comment_text, time, commentator;
    private int agreed_co;

    public Comment() {
    }

    public Comment(String id_comment, String id_user, int id_post, String comment_text, String time, String commentator, int agreed_co) {
        this.id_comment = id_comment;
        this.id_user = id_user;
        this.id_post = id_post;
        this.comment_text = comment_text;
        this.time = time;
        this.commentator = commentator;
        this.agreed_co = agreed_co;
    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public int getAgreed_co() {
        return agreed_co;
    }

    public void setAgreed_co(int agreed_co) {
        this.agreed_co = agreed_co;
    }
}
