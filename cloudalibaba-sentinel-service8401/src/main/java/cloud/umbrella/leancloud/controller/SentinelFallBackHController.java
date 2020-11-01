package cloud.umbrella.leancloud.controller;

import cloud.umbrella.leancloud.myhandler.MyBlockHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.umbrella.leancloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @JIRA:HY3-
 * @Des:统一的自定义的方法处理类测试的controller
 * @Author:WL
 * @Date: 20:48 2020/10/18
 */
@Slf4j
@RestController
public class SentinelFallBackHController {
    @GetMapping("/testMyHandler1")
    @SentinelResource(value = "testMyHandler1",
            blockHandlerClass = MyBlockHandler.class,blockHandler = "myHandler1")
    public CommonResult testMyHandler1(){
        return new CommonResult(200,"access success testMyHander1 统一的自定义的方法处理类 controller",1);
    }

    @GetMapping("/testMyHandler2")
    @SentinelResource(value = "testMyHandler2",blockHandlerClass = MyBlockHandler.class,blockHandler = "myHandler2")
    public CommonResult testMyHandler2() {
        return  new CommonResult(200,"access success testMyHander2 统一的自定义的方法处理类 controller",2);
    }

    @GetMapping("/testMyHandler3/{id}")
    //@SentinelResource(value = "testMyHandler3")//如果不配置fallback，运行异常直接给前台 不友好
    //@SentinelResource(value = "testMyHandler3",fallback = "fallbackMyHandler")//如果不配置blockHandler，限流后前台error page 不友好
    @SentinelResource(value = "testMyHandler3",fallback = "fallbackMyHandler",blockHandler = "blockMyHandler")//fallback管运行时异常，blockHandler管流控违规
    //@SentinelResource(value = "testMyHandler3",
    // fallback = "fallbackMyHandler",
    // blockHandler = "blockMyHandler",
    // exceptionsToIgnore = RuntimeException.class)//排除不需要进入兜底方法的运行时异常
    public String testMyHandler3(@PathVariable int id) {
        if(1==id){
            throw new NullPointerException("空指针异常，id不能为1");
        }
        if(2==id){
            throw new RuntimeException("运行时异常，id不能为2");
        }
        return "成功返回！";
    }

    public String fallbackMyHandler(int id,BlockException e) {
        return "兜底方法返回！";
    }

    public String blockMyHandler(int id,BlockException e) {
        return "限流自定义方法返回！";
    }
}
