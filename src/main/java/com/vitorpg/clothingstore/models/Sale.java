package com.vitorpg.clothingstore.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private Product product;
    private Long amount;
    private LocalDateTime dateTime;
    private Double totalPrice;
    private User vendor;

    public Sale() {

    }

    public Sale(Long id, Product product, Long amount, LocalDateTime dateTime, Double totalPrice, User vendor) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.dateTime = dateTime;
        this.totalPrice = totalPrice;
        this.vendor = vendor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getVendor() {
        return vendor;
    }

    public void setVendor(User vendor) {
        this.vendor = vendor;
    }
}
