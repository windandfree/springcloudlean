package com.umbrella.leancloud.loadbalance.impl;

import com.umbrella.leancloud.loadbalance.MyloadBalanceInterface;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自编写Ribbon轮询算法
 * windAndFree
 * 时间：2020-9-4 21:24:22
 */
@Component
public class MyloadBalanceImpl implements MyloadBalanceInterface {

    private AtomicInteger nextServe = new AtomicInteger(0);

    public final int getNextCallNum(){
        int current;
        int next;
        do{
            current = this.nextServe.get();
            next = current> Integer.MAX_VALUE ? 0 : current+1;
        }while (this.nextServe.compareAndSet(current,next)==false);
        System.out.println("第几次调用，次数next："+next);
        return next;
    }

    @Override
    public ServiceInstance myloadRoundRule(List<ServiceInstance> instances) {
        int index = getNextCallNum() % instances.size();
        return instances.get(index);
    }
}
