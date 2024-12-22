package com.vitorpg.clothingstore.models.enums;

public enum SizeType {
    NUMBER("Number"),
    LETTER("Letter");

    private final String description;

    SizeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
