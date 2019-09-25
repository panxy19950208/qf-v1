package com.qf.qfv1cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.api.cart.ICartService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("cart")
public class CartController {

    //前后端分离，约定接口
    //并行开发
    //开发接口
    //联调

    @Reference
    private ICartService cartService;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean add(@PathVariable("productId") Long productId,
                           @PathVariable("count") Integer count,
                           @CookieValue(name = "user_cart",required = false) String uuid,
                            HttpServletResponse response,
                            HttpServletRequest request){

        //查看当前用户是否为登陆状态
        //user:cart:userId
        //user:cart:uuid
        TUser user = (TUser) request.getAttribute("user");
        System.err.println(user);
        String key = "";
        if(user != null){
            key = new StringBuilder("user:cart:").append(user.getId()).toString();
        }else{
            if(uuid == null){
                uuid = UUID.randomUUID().toString();
            }
            key = new StringBuilder("user:cart:").append(uuid).toString();
        }

        //写cookie到客户端
        flushCookie(uuid, response);
        return cartService.add(key.toString(),productId,count);
    }

    @RequestMapping("list")
    @ResponseBody
    public ResultBean list(@CookieValue(name = "user_cart",required = false) String uuid,
                           HttpServletResponse response,
                           HttpServletRequest request){
        //从拦截器中接收登录状态
        TUser user = (TUser) request.getAttribute("user");
        System.out.println(user);
        String key = "";
        if(user != null){
            key = new StringBuilder("user:cart:").append(user.getId()).toString();
        }else{
            if(uuid == null){
                    return ResultBean.errorResult("购物车没东西");
            }
            key = new StringBuilder("user:cart:").append(uuid).toString();
        }
        flushCookie(uuid, response);
        return cartService.list(key.toString());
    }

    @RequestMapping("update/{productId}/{count}")
    @ResponseBody
    public ResultBean update(@PathVariable("productId") Long productId,
                             @PathVariable("count") Integer count,
                             @CookieValue(name = "user_cart",required = false) String uuid,
                             HttpServletResponse response,
                             HttpServletRequest request){
        TUser user = (TUser) request.getAttribute("user");
        String key = "";
        if(user != null){
            key = new StringBuilder("user:cart:").append(user.getId()).toString();
        }else{
            if(uuid == null){
                return ResultBean.errorResult("购物车没东西");
            }
            key = new StringBuilder("user:cart:").append(uuid).toString();
        }
        ResultBean resultBean = cartService.updateCount(key.toString(), productId, count);
        //刷新cookie
        flushCookie(uuid,response);
        return resultBean;
    }

    @RequestMapping("del/{productId}")
    @ResponseBody
    public ResultBean del(@PathVariable("productId") Long productId,
                          @CookieValue(name = "user_cart",required = false) String uuid,
                          HttpServletResponse response,
                          HttpServletRequest request){
        TUser user = (TUser) request.getAttribute("user");
        String key = "";
        if(user != null){
            key = new StringBuilder("user:cart:").append(user.getId()).toString();
        }else{
            if(uuid == null){
                return ResultBean.errorResult("购物车没东西");
            }
            key = new StringBuilder("user:cart:").append(uuid).toString();
        }
        ResultBean resultBean = cartService.del(key.toString(), productId);
        flushCookie(uuid,response);
        return resultBean;
    }

    @RequestMapping("merge")
    @ResponseBody
    public ResultBean merge( @CookieValue(name = "user_cart",required = false) String uuid,
                             HttpServletResponse response,
                             HttpServletRequest request){
        TUser user = (TUser) request.getAttribute("user");
        if(user == null){
            return ResultBean.errorResult("未登录");
        }
        if(uuid == null || "".equals(uuid)){
            return ResultBean.errorResult("不存在未登录购物车，不用合并");
        }
        String loginKey = new StringBuilder("user:cart:").append(user.getId()).toString();
        String noLoginKey = new StringBuilder("user:cart:").append(uuid).toString();
        return cartService.merge(noLoginKey,loginKey);

    }


    //抽出的代码
    private void flushCookie(@CookieValue(name = "user_cart", required = false) String uuid, HttpServletResponse response) {
        System.out.println(uuid);
        Cookie cookie = new Cookie("user_cart",uuid);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        response.addCookie(cookie);
    }
}

