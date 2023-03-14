package com.ibase.mall.auth.controller;
import com.ibase.mall.auth.service.CartInfoService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProduceController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private CartInfoService cartInfoService;

    @GetMapping(value = "/send/{msg}")
    public void send(@PathVariable String msg){
        rocketMQTemplate.convertAndSend("test-topic",msg);
    }

    @GetMapping(value = "/sendTransaction/{userId}/{goodsId}")
    public void sendTransaction(@PathVariable Long userId, @PathVariable Long goodsId){
        cartInfoService.saveCartInfo(userId,goodsId);
    }
}
