package com.ibase.mall.auth.entity;

import java.io.Serializable;

/**
 * Created by huixiong on 2018/1/4.
 */
public class CartInfoEntity implements Serializable {
    private Integer id;
    private Long userId;
    private Long goodsId;
    private String tradeId;

    public CartInfoEntity() {
    }

    public CartInfoEntity(Integer id, Long userId, Long goodsId) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
