package com.ibase.mall.auth.utils;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {

    public static String handleException(String s, BlockException ex) {
        // Do some log here.
//        ex.printStackTrace();
        System.out.println("被限流，无法访问接口");
        return "被限流，无法访问接口";
    }

    /**
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     * fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     *
     * @param s
     * @return
     */
    public static String helloFallback(String s) {
        System.out.println("熔断功能被开启");
        return "熔断功能被开启";
    }
}
