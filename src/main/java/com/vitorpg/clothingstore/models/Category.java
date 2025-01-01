package com.vitorpg.clothingstore.models;

import com.vitorpg.clothingstore.models.enums.SizeType;

import java.util.List;

public class Category {
    private Long id;
    private String name;

    private SizeType sizeType;

    public Category() {

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public SizeType getSizeType() {
        return sizeType;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
