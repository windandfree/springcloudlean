package cloud.umbrella.leancloud.controller;

import cloud.umbrella.leancloud.service.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MessageProviderController {
    @Resource
    private MessageProvider messageProvider;

    @GetMapping(value = "/sendMessage")
    public void sendMessageSelf(){
        messageProvider.sendMessagesSelf();
    }
}
