package com.example.seckilldemo;

import com.example.seckilldemo.exception.GlobalException;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.service.impl.UserServiceImpl;
import com.example.seckilldemo.vo.RespBeanEnum;

import java.util.Date;

public class test {
    public static void main(String[] args) {
        IUserService userService = new UserServiceImpl();
        try {
            User user = new User();
            user.setPassword("123456");
            user.setSalt("1a2b3c4d");
            user.setId(Long.valueOf("18740013090"));
            user.setNickname("1");
            user.setLoginCount(1);
            user.setRegisterDate(new Date());
            user.setLastLoginDate(new Date());
            userService.save(user);
            System.out.println(RespBeanEnum.SUCCESS.getMessage());
        }catch (GlobalException e){
            e.printStackTrace();
        }
    }
}
