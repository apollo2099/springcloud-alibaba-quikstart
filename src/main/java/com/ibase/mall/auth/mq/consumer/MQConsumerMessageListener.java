package com.ibase.mall.auth.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.ibase.mall.auth.entity.CartInfoEntity;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "test-group", topic = "test-topic")
public class MQConsumerMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        CartInfoEntity cartInfoEntity = JSON.parseObject(message, CartInfoEntity.class);
        System.out.println("消费端测试"+JSON.toJSONString(cartInfoEntity));
    }
}
