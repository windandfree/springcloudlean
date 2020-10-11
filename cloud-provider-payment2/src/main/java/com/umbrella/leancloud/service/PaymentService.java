package com.umbrella.leancloud.service;

import com.umbrella.leancloud.entities.Payment;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
