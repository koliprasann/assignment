package com.hardcastle.assignment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProductModel implements Parcelable {

    String id,
            product_id,
            image,
            title,
            description,
            price,
            price2,
            minimum,
            rating,
            href,
            //linked_products,
            combo_products;
    //ArrayList<ProductOptionModel> product_options;
    ArrayList<LinkedProductModel> linked_products;
  Boolean  special,
    special2,
    tax;

    public ProductModel(String id, String product_id, String image, String title, String description, String price, String price2, Boolean special, Boolean special2, Boolean tax, String minimum, String rating, String href, ArrayList<LinkedProductModel> linked_products, String combo_products/*, ArrayList<ProductOptionModel> product_options*/) {
        this.id = id;
        this.product_id = product_id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.price2 = price2;
        this.special = special;
        this.special2 = special2;
        this.tax = tax;
        this.minimum = minimum;
        this.rating = rating;
        this.href = href;
        this.linked_products = linked_products;
        this.combo_products = combo_products;
       // this.product_options = product_options;
    }

    protected ProductModel(Parcel in) {
        id = in.readString();
        product_id = in.readString();
        image = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readString();
        price2 = in.readString();
        minimum = in.readString();
        rating = in.readString();
        href = in.readString();
        combo_products = in.readString();
        byte tmpSpecial = in.readByte();
        special = tmpSpecial == 0 ? null : tmpSpecial == 1;
        byte tmpSpecial2 = in.readByte();
        special2 = tmpSpecial2 == 0 ? null : tmpSpecial2 == 1;
        byte tmpTax = in.readByte();
        tax = tmpTax == 0 ? null : tmpTax == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(product_id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(price2);
        dest.writeString(minimum);
        dest.writeString(rating);
        dest.writeString(href);
        dest.writeString(combo_products);
        dest.writeByte((byte) (special == null ? 0 : special ? 1 : 2));
        dest.writeByte((byte) (special2 == null ? 0 : special2 ? 1 : 2));
        dest.writeByte((byte) (tax == null ? 0 : tax ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getSpecial2() {
        return special2;
    }

    public void setSpecial2(Boolean special2) {
        this.special2 = special2;
    }

    public Boolean getTax() {
        return tax;
    }

    public void setTax(Boolean tax) {
        this.tax = tax;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ArrayList<LinkedProductModel> getLinked_products() {
        return linked_products;
    }

    public void setLinked_products(ArrayList<LinkedProductModel> linked_products) {
        this.linked_products = linked_products;
    }

    public String getCombo_products() {
        return combo_products;
    }

    public void setCombo_products(String combo_products) {
        this.combo_products = combo_products;
    }

  /*  public ArrayList<ProductOptionModel> getProduct_options() {
        return product_options;
    }

    public void setProduct_options(ArrayList<ProductOptionModel> product_options) {
        this.product_options = product_options;
    }*/
}
