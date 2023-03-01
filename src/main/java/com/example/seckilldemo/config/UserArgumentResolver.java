package com.example.seckilldemo.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        Class<?> tClass = parameter.getParameterType();
        return tClass == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)throws Exception{
        return UserContext.getUser();
//        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        String ticket = CookieUtil.getCookieValue(request, "userTicker");
//        if(StringUtils.isEmpty(ticket))return null;
//        return userService.getByUserTicket(ticket, request, response);
    }
}
