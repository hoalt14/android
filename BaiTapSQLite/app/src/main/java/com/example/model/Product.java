package com.example.model;

public class Product {
    private int productId;
    private String productName;
    private String productManufacturer;
    //private Integer productPrice;

    //public Product(int productId, String productName, String productManufacturer, Integer productPrice) {
    public Product(int productId, String productName, String productManufacturer) {
        this.productId = productId;
        this.productName = productName;
        this.productManufacturer = productManufacturer;
        //this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

//    public Integer getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(Integer productPrice) {
//        this.productPrice = productPrice;
//    }
}
