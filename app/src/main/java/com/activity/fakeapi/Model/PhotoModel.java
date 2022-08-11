package com.activity.fakeapi.Model;

public class PhotoModel {

    private String id;
    private String thumbnailUrl;

    public PhotoModel() {
    }

    public PhotoModel(String title, String thumbnailUrl) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.id = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setUrl(String url) {
        this.thumbnailUrl = url;
    }
}
