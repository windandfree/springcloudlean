package com.umbrella.leancloud.service;

import com.umbrella.leancloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {

    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long Id);

    @GetMapping(value="/payment/timeout/{id}")
    public CommonResult testFeignTimeOut(@PathVariable("id") Long Id);

}
