package com.vitorpg.clothingstore.interfaces;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException (String message) {
        super(message);
    }
}
