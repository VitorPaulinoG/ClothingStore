package com.vitorpg.clothingstore.models.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    UNISEX("Unisex");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
