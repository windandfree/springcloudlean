package com.umbrella.leancloud.service;

import com.umbrella.leancloud.service.impl.PaymentFeignHytrixServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE",fallback = PaymentFeignHytrixServiceImpl.class)
public interface PaymentFeignHytrixService {

    @GetMapping(value="/payment/hystrix/timeout/{id}")
    public String paymentInfo_timeOut(@PathVariable("id") Integer Id);

    @GetMapping(value="/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer Id);

}
