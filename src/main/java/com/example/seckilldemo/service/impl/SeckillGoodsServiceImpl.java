package com.example.seckilldemo.service.impl;

import com.example.seckilldemo.pojo.SeckillGoods;
import com.example.seckilldemo.mapper.SeckillGoodsMapper;
import com.example.seckilldemo.service.ISeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀商品表 服务实现类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Primary
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

}
