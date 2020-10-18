package cloud.umbrella.leancloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @JIRA:HY3-
 * @Des:
 * @Author:WL
 * @Date: 19:46 2020/10/14
 */
@RestController
@Slf4j
@RefreshScope
public class ConfigNacosController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/getInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
