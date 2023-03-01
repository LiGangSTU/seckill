package com.example.seckilldemo.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IGoodsService;
import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;
//    @Autowired
//    private IUserService iUserService;
//
//
//
//    @RequestMapping(value = "/toList")
//    public String toLogin(HttpServletResponse response, HttpServletRequest request, Model model,
//                          @CookieValue("userTicket") String ticket){
//        if(StringUtils.isEmpty(ticket))return "login";
//        User user = iUserService.getByUserTicket(ticket, request, response);
//        if(null == user)return "login";
//        model.addAttribute("user", user);
//        return "goodsList";
//    }

    @RequestMapping(value = "/toList")
    public String toList(Model model, User user){
        model.addAttribute("user", user);
        System.out.println("List goods:"+user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        System.out.println(goodsService.findGoodsVo());
        return "goodsList";
    }

    @RequestMapping(value = "/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId){
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        System.out.println(goodsVo);
        model.addAttribute("goods", goodsVo);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //描述状态
        int seckillStatus = 0;
        //剩余时间
        int remainSeconds;
        //描述还未开始
        if(nowDate.before(startDate)){
            remainSeconds = (int) ((startDate.getTime()-nowDate.getTime())/1000);
        }else if(nowDate.after(endDate)){
            //描述已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goodsDetail";
    }
}
