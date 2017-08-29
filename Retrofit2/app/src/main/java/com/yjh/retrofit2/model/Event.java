package com.yjh.retrofit2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Event implements Serializable {

    @SerializedName("event_id")
    private int eventId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("type")
    private int type;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("image1")
    private String image1;
    @SerializedName("video_url")
    private String videoUrl;
    @SerializedName("video_width")
    private int videoWidth;
    @SerializedName("video_height")
    private int videoHeight;
    @SerializedName("video_thumbnail")
    private String videoThumbnail;
    @SerializedName("like_count")
    private int likeCount;
    @SerializedName("comment_count")
    private int commentCount;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

}
