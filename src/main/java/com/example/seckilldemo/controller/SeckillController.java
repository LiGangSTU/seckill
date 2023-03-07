package com.example.seckilldemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.rabbitmq.MQSender;
import com.example.seckilldemo.service.IGoodsService;
import com.example.seckilldemo.service.IOrderService;
import com.example.seckilldemo.service.ISeckillOrderService;
import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.utils.JsonUtil;
import com.example.seckilldemo.vo.GoodsVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import com.example.seckilldemo.vo.SeckillMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/seckill")
@ApiOperation("秒杀订单")
public class SeckillController implements InitializingBean {
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
    @Autowired
    private MQSender mqSender;
    @Autowired
    private RedisScript<Long> redisScript;

    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill( User user, Long goodsId){
       try {
           System.out.println("doSeckill: "+user);
           if(user == null) {
               System.out.println("用户获取失败");
               return RespBean.success(RespBeanEnum.SESSION_ERROR);
           }
           ValueOperations valueOperations = redisTemplate.opsForValue();
           // 判断是否重复抢购
           String seckillOrderJson = (String) valueOperations.get("order:"+user.getId()+":"+goodsId);
           if(!StringUtils.isEmpty(seckillOrderJson)){
               return RespBean.error(RespBeanEnum.REPEATE_ERROR);
           }
           //标记内存，减少redis访问
           if(EmptyStockMap.get(goodsId)){
               return RespBean.error(RespBeanEnum.EMPTY_STOCK);
           }
           //预减内存
           Long stock = (Long) redisTemplate.execute(redisScript,
                   Collections.singletonList("seckillGoods:"+goodsId), Collections.EMPTY_LIST);
           if(stock < 0){
               EmptyStockMap.put(goodsId, true);
               //valueOperations.increment("seckillGoods:"+goodsId);
               return RespBean.error(RespBeanEnum.EMPTY_STOCK);
           }
           //请求入队，立即返回排队中
           SeckillMessage message = new SeckillMessage(user, goodsId);
           mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(message));
           return RespBean.success();
           //GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
           //判断库存
           //
           // 判断是否重复抢购
//           SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                   .eq("user_id", user.getId())
//                   .eq("goods_id", goodsId));
           //改为从redis中读取
           //
       }catch (Exception e){
           e.printStackTrace();
       }
       return RespBean.error(RespBeanEnum.ERROR);
    }
    /**
     * 系统初始化，把商品库存数量加载到Redis
     *
     * @param
     * @return void
     * @author LG
     * @operation add
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), false);
        });
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user, Long goodsId){
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
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
