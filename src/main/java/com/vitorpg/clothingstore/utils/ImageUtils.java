package com.vitorpg.clothingstore.utils;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class ImageUtils {
    public static Image bytesToJavaFXImage(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return new Image(new ByteArrayInputStream(data));
    }
}
