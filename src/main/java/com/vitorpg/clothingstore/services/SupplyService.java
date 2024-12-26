package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Supply;
import com.vitorpg.clothingstore.repositories.SupplyDao;

public class SupplyService extends GenericEntityService<Supply, SupplyDao> {

    public SupplyService() {
        super(new SupplyDao());
    }
}
