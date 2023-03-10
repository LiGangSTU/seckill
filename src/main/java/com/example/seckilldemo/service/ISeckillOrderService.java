package com.example.seckilldemo.service;

import com.example.seckilldemo.pojo.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.User;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {
    Long getResult(User user, Long goodsId);
}
