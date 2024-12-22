package com.vitorpg.clothingstore.models;

import java.time.LocalDate;

public class Supply {
    private Long id;
    private Product product;
    private Supplier supplier;
    private String status;
    private LocalDate date;
    private Double price;
    private Double deliveryPrice;
}
