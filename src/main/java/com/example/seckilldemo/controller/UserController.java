package com.example.seckilldemo.controller;


import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.vo.RespBean;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/info")
    public RespBean info(User user){
        return RespBean.success(user);
    }

}
