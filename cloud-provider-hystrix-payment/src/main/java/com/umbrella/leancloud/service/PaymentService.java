package com.umbrella.leancloud.service;

import com.umbrella.leancloud.entities.Payment;

public interface PaymentService {
    public String paymentInfo_ok(Integer integer);

    public String paymentInfo_timeOut(Integer integer);

    public String paymentInfoTestBreak(Integer id);

}
