package com.umbrella.learncloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @JIRA:HY3-
 * @Des:
 * @Author:WL
 * @Date: 21:01 2020/10/12
 */
@RestController
@Slf4j
public class PaymentNacosController {
    @GetMapping("/paymentInfo")
    public String paymentInfo(){
        return "payment9002.paymentInfo method invoke success!";
    }
}
