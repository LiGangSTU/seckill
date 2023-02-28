package com.example.seckilldemo.service.impl;

import com.example.seckilldemo.pojo.Goods;
import com.example.seckilldemo.mapper.GoodsMapper;
import com.example.seckilldemo.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
