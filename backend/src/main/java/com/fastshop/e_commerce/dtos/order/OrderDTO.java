package com.fastshop.e_commerce.dtos.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fastshop.e_commerce.dtos.itemOrder.ItemOrderDTO;
import com.fastshop.e_commerce.dtos.payment.PaymentDTO;
import com.fastshop.e_commerce.enuns.OrderStatus;
import com.fastshop.e_commerce.enuns.PaymentMethod;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    @NotNull(message = "O preço total não pode ser nulo.")
    @Positive(message = "O preço total deve ser maior que zero.")
    private Double totalPrice;

    @NotNull(message = "O desconto não pode ser nulo.")
    @PositiveOrZero(message = "O desconto não pode ser negativo.")
    private Double discount;

    @NotNull(message = "O método de pagamento não pode ser nulo.")
    private PaymentMethod paymentMethod;

    @NotNull(message = "O status do pedido não pode ser nulo.")
    private OrderStatus status;

    @NotNull(message = "A data de criação não pode ser nula.")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "O ID da conta não pode ser nulo.")
    private Long accountId;

    @NotNull(message = "O ID do endereço de entrega não pode ser nulo.")
    private Long deliveryAddressId;

    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    @Valid
    private List<@Valid ItemOrderDTO> items = new ArrayList<>();

    @Setter
    private PaymentDTO payment;

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
