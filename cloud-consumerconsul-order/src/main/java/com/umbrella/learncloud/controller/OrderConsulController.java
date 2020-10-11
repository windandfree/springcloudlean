package com.umbrella.learncloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderConsulController {

    private static final String PAYMENT_URL="http://CLOUD-PROVIDERCONSUL-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/consul")
    public String get(){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/consul",String.class);
    }

}
