package com.example.seckilldemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IGoodsService;
import com.example.seckilldemo.service.IOrderService;
import com.example.seckilldemo.service.ISeckillOrderService;
import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.vo.GoodsVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/seckill")
@ApiOperation("秒杀订单")
public class SeckillController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill( User user, Long goodsId){
       try {
           System.out.println("doSeckill: "+user);
           if(user == null) {
               System.out.println("用户获取失败");
               return RespBean.success(RespBeanEnum.SESSION_ERROR);
           }
           GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
           //判断库存
           if(goodsVo.getStockCount() < 1){
               //跳转到失败页面
               return RespBean.error(RespBeanEnum.EMPTY_STOCK);
           }
           // 判断是否重复抢购
//           SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                   .eq("user_id", user.getId())
//                   .eq("goods_id", goodsId));
           //改为从redis中读取
           String seckillOrderJson = (String) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
           if(!StringUtils.isEmpty(seckillOrderJson))return RespBean.error(RespBeanEnum.REPEATE_ERROR);
           Order order = orderService.seckill(user, goodsVo);
           return RespBean.success(order);
       }catch (Exception e){
           e.printStackTrace();
       }
       return RespBean.error(RespBeanEnum.ERROR);
    }

    @RequestMapping("/doSeckill1")
    public String doSeckill1(Model model, User user, Long goodsId){
        System.out.println(user);
        if(user == null) {
            System.out.println("用户获取失败");
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if(goodsVo.getStockCount() < 1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            //跳转到失败页面
            return "seckillFail";
        }
        // 判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq(
                "goods_id", goodsId));
        if(seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }
        Order order = orderService.seckill(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }
}
