package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.dtos.SaleFilter;
import com.vitorpg.clothingstore.models.Sale;
import com.vitorpg.clothingstore.repositories.SaleDao;

import java.util.List;

public class SaleService extends GenericEntityService<Sale, SaleDao> {
    public SaleService() {
        super(new SaleDao());
    }

    public List<Sale> findPaginated(Long maxCount, Long offset) {
        return this.dao.findPaginated(maxCount, offset);
    }

    public List<Sale> findPaginatedFiltered(Long maxCount, Long offset, SaleFilter saleFilter) {
        return this.dao.findPaginatedFiltered(maxCount, offset, saleFilter);
    }

    public Double getTotalRevenue () {
        return this.dao.getTotalRevenue();
    }

    public Long getSoldItemsCount () {
        return this.dao.getSoldItemsCount();
    }

    public Double getTotalProfit () {
        return this.dao.getTotalProfit();
    }

    public Long getTotalCountFiltered (SaleFilter saleFilter) {
        return this.dao.getTotalCountFiltered(saleFilter);
    }

    public Long getTotalCount() {
        return this.getTotalCount();
    }

}
