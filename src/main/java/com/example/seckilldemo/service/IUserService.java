package com.example.seckilldemo.service;

import com.example.seckilldemo.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.vo.LoginVo;
import com.example.seckilldemo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
    RespBean login(LoginVo loginVo);
}
