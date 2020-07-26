package com.sel.smartfood.data.model;



public class OrderHistory{
    private String  productName;
    private int productTotalPrice;
    private int  productNumber;
    private String date;
    private String  productImage;

    public OrderHistory(){}

    public OrderHistory(String productName, int productTotalPrice, int productNumber,String date, String productImage) {
        this.productName = productName;
        this.productTotalPrice = productTotalPrice;
        this.productNumber = productNumber;
        this.date = date;
        this.productImage = productImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(int productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
