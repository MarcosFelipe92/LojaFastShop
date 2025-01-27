package com.fastshop.e_commerce.services.payment;

import com.fastshop.e_commerce.dtos.payment.PaymentDTO;

public interface PaymentService {
    PaymentDTO pay(PaymentDTO Payment);
}
