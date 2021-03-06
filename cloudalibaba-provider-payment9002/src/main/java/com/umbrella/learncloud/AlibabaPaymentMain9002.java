package com.umbrella.learncloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @JIRA:HY3-
 * @Des:
 * @Author:WL
 * @Date: 20:24 2020/10/12
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaPaymentMain9002 {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaPaymentMain9002.class,args);
    }
}
