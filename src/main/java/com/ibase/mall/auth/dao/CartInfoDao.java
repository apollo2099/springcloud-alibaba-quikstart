package com.ibase.mall.auth.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibase.mall.auth.entity.CartInfoEntity;

import java.util.Map;

/**
 * Created by huixiong on 2018/1/4.
 */
public interface CartInfoDao extends BaseMapper<CartInfoEntity> {

    CartInfoEntity getCartInfoByUserId(Map param);
}