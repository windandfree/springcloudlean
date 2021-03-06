package com.umbrella.leancloud.controller;


import com.umbrella.leancloud.entities.CommonResult;
import com.umbrella.leancloud.entities.Payment;
import com.umbrella.leancloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultType;
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

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        if(result > 0){
            log.info("****插入结果："+result);
            return new CommonResult(200,"插入成功,端口号：" + serverPort,result);
        }else return new CommonResult(500,"插入失败,端口号：" + serverPort,null);
    }

    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long Id){
        Payment payment = paymentService.getPaymentById(Id);
        if(payment != null){
            log.info("****查询结果："+payment);
            return new CommonResult(200,"查询成功,端口号："+serverPort,payment);
        }else return new CommonResult(500,"没有对应记录，查询ID:"+Id,null);
    }
    @GetMapping(value="/payment/loadBalance/{id}")
    public String getPaymentLbById(@PathVariable("id") Long Id){
       return serverPort;
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();//注册的service
        for (String service:services) {
           log.info("Application name:"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance serviceInstance:instances) {
            log.info(serviceInstance.getInstanceId()+"\t"+serviceInstance.getHost()+"\t"+serviceInstance.getPort()+"\t"+serviceInstance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value="/payment/timeout/{id}")
    public CommonResult testFeignTimeOut(@PathVariable("id") Long Id){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Payment payment = paymentService.getPaymentById(Id);
        if(payment != null){
            log.info("****查询结果："+payment);
            return new CommonResult(200,"查询成功,端口号："+serverPort,payment);
        }else return new CommonResult(500,"没有对应记录，查询ID:"+Id,null);
    }
}
