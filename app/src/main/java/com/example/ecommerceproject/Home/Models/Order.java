package com.example.ecommerceproject.Home.Models;

public class Order
{
    private String product_image;
    private String product_name;
    private String product_price;
    private String seller_name;
    private String product_description;
    private String quantity;
    private String state;

    Order() {}

    public Order(String product_image, String product_name, String product_price, String seller_name, String product_description, String quantity, String state) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.seller_name = seller_name;
        this.product_description = product_description;
        this.quantity = quantity;
        this.state = state;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
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

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
