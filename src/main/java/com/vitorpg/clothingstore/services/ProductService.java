package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.repositories.ImageDao;
import com.vitorpg.clothingstore.repositories.ProductDao;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

public class ProductService extends GenericEntityService<Product, ProductDao> {
    public ProductService() {
        super(new ProductDao(new ImageDao()));
    }
    public void findPaginated (Long maxCount, Long offset) {
        this.dao.findPaginated(maxCount, offset);
    }
    public void adjustAmount(Long id, Long amount) {
        super.dao.adjustAmount(id, amount);
    }
}