package com.vitorpg.clothingstore.models.enums;

public enum ProductStatus {
    ACTIVE("Active"),
    REMOVED("Removed");

    private final String description;

    ProductStatus (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
