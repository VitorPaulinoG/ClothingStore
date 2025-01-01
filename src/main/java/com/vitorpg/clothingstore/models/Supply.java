package com.vitorpg.clothingstore.models;

import java.time.LocalDate;

public class Supply {
    private Long id;
    private Product product;
    private Supplier supplier;
    private String status;
    private LocalDate date;
    private Double price;

    public Supply () {

    }

    public Supply(Long id, Product product, Supplier supplier, String status, LocalDate date, Double price) {
        this.id = id;
        this.product = product;
        this.supplier = supplier;
        this.status = status;
        this.date = date;
        this.price = price;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
