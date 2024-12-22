package com.vitorpg.clothingstore.models;

import com.vitorpg.clothingstore.models.enums.Gender;

import java.util.List;

public class Product {
    private Long id;
    private String name;
    private Category category;
    private Style style;
    private List<byte[]> images;
    private Gender gender;
    private Size size;
    private Color color;
    private Material material;
    private Long amount;
    private Double price;

    public Product() {

    }

    public Product(Long id, String name, Category category, Style style, List<byte[]> images,
                   Gender gender, Size size, Color color, Material material, Long amount, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.style = style;
        this.images = images;
        this.gender = gender;
        this.size = size;
        this.color = color;
        this.material = material;
        this.amount = amount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
