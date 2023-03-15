package com.ibase.mall.auth.service;
import com.ibase.mall.auth.dao.CartInfoDao;
import com.ibase.mall.auth.entity.CartInfoEntity;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by huixiong on 2018/1/4.
 */
@Component
public class CartInfoService {
    @Resource
    private CartInfoDao cartInfoDao;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public CartInfoEntity getCartInfoByUserId(Long userId){
        // 获取分表名称
        String tableName ="js_cart_info";
        // 暂时先屏蔽分表功能
        // tableName = TableModelHelper.getCartInfoTableName(String.valueOf(userId), tableName);
        // 组装参数
        Map<String,Object> param = new HashMap(2);
        param.put("userId",userId);
        param.put("tableName",tableName);
        return cartInfoDao.getCartInfoByUserId(param);
    }

    public CartInfoEntity getCartInfoByTraceId(String traceId){
        // 获取分表名称
        String tableName ="js_cart_info";
        // 暂时先屏蔽分表功能
        // tableName = TableModelHelper.getCartInfoTableName(String.valueOf(userId), tableName);
        // 组装参数
        Map<String,Object> param = new HashMap(2);
        param.put("traceId",traceId);
        param.put("tableName",tableName);
        return cartInfoDao.getCartInfoByTraceId(param);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveCartInfo(Long userId,Long goodsId){
        String transactionId = UUID.randomUUID().toString();
        CartInfoEntity cartInfoEntity = new CartInfoEntity();
        cartInfoEntity.setTradeId(transactionId);
        cartInfoEntity.setGoodsId(goodsId);
        cartInfoEntity.setUserId(userId);
        cartInfoDao.insert(cartInfoEntity);


        // 事务id
        rocketMQTemplate.sendMessageInTransaction("update-account-score",
                MessageBuilder.withPayload(cartInfoEntity)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                        .setHeader("goods_id", goodsId).build(),
                4L
        );
        System.out.println(" prepare 消息发送成功");

        // 这里消息发送只是prepare发送，
        // 后面消息队列中prepare成功后，在TestTransactionListener中的executeLocalTransaction的方法中决定是否要提交本地事务
        return true;
    }
}
