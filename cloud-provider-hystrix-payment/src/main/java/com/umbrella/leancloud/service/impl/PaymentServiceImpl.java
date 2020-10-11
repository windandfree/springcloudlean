package com.umbrella.leancloud.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import com.umbrella.leancloud.entities.Payment;
import com.umbrella.leancloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_ok(Integer integer){
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_ok  参数："+integer;
    }
    @Override
    @HystrixCommand(fallbackMethod="paymentInfo_timeOut_handler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="100000")
    })
    public String paymentInfo_timeOut(Integer integer){
        log.info("time_out方法开始："+new Date().toString());
        Integer timeInt = 500;
        try {
            TimeUnit.MILLISECONDS.sleep(timeInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("time_out方法结束："+new Date().toString());
        return "服务提供方，paymentInfo_TimeOut  参数："+integer+"耗时（S）："+timeInt;
    }

    /**
     * 兜底的方案
     * @param integer
     * @return
     */
    public String paymentInfo_timeOut_handler(Integer integer){
        return "服务提供方的兜底方案，线程池："+Thread.currentThread().getName()+"paymentInfo_TimeOut_handler  程序出现异常，请稍后再试";
    }

    /*============================服务熔断==========================*/

    @HystrixCommand(fallbackMethod = "paymentInfoTestExceptionHanler",commandProperties={
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否启用断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "5"),//请求次数峰值 默认20次
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "15000"),//时间窗口期 默认10秒
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "40"),//失败率达到百分之多少时跳闸 默认50%
    })
    //上面配置的意思是 在10次请求中有6次都进行了服务降级则，进行服务熔断,时间窗口期20秒钟后进入半开闭状态，检查是否能恢复恢复链路
    @Override
    public String paymentInfoTestBreak(Integer id){
        if(id < 0){
            throw new RuntimeException("参数不能为负数");
        }
        return "服务提供者：PaymentService.paymentInfoTestBreak正常返回，参数："+id;
    }

    public String paymentInfoTestExceptionHanler(Integer id){
        return "服务提供者，反生服务熔断，进入服务降级方法：PaymentService.paymentInfoTestExceptionHanler,参数："+id;
    }
    /*//所有Hystrix配置
    @HystrixCommand(fallbackMethod = "兜底方法名",commandProperties = {
            //表示隔离策略，THREAD线程池隔离，SEMAPHORE信号池隔离
            @HystrixProperty(name="execution.isolation.strategy",value = "THREAD"),
            //当选择信号池隔离时，用来设置信号池的大小（最大并发数）
            @HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests",value = "10"),
            //配置命令执行的超时时间
            @HystrixProperty(name="execution.isolation.thread.timeoutinMilliseconds",value = "10"),
            //是否启用超时时间
            @HystrixProperty(name="execution.timeout.enabled",value = "true"),
            //执行超时的时候是否中断
            @HystrixProperty(name="execution.isolation.thread.interruptOnTimeout",value = "true"),
            //执行被取消的时候是否中断
            @HystrixProperty(name="execution.isolation.thread.interruptOnCancel",value = "true"),
            //允许回调方法执行的最大并发数
            @HystrixProperty(name="fal1back.isolation.semaphore.maxConcurrentRequests",value = "10"),
            //服务降级是否启用
            @HystrixProperty(name="fallback.enabled",value = "true"),
            //服务熔断是否启用
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
            *//*该属性用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候,，
            如果滚动时间窗（默认10秒）内仅收到了19个请求，即使达19个请求都失败了，断路器也不会打开。*//*
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "20"),
            *//* 该属性用来设置错误百分比，表示在滚动时间窗中，在请求数量超过
            circuitBreaker.requestVolumeThreshold 的情况下，如果错误请求数的百分比超过50,
            就把断路器设置为“打开”状态，否则就设置为“关闭”状态。*//*
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
            *//*该属性用来设置当断路器打开之后的休眠时间窗。体眠时间窗结束之后，
            会将断路器置为“半开”状态，尝试熔断的请求命令，如果依然失败就将断路器继续设置为“打开”状态，
            并进入下一个休眠周期，如果成功就设置为“关闭”状态。*//*
            @HystrixProperty(name="circuitBreaker.sleepwindowinMilliseconds",value = "10"),
            //断路器强制打开
            @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
            //断路器强制关闭
            @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false" ),
            //滚动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
            @HystrixProperty(name ="metrics.rollingStats.timeinMilliseconds",value = "10000"),
            *//*该属性用来设置滚动时间窗统计指标信息时划分"桶"的数量，断路器在收集指标信息的时候会根据
            设置的时间窗长度拆分成多个“桶”来累计各度量值，每个"桶"记录了一段时间内的采集指标。
            比如10秒内拆分成10个"转"收集这样，所以timeinWilliseconds必须能被_numBuckets整除。否则会抛异常*//*
            @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10"),
            //该属性用来设置对命令执行的延迟是否使用百分位数来跟踪和计算。如果没置为false，那么所有的概要统计都将返回-1
            @HystrixProperty(name = "metrics.rollingPercentile.enabled",value = "false"),
            //该属性用来设置百分位统计的滚动窗口的持续时间，单位为毫秒。
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds",value = "60000"),
            //该属性用来设置百分位统计滚动窗口期中使用“捅”的数量。
            @HystrixProperty(name = "metrics.rollingPercentie.numBuckets", value = "60000"),
            *//*该属性用来设置在执行过程中每个“桶”中保留的最大执行次数。如果在滚动时间窗内发生超过该设定值的执行次数,
            就从最初的位置开始重写。例如，将该值设置为100，滚动窗口为10秒，若在10秒内一个“桶”中发生了500次执行，
            那么该“桶”中只保留最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗，并增加排序百分位数所需的计算时间*//*
            @HystrixProperty(name = "metrics.rollingPercentile.bucketSize",value = "100"),
            //该属性用来设置采集影响断路器状态的健康快照（请求的成功、错误百分比）的间隔等待时间。
            @HystrixProperty(name = "metrics.healthSnapshot.intervalinMilliseconds",value = "500"),
            //是否开启请求缓存
            @HystrixProperty(name = "requestCache.enabled",value = "true"),
            // HystrixCommand的执行和事件是否打印日志到HystrixRequestLog中
            @HystrixProperty(name = "requestLog.enabled", value = "true"),
            //该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
            @HystrixProperty(name = "coreSize", value = "10"),
            *//*该参数用来设置线程池的最大队列大小。当设置为-1时，线程池将使用SynchronousQueue
            否则将使用LinkedBLockingQueue实现的队列。*//*
            @HystrixProperty(name = "maxQueueSize", value = "-1"),
            *//*该参数用来为队列设置拒绝阚值。通过该参数，即使队列没有达到最大值也能拒绝请求。
            该参数主要是对LinkedBLockingQueue队列的补充,因为LinkedBLockingQueue
            队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。*//*
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5")
    })*/

}
