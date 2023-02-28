package com.example.seckilldemo.service.impl;

import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.mapper.OrderMapper;
import com.example.seckilldemo.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
