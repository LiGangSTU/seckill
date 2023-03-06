package com.example.seckilldemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.mapper.SeckillOrderMapper;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Primary
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectById(
                new QueryWrapper<SeckillOrder>()
                        .eq("user_id", user.getId())
                        .eq("goods_id", goodsId)
        );
        if(null != seckillOrder){
            return seckillOrder.getOrderId();
        }else {
            if(redisTemplate.hasKey("isStockEmpty:"+goodsId)){
                return -1L;
            }else {
                return 0L;
            }
        }
    }
}
