package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Sale;
import com.vitorpg.clothingstore.repositories.SaleDao;

public class SaleService extends GenericEntityService<Sale, SaleDao> {
    public SaleService() {
        super(new SaleDao());
    }
}
