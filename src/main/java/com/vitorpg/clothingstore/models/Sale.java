package com.vitorpg.clothingstore.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private Product product;
    private Long amount;
    private LocalDateTime dateTime;
    private Double totalPrice;
    private User vendor;
}
