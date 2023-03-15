package com.ibase.mall.auth.mq.consumer.transaction;

import com.alibaba.fastjson.JSON;
import com.ibase.mall.auth.entity.CartInfoEntity;
import com.ibase.mall.auth.service.CartInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
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
        System.out.println(String.format("half message\npayload:%s, arg:%s, transactionId:%s", order, args, message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID)));
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        CartInfoEntity cart = JSON.parseObject(new String((byte[]) message.getPayload()), CartInfoEntity.class);

        MessageHeaders headers = message.getHeaders();
        // 获取事务ID
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("事务消息ID:"+transactionId);

        CartInfoEntity cartInfoEntity = cartInfoService.getCartInfoByTraceId(transactionId);

        // 根据message去查询本地事务是否执行成功，如果成功，则commit
        if (!ObjectUtils.isEmpty(cartInfoEntity)) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }



//    private final OrderService orderService;
//    private final RocketMqTransactionLogMapper rocketMqTransactionLogMapper;
//    /**
//     * 执行本地事务
//     */
//    @Override
//    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
//        log.info("执行本地事务");
//        MessageHeaders headers = message.getHeaders();
//        //获取事务ID
//        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
//        Integer orderId = Integer.valueOf((String)headers.get("order_id"));
//        log.info("transactionId is {}, orderId is {}",transactionId,orderId);
//
//        try{
//            //执行本地事务，并记录日志
//            orderService.changeStatuswithRocketMqLog(orderId, CloudConstant.INVALID_STATUS,transactionId);
//            //执行成功，可以提交事务
//            return RocketMQLocalTransactionState.COMMIT;
//        }catch (Exception e){
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
//    }
//
//    /**
//     * 本地事务的检查，检查本地事务是否成功
//     */
//    @Override
//    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
//
//        MessageHeaders headers = message.getHeaders();
//        //获取事务ID
//        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
//        log.info("检查本地事务,事务ID:{}",transactionId);
//        //根据事务id从日志表检索
//        QueryWrapper<RocketmqTransactionLog> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("transaction_id",transactionId);
//        RocketmqTransactionLog rocketmqTransactionLog = rocketMqTransactionLogMapper.selectOne(queryWrapper);
//        if(null != rocketmqTransactionLog){
//            return RocketMQLocalTransactionState.COMMIT;
//        }
//        return RocketMQLocalTransactionState.ROLLBACK;
//    }
}

