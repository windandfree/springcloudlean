package com.umbrella.leancloud.service.impl;

import com.umbrella.leancloud.service.PaymentFeignHytrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFeignHytrixServiceImpl implements PaymentFeignHytrixService {
    @Override
    public String paymentInfo_timeOut(Integer Id) {
        return "消费者：PaymentFeignHytrixService.paymentInfo_timeOut方法fallback";
    }

    @Override
    public String paymentInfo_ok(Integer Id) {
        return "消费者：PaymentFeignHytrixService.paymentInfo_ok方法fallback";
    }
}
