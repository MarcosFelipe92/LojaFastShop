package com.fastshop.e_commerce.dtos.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fastshop.e_commerce.dtos.itemOrder.ItemOrderDTO;
import com.fastshop.e_commerce.enuns.OrderStatus;
import com.fastshop.e_commerce.enuns.PaymentMethod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Double totalPrice;
    private Double discount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long accountId;
    private Long deliveryAddressId;

    private List<ItemOrderDTO> items = new ArrayList<>();

    public OrderDTO(Long id, Double totalPrice, Double discount, PaymentMethod paymentMethod, OrderStatus status,
            LocalDateTime createdAt, LocalDateTime updatedAt, Long accountId, Long deliveryAddressId) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accountId = accountId;
        this.deliveryAddressId = deliveryAddressId;
    }

    public OrderDTO(Long id, Double totalPrice, Double discount, PaymentMethod paymentMethod, OrderStatus status,
            LocalDateTime createdAt, LocalDateTime updatedAt, Long accountId, Long deliveryAddressId,
            List<ItemOrderDTO> items) {
        this(id, totalPrice, discount, paymentMethod, status, createdAt, updatedAt, accountId, deliveryAddressId);
        this.items.addAll(items);
    }
}
