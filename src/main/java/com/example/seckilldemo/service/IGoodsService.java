package com.example.seckilldemo.service;

import com.example.seckilldemo.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 根据商品id获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);

}
