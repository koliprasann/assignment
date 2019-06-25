package com.hardcastle.assignment;

import java.util.ArrayList;

public class ProductOptionModel {
     String product_option_id;
      ArrayList<ProductOptionValueModel>product_option_value;

    public ProductOptionModel(String product_option_id, ArrayList<ProductOptionValueModel> product_option_value) {
        this.product_option_id = product_option_id;
        this.product_option_value = product_option_value;
    }

    public String getProduct_option_id() {
        return product_option_id;
    }

    public void setProduct_option_id(String product_option_id) {
        this.product_option_id = product_option_id;
    }

    public ArrayList<ProductOptionValueModel> getProduct_option_value() {
        return product_option_value;
    }

    public void setProduct_option_value(ArrayList<ProductOptionValueModel> product_option_value) {
        this.product_option_value = product_option_value;
    }
}
