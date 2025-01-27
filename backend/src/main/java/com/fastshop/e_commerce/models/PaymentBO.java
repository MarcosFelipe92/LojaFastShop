package com.fastshop.e_commerce.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentBO {

    private List<Item> items;

    @JsonProperty("back_urls")
    private BackUrls backUrls;

    private OrderBO order;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class Item {
        private String title;
        private String description;
        private int quantity;
        @JsonProperty("unit_price")
        private double unitPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class BackUrls {
        private String success;
        private String pending;
        private String failure;
    }

}