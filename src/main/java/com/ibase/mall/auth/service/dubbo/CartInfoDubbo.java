package com.ibase.mall.auth.service.dubbo;

import com.ibase.mall.auth.entity.CartInfoEntity;

public interface CartInfoDubbo {
    public CartInfoEntity getCartInfoByUserId(Long userId);
}
