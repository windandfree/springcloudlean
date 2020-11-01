package cloud.umbrella.leancloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @JIRA:HY3-
 * @Des:
 * @Author:WL
 * @Date: 20:48 2020/10/18
 */
@Slf4j
@RestController
public class SentinelController {
    @GetMapping("/getInfo1")
    public String getInfo1(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "access success  getInfo1 测试慢调用比例RT" ;
    }

    @GetMapping("/getInfo2")
    public String getInfo2() {
        int i = 10/0;
        return "access success  getInfo2 测试异常数、异常比例";
    }

    @GetMapping("/getInfo3")
    public String getInfo3(){
        return "access success  getInfo3";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKeyResourceName",//这个name就是页面控制台中的资源名
            blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                           @RequestParam(value = "p2",required = false) String p2){
        return "访问成功，参数p1："+p1+"参数p2："+p2;
    }

    public String deal_testHotKey(String p1, String p2, BlockException blockException){
        return "-------deal_testHotKey 进入兜底方法";//// 如果不配兜底方法 就会显示error page  不友好
    }
}
