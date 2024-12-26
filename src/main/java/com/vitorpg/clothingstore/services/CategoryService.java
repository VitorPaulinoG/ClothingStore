package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Category;
import com.vitorpg.clothingstore.repositories.CategoryDao;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

public class CategoryService extends GenericEntityService<Category, CategoryDao> {
    public CategoryService() {
        super(new CategoryDao());
    }
}
