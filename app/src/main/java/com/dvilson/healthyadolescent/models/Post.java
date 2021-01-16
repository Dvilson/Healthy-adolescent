package com.dvilson.healthyadolescent.models;

import java.util.Date;

public class Post {
    private int likes, comments;
    private String date, description,photo;
    private User user;

    public Post(int likes, int comments, String date, String description, String photo, User user, boolean selfLike) {
        this.likes = likes;
        this.comments = comments;
        this.date = date;
        this.description = description;
        this.photo = photo;
        this.user = user;
        this.selfLike = selfLike;
    }

    private boolean selfLike;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSelfLike() {
        return selfLike;
    }

    public void setSelfLike(boolean selfLike) {
        this.selfLike = selfLike;
    }

    public Post(){

    }
}
