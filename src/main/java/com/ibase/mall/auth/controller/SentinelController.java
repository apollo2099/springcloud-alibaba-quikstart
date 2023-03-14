package com.ibase.mall.auth.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ibase.mall.auth.utils.ExceptionUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流接口测试
 * @author apollo
 */
@RestController
public class SentinelController {

    @SentinelResource(value = "test",
            blockHandler = "handleException",
            blockHandlerClass = {ExceptionUtil.class},
            fallback = "helloFallback",
            fallbackClass = {ExceptionUtil.class})
    @GetMapping("/sentinelRoute")
    public Boolean sentinelRoute(){
        System.out.println("sentinelRoute success");
        return true;
    }
}
