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
}
