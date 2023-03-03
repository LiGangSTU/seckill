package com.example.seckilldemo.controller;

import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IOrderService;
import com.example.seckilldemo.vo.OrderDetailVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yswu
 * @since 2023-02-28
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public RespBean detailOoder(User user, Long orderId){
        try{
            if(null == user)return RespBean.error(RespBeanEnum.SESSION_ERROR);
            OrderDetailVo detailVo = orderService.detail(orderId);
            System.out.println(detailVo);
            return RespBean.success(detailVo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }

}
