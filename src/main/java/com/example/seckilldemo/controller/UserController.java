package com.example.seckilldemo.controller;


import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.rabbitmq.MQReceiver;
import com.example.seckilldemo.rabbitmq.MQSender;
import com.example.seckilldemo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/info")
    public RespBean info(User user){
        return RespBean.success(user);
    }

    /**
     * 测试RabbitMQ
     */
    @RequestMapping("/mq/fanout")
    @ResponseBody
    public void mq() {
        mqSender.send("Hello");
    }
}
