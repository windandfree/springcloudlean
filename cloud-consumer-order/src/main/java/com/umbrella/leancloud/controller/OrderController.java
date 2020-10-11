package com.umbrella.leancloud.controller;

import com.umbrella.leancloud.entities.CommonResult;
import com.umbrella.leancloud.entities.Payment;
import com.umbrella.leancloud.loadbalance.MyloadBalanceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class OrderController {

//    private static final String PAYMENT_URL="http://localhost:8001";CLOUD-PAYMENT-SERVICE
    private static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private MyloadBalanceInterface myloadBalanceInterface;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping(value="/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long Id){
            return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+Id,CommonResult.class);
    }

    @GetMapping(value="/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPaymentEntityById(@PathVariable("id") Long Id){
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+Id,CommonResult.class);
        log.info("responseEntity的响应体:",responseEntity.getBody());
        log.info("responseEntity的响应头:",responseEntity.getHeaders());
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }else
        return new CommonResult<>(444,"操作失败",new Payment());
    }

    @GetMapping(value="/consumer/payment/getForSelfLB/{id}")
    public CommonResult<Payment> getPaymentByIdSelfLB(@PathVariable("id") Long Id){

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        ServiceInstance serviceInstance = myloadBalanceInterface.myloadRoundRule(instances);
        return restTemplate.getForObject(serviceInstance.getUri()+"/payment/get/"+Id,CommonResult.class);
    }



}
