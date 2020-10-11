package com.umbrella.leancloud.controller;


import com.umbrella.leancloud.entities.CommonResult;
import com.umbrella.leancloud.entities.Payment;
import com.umbrella.leancloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;


    @GetMapping(value="/payment/hystrix/timeout/{id}")
    public String paymentInfo_timeOut(@PathVariable("id") Integer Id){
        return paymentService.paymentInfo_timeOut(Id);
    }

    @GetMapping(value="/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer Id){
        return paymentService.paymentInfo_ok(Id);
    }

    //服务熔断
    @GetMapping(value="/payment/hystrix/circuit/{id}")
    public String paymentInfoTestBreak(@PathVariable("id") Integer Id){
        return paymentService.paymentInfoTestBreak(Id);
    }
}
