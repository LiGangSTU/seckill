package com.example.seckilldemo.controller;

import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.vo.LoginVo;
import com.example.seckilldemo.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/login")
@Slf4j
@Api(value = "登录", tags = "登录")
public class LoginController {

    @Autowired
    private IUserService iUserService;

    /**
     * 跳转登录界面
     * @return
     */
    @ApiOperation("跳转登录界面")
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin(){return "login";}

    /**
     * 登录
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo){
        log.info(loginVo.toString());
        // return iUserService.doLogin(loginVo, request, response);
        return iUserService.login(loginVo);
    }
}
