package com.vitorpg.clothingstore.dtos;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.ProductStatus;

import java.util.*;

public class ProductFilter {
    private Optional<String> name;
    private Optional<Category> category;
    private Optional<Style> style;
    private Optional<Gender> gender;
    private Optional<Size> size;
    private Optional<Color> color;
    private Optional<Material> material;
    private Optional<ProductStatus> status;

    public ProductFilter () {
        this.name = Optional.empty();
        this.category = Optional.empty();
        this.style = Optional.empty();
        this.gender = Optional.empty();
        this.size = Optional.empty();
        this.color = Optional.empty();
        this.material = Optional.empty();
        this.status = Optional.empty();
    }

    public ProductFilter(Optional<String> name, Optional<Category> category, Optional<Style> style, Optional<Gender> gender, Optional<Size> size, Optional<Color> color, Optional<Material> material, Optional<ProductStatus> status) {
        this.name = name;
        this.category = category;
        this.style = style;
        this.gender = gender;
        this.size = size;
        this.color = color;
        this.material = material;
        this.status = status;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<Category> getCategory() {
        return category;
    }

    public void setCategory(Optional<Category> category) {
        this.category = category;
    }

    public Optional<Style> getStyle() {
        return style;
    }

    public void setStyle(Optional<Style> style) {
        this.style = style;
    }

    public Optional<Gender> getGender() {
        return gender;
    }

    public void setGender(Optional<Gender> gender) {
        this.gender = gender;
    }

    public Optional<Size> getSize() {
        return size;
    }

    public void setSize(Optional<Size> size) {
        this.size = size;
    }

    public Optional<Color> getColor() {
        return color;
    }

    public void setColor(Optional<Color> color) {
        this.color = color;
    }

    public Optional<Material> getMaterial() {
        return material;
    }

    public void setMaterial(Optional<Material> material) {
        this.material = material;
    }

    public Optional<ProductStatus> getStatus() {
        return status;
    }

    public void setStatus(Optional<ProductStatus> status) {
        this.status = status;
    }

}
