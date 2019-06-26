package com.hardcastle.assignment;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LinkedProductModel implements Serializable {
    String id;
    String title;
    String price2;
    String price;
    String minimum;
    String special2;
    String special;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getSpecial2() {
        return special2;
    }

    public void setSpecial2(String special2) {
        this.special2 = special2;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
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

    String image;
    String href;

    public LinkedProductModel(String id, String title, String price2, String price, String minimum, String special2, String special, String image, String href) {
        this.id = id;
        this.title = title;
        this.price2 = price2;
        this.price = price;
        this.minimum = minimum;
        this.special2 = special2;
        this.special = special;
        this.image = image;
        this.href = href;
    }
}