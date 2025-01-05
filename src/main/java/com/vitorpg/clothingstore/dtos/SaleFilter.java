package com.vitorpg.clothingstore.dtos;

import com.vitorpg.clothingstore.models.Product;

import java.time.LocalDateTime;
import java.util.Optional;

public class SaleFilter {
    private Optional<String> productName;
    private Optional<String> amountComparator;
    private Optional<Long> amount;
    private Optional<LocalDateTime> afterDateTime;
    private Optional<LocalDateTime> beforeDateTime;

    public SaleFilter () {
        this.productName = Optional.empty();
        this.amount = Optional.empty();
        this.afterDateTime = Optional.empty();
        this.beforeDateTime = Optional.empty();
        this.amountComparator = Optional.empty();
    }

    public SaleFilter(Optional<String> productName, Optional<String> amountComparator, Optional<Long> amount, Optional<LocalDateTime> afterDateTime, Optional<LocalDateTime> beforeDateTime) {
        this.productName = productName;
        this.amountComparator = amountComparator;
        this.amount = amount;
        this.afterDateTime = afterDateTime;
        this.beforeDateTime = beforeDateTime;
    }

    public Optional<String> getProductName() {
        return productName;
    }

    public void setProductName(Optional<String> productName) {
        this.productName = productName;
    }

    public Optional<String> getAmountComparator() {
        return amountComparator;
    }

    public void setAmountComparator(Optional<String> amountComparator) {
        this.amountComparator = amountComparator;
    }

    public Optional<Long> getAmount() {
        return amount;
    }

    public void setAmount(Optional<Long> amount) {
        this.amount = amount;
    }

    public Optional<LocalDateTime> getAfterDateTime() {
        return afterDateTime;
    }

    public void setAfterDateTime(Optional<LocalDateTime> afterDateTime) {
        this.afterDateTime = afterDateTime;
    }

    public Optional<LocalDateTime> getBeforeDateTime() {
        return beforeDateTime;
    }

    public void setBeforeDateTime(Optional<LocalDateTime> beforeDateTime) {
        this.beforeDateTime = beforeDateTime;
    }
}
