package com.umbrella.leancloud.service.impl;

import com.umbrella.leancloud.entities.Payment;
import com.umbrella.leancloud.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    public int create(Payment payment) {
        return 1;
    }

    public Payment getPaymentById(Long id) {
        Payment p = new Payment();
        p.setId(1L);
        p.setSerial("123");
        return p;
    }
}
