package com.example.seckilldemo.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    private IUserService iUserService;


    @RequestMapping(value = "/toList")
    public String toLogin(HttpServletResponse response, HttpServletRequest request, Model model,
                          @CookieValue("userTicket") String ticket){
        if(StringUtils.isEmpty(ticket))return "login";
        User user = iUserService.getByUserTicket(ticket, request, response);
        if(null == user)return "login";
        model.addAttribute("user", user);
        return "goodsList";
    }
}
