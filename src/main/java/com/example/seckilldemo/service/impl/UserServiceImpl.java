package com.example.seckilldemo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.exception.GlobalException;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.mapper.UserMapper;
import com.example.seckilldemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.utils.MD5Util;
import com.example.seckilldemo.utils.ValidatorUtil;
import com.example.seckilldemo.vo.LoginVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public RespBean login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){return RespBean.error(RespBeanEnum.LOGIN_ERROR);}
//        if(!ValidatorUtil.isMobile(mobile))return RespBean.error(RespBeanEnum.MOBILE_ERROR);

        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if(null == user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if(!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
           throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}
