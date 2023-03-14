package com.ibase.mall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.ibase.mall.auth.entity.CartInfoEntity;
import com.ibase.mall.auth.service.CartInfoService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartInfoService cartInfoService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping(value = "get")
    public CartInfoEntity getCartInfoByUserId(Long userId) {
        // 查询购物车信息
        CartInfoEntity cartInfoEntity = cartInfoService.getCartInfoByUserId(userId);

        // 获取缓存数据测试
        String redisKey = "com.ibase.mall.cart-" + userId;
        if (redisTemplate.hasKey(redisKey)) {
            cartInfoEntity = (CartInfoEntity) redisTemplate.opsForValue().get(redisKey);
            System.out.println(JSON.toJSONString(cartInfoEntity));
        }
        redisTemplate.opsForValue().set(redisKey, cartInfoEntity, 86400, TimeUnit.SECONDS);

        // 发送消息测试
        rocketMQTemplate.convertAndSend("test-topic", JSON.toJSONString(cartInfoEntity));
        return cartInfoEntity;
    }
}
