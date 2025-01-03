package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.dtos.ProductFilter;
import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.repositories.ImageDao;
import com.vitorpg.clothingstore.repositories.ProductDao;

import java.util.List;

public class ProductService extends GenericEntityService<Product, ProductDao> {
    public ProductService() {
        super(new ProductDao(new ImageDao()));
    }

    public Long getTotalCount () {
        return dao.getTotalCount();
    }

    public Long getTotalCountFiltered (ProductFilter productFilter) {
        return dao.getTotalCountFiltered(productFilter);
    }

    public Product findFirst (Product product) {
        return dao.findFirst(product);
    }

    public List<Product> findPaginatedFiltered(Long maxCount, Long offset, ProductFilter productFilter) {
        return dao.findPaginatedFiltered(maxCount, offset, productFilter);
    }

    public List<Product> findPaginated (Long maxCount, Long offset) {
        return this.dao.findPaginated(maxCount, offset);
    }
    public void adjustAmount(Long id, Long amount) {
        super.dao.adjustAmount(id, amount);
    }

    public boolean remove(Long id) {
        return super.dao.remove(id);
    }
}
