package com.umbrella.leancloud.controller;

import com.umbrella.leancloud.entities.CommonResult;
import com.umbrella.leancloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value="/consumer/feign/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long Id){
            return paymentFeignService.getPaymentById(Id);
    }

    @GetMapping(value="/consumer/feign/paymentTimeout/{id}")
    public CommonResult testFeignTimeOut(@PathVariable("id") Long Id){
        return paymentFeignService.testFeignTimeOut(Id);
    }

}
