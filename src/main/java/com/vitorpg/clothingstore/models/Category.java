package com.vitorpg.clothingstore.models;

import java.util.List;

public class Category {
    private Long id;
    private String name;
    private List<Size> sizes;

    public Category() {

    }

    public Category(Long id, String name, List<Size> sizes) {
        this.id = id;
        this.name = name;
        this.sizes = sizes;
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

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }
}
