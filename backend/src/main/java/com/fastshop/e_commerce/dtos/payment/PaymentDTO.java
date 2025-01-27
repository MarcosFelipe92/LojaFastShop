package com.fastshop.e_commerce.dtos.payment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentDTO {

    private List<ItemDTO> items;

    @JsonProperty("back_urls")
    private BackUrlsDTO backUrls;

    private String initPoint;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ItemDTO {
        private String title;
        private String description;
        private Integer quantity;
        @JsonProperty("unit_price")
        private Double unitPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class BackUrlsDTO {
        private String success;
        private String pending;
        private String failure;
    }
}
