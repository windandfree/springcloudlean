package cloud.umbrella.leancloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.umbrella.leancloud.entities.CommonResult;

/**
 * @JIRA:HY3-
 * @Des:统一的自定义的方法处理类
 * @Author:WL
 * @Date: 20:53 2020/10/21
 */
public class MyBlockHandler {
    //BlockException参数必须定义，即使没有用到 也要定义，否则会出现调用不了这个兜底方法的情况
    public static CommonResult myHandler1(BlockException blockException) {
        return new CommonResult(444,"调用了 MyHandler.myHandler1",2);
    }
    public static CommonResult myHandler2(BlockException blockException) {
        return new CommonResult(444,"调用了 MyHandler.myHandler2",2);
    }

}
