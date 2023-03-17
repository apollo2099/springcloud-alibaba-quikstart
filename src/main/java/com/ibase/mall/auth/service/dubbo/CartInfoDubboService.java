package com.ibase.mall.auth.service.dubbo;

import com.ibase.mall.auth.dao.CartInfoDao;
import com.ibase.mall.auth.entity.CartInfoEntity;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@DubboService
@Component
public class CartInfoDubboService implements CartInfoDubbo{

    @Resource
    private CartInfoDao cartInfoDao;

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

}
