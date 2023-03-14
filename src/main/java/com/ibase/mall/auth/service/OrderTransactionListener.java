//package com.ibase.mall.auth.service;
//
//import com.alibaba.fastjson.JSON;
//import com.ibase.alibaba.provider.dao.OrderDao;
//import com.ibase.alibaba.provider.entity.Order;
//import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
//import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
//import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
//import org.apache.rocketmq.spring.support.RocketMQHeaders;
//import org.springframework.messaging.Message;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author apollo
// */
//@RocketMQTransactionListener(txProducerGroup = "erp")
//public class OrderTransactionListener implements RocketMQLocalTransactionListener {
//
//    @Resource
//    private OrderDao orderDao;
//
//
//    @Override
//    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
//        Order order = JSON.parseObject(new String((byte[]) message.getPayload()), Order.class);
//        Long args = (Long) o;
//        System.out.println(String.format("half message\npayload:%s, arg:%s, transactionId:%s", order, args,
//                message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID)));
//        return RocketMQLocalTransactionState.COMMIT;
//    }
//
//    @Override
//    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
//        Order order = JSON.parseObject(new String((byte[]) message.getPayload()), Order.class);
//        List<Order> orders = orderDao.queryByTradeAndItem(order.getTradeId(), order.getItemId());
//
//        // 根据message去查询本地事务是否执行成功，如果成功，则commit
//        if (orders.size() > 0) {
//            return RocketMQLocalTransactionState.COMMIT;
//        } else {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
//    }
//}
