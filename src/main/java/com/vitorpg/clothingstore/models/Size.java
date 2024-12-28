package com.vitorpg.clothingstore.models;

import com.vitorpg.clothingstore.models.enums.SizeType;

public class Size {
    private Long id;
    private String value;
    private SizeType sizeType;

    public Size() {

    }

    public Size(Long id, String value, SizeType sizeType) {
        this.id = id;
        this.value = value;
        this.sizeType = sizeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
