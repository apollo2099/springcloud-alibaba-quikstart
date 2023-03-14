package com.ibase.mall.auth.mq.consumer.transaction;

import com.alibaba.fastjson.JSON;
import com.ibase.mall.auth.entity.CartInfoEntity;
import com.ibase.mall.auth.service.CartInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.util.ObjectUtils;
import javax.annotation.Resource;

/**
 * @author apollo
 */
@RocketMQTransactionListener
public class OrderTransactionListener implements RocketMQLocalTransactionListener {

    @Resource
    private CartInfoService cartInfoService;


    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        CartInfoEntity order = JSON.parseObject(new String((byte[]) message.getPayload()), CartInfoEntity.class);
        Long args = (Long) o;
        System.out.println(String.format("half message\npayload:%s, arg:%s, transactionId:%s", order, args,
                message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID)));
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        CartInfoEntity cart = JSON.parseObject(new String((byte[]) message.getPayload()), CartInfoEntity.class);
        CartInfoEntity cartInfoEntity = cartInfoService.getCartInfoByUserId(cart.getUserId());

        // 根据message去查询本地事务是否执行成功，如果成功，则commit
        if (!ObjectUtils.isEmpty(cartInfoEntity)) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
