package com.vitorpg.clothingstore.models;

public class Image {
    private Long id;
    private byte[] data;
    private String format;
    private Long productId;

    public Image() {

    }

    public Image(Long id, byte[] data, String format, Long productId) {
        this.id = id;
        this.data = data;
        this.format = format;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
