package com.hardcastle.assignment;

public class SliderModel {
    int Id;
    String Title,Link,Image;

    public SliderModel(int id, String title, String link, String image) {
        Id = id;
        Title = title;
        Link = link;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
