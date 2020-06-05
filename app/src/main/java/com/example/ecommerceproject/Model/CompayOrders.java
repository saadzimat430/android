package com.example.ecommerceproject.Model;

public class CompayOrders
{
    private String name, user_id, address,product_image,product_description, state, date, time, totalAmount, product_name, product_price,seller_name, total_price, total_product_count, quantity, phone,product_category, product_key;

    CompayOrders(){
    }

    public CompayOrders(String name, String user_id, String address, String product_image, String product_description, String state, String date, String time, String totalAmount, String product_name, String product_price, String seller_name, String total_price, String total_product_count, String quantity, String phone, String product_category, String product_key) {
        this.name = name;
        this.user_id = user_id;
        this.address = address;
        this.product_image = product_image;
        this.product_description = product_description;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.product_name = product_name;
        this.product_price = product_price;
        this.seller_name = seller_name;
        this.total_price = total_price;
        this.total_product_count = total_product_count;
        this.quantity = quantity;
        this.phone = phone;
        this.product_category = product_category;
        this.product_key = product_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_product_count() {
        return total_product_count;
    }

    public void setTotal_product_count(String total_product_count) {
        this.total_product_count = total_product_count;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }
}
