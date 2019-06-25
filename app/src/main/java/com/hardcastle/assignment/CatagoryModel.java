package com.hardcastle.assignment;

public class CatagoryModel {
    String title,id,image,href;

    public CatagoryModel(String title, String id, String image, String href) {
        this.title = title;
        this.id = id;
        this.image = image;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
