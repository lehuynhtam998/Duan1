package com.poly.sms.entity;

public class ProductWithImage {
    private Product product;
    private ProductImage productImage;

    // Constructor, getters, and setters
    public ProductWithImage(Product product, ProductImage productImage) {
        this.product = product;
        this.productImage = productImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }
}

