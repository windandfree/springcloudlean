package com.umbrella.leancloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.umbrella.leancloud.entities.CommonResult;
import com.umbrella.leancloud.service.PaymentFeignHytrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class OderHystrixController {

    @Autowired
    private PaymentFeignHytrixService paymentFeignHytrixService;

    @GetMapping(value="/consumer/hytrix/payment/get/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        return paymentFeignHytrixService.paymentInfo_ok(id);
    }

    @GetMapping(value="/consumer/hytrix/paymentTimeout/{id}")
//    @HystrixCommand(fallbackMethod="paymentInfo_timeOut_handler",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="100000")
//    }) 在接口中做总的fallback处理
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        //int i = 10/0; 出错也会进入兜底方法
        log.info("paymentInfo_TimeOut："+new Date().toString());
        return paymentFeignHytrixService.paymentInfo_timeOut(id);
    }

    /**
     * 兜底的方案
     * @param integer
     * @return
     */
    public String paymentInfo_timeOut_handler(Integer integer){
        log.info("paymentInfo_timeOut_handler方法："+new Date().toString());
        return "服务消费者的兜底方案，调用超时or程序出现异常，请稍后再试";
    }

}
