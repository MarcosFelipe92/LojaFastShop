package com.fastshop.e_commerce.mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.itemOrder.ItemOrderDTO;
import com.fastshop.e_commerce.dtos.order.OrderDTO;
import com.fastshop.e_commerce.enuns.OrderStatus;
import com.fastshop.e_commerce.enuns.PaymentMethod;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.ItemOrderBO;
import com.fastshop.e_commerce.models.OrderBO;

public class OrderMapper {
    public static OrderBO dtoToEntity(OrderDTO dto, AccountBO account, AddressBO deliveryAddress) {
        OrderBO entity = new OrderBO();

        entity.setId(dto.getId());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setDiscount(dto.getDiscount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setAccount(account);
        entity.setDeliveryAddress(deliveryAddress);

        entity.setItems(dto.getItems().stream()
                .map(item -> ItemOrderMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    public static OrderDTO entityToDto(OrderBO entity) {
        Long id = entity.getId();
        Double totalPrice = entity.getTotalPrice();
        Double discount = entity.getDiscount();
        PaymentMethod paymentMethod = entity.getPaymentMethod();
        OrderStatus status = entity.getStatus();
        LocalDateTime createdAt = entity.getCreatedAt();
        LocalDateTime updatedAt = entity.getUpdatedAt();
        Long accountId = entity.getAccount().getId();
        Long deliveryAddressId = entity.getDeliveryAddress().getId();

        return new OrderDTO(id, totalPrice, discount, paymentMethod, status, createdAt, updatedAt, accountId,
                deliveryAddressId);
    }

    public static OrderDTO entityToDto(OrderBO entity, List<ItemOrderBO> items) {
        Long id = entity.getId();
        Double totalPrice = entity.getTotalPrice();
        Double discount = entity.getDiscount();
        PaymentMethod paymentMethod = entity.getPaymentMethod();
        OrderStatus status = entity.getStatus();
        LocalDateTime createdAt = entity.getCreatedAt();
        LocalDateTime updatedAt = entity.getUpdatedAt();
        Long accountId = entity.getAccount().getId();
        Long deliveryAddressId = entity.getDeliveryAddress().getId();

        List<ItemOrderDTO> itemsToAdd = items.stream()
                .map(ItemOrderMapper::entityToDto)
                .collect(Collectors.toList());

        return new OrderDTO(id, totalPrice, discount, paymentMethod, status, createdAt, updatedAt, accountId,
                deliveryAddressId, itemsToAdd);
    }
}
