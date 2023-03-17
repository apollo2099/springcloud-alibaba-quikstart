package com.ibase.mall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ibase.mall.auth.entity.CartInfoEntity;
import com.ibase.mall.auth.service.CartInfoService;
import com.ibase.mall.auth.service.dubbo.CartInfoDubbo;
import com.ibase.mall.auth.service.dubbo.CartInfoDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Api(value = "购物车服务接口", description = "购物车服务接口")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartInfoService cartInfoService;

    @DubboReference
    private CartInfoDubbo cartInfoDubbo;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @ApiOperation(value = "获取购物车信息", notes = "获取购物车信息")
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

    @ApiOperation(value = "获取购物车信息(dubbo方式)", notes = "获取购物车信息(dubbo方式)")
    @GetMapping(value = "get-by-dubbo")
    public CartInfoEntity getCartInfoDubboByUserId(Long userId) {
        // 查询购物车信息
        CartInfoEntity cartInfoEntity = cartInfoDubbo.getCartInfoByUserId(userId);

        // 获取缓存数据测试
        String redisKey = "com.ibase.mall.cart-" + userId;
        if (redisTemplate.hasKey(redisKey)) {
            cartInfoEntity = (CartInfoEntity) redisTemplate.opsForValue().get(redisKey);
            System.out.println(JSON.toJSONString(cartInfoEntity));
        }
        redisTemplate.opsForValue().set(redisKey, cartInfoEntity, 86400, TimeUnit.SECONDS);
        return cartInfoEntity;
    }


    @ApiOperation(value = "购物车信息-分页查询", notes = "购物车信息-分页查询")
    @PostMapping(value = "list")
    public IPage<CartInfoEntity> pageList(Long userId) {
        IPage<CartInfoEntity> iPage = cartInfoService.pageList(userId);
        return iPage;
    }
}
