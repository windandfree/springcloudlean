package com.umbrella.leancloud.loadbalance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyloadBalanceInterface {

    ServiceInstance myloadRoundRule(List<ServiceInstance> instances );
}
