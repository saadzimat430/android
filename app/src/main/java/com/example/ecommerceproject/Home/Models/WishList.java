package com.example.ecommerceproject.Home.Models;

public class WishList
{
    private String product_name;
    private String product_price;
    private String product_image;
    private String seller_name;
    private String cart_key;
    private String product_key;
    private String product_category;
    private String product_description;

    WishList(){}

    public WishList(String product_name, String product_price, String product_image, String seller_name, String cart_key, String product_key, String product_category, String product_description) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.seller_name = seller_name;
        this.cart_key = cart_key;
        this.product_key = product_key;
        this.product_category = product_category;
        this.product_description = product_description;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getCart_key() {
        return cart_key;
    }

    public void setCart_key(String cart_key) {
        this.cart_key = cart_key;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }
}
