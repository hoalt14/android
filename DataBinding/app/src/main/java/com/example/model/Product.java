package com.example.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class Product extends BaseObservable {
    private int productCode;
    private String productName;

    public Product() {
    }

    public Product(int productCode, String productName) {
        this.productCode = productCode;
        this.productName = productName;
    }

    @Bindable
    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
        notifyPropertyChanged(BR.productCode);
    }

    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyPropertyChanged(BR.productName);
    }
}
