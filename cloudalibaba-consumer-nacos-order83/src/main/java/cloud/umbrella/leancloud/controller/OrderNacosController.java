package cloud.umbrella.leancloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @JIRA:HY3-
 * @Des:
 * @Author:WL
 * @Date: 20:35 2020/10/13
 */
@RestController
@Slf4j
public class OrderNacosController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.url}")
    private String paymentUrl;

    @GetMapping("/consumer/getInfo")
    public String getPaymentInfo(){
        return restTemplate.getForObject(paymentUrl+"/paymentInfo",String.class);
    }
}
