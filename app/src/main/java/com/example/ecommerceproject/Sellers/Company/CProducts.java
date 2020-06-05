package com.example.ecommerceproject.Sellers.Company;

public class CProducts
{
    private String product_image;
    private String product_name;
    private String product_price;
    private String product_description;
    private String company_name;
    private String product_key;
    private String product_category;
    private String company_key;
    private String quantity_stock;

    CProducts() {}

    public CProducts(String product_image, String product_name, String product_price, String product_description, String company_name, String product_key, String product_category, String company_key, String quantity_stock) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_description = product_description;
        this.company_name = company_name;
        this.product_key = product_key;
        this.product_category = product_category;
        this.company_key = company_key;
        this.quantity_stock = quantity_stock;
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

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getCompany_key() {
        return company_key;
    }

    public void setCompany_key(String company_key) {
        this.company_key = company_key;
    }

    public String getQuantity_stock() {
        return quantity_stock;
    }

    public void setQuantity_stock(String quantity_stock) {
        this.quantity_stock = quantity_stock;
    }
}
