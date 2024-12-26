package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Supplier;
import com.vitorpg.clothingstore.repositories.SupplierDao;

public class SupplierService extends GenericEntityService<Supplier, SupplierDao> {

    public SupplierService() {
        super(new SupplierDao());
    }
}
