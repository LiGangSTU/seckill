package com.example.seckilldemo.service.impl;

import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.mapper.SeckillOrderMapper;
import com.example.seckilldemo.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
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

}
