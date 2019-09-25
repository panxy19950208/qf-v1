package com.qf.qfv1sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.api.search.IUserService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.common.util.HttpClientUtils;
import com.qf.v1.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("sso")
public class SSOController {

    @Reference
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    //展示登录页
    @RequestMapping("login")
    public String showLogin(HttpServletRequest request){
        //获得从哪来的网址
        String referer = request.getHeader("Referer");
        request.setAttribute("referer",referer);
        return "login";
    }

    //认证登录
    @RequestMapping("checkLogin")
    public String checkLogin(@CookieValue(name = "user_cart",required = false) String userCartToken,
                             TUser user,HttpServletRequest request,
                             HttpServletResponse response){

        //查询数据库
        TUser currentUser = userService.checkLogin(user);
        System.out.println(currentUser.getPhone());
        if(currentUser == null){
            return "login";
        }
        //生成唯一标识uuid
        String uuid = UUID.randomUUID().toString();
        //构造一个cookie
        Cookie cookie = new Cookie("user_token",uuid);
        cookie.setPath("/");
        //设置成父域名，这样所有子域名系统都能共享到
        cookie.setDomain("qf.com");
        //基于安全控制，只允许通过后端来获取到cookie的信息,前端获取不能通过脚本获取
        cookie.setHttpOnly(true);
        //Redis中保存凭证信息
        StringBuilder redisKey = new StringBuilder("user_token:").append(uuid);
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(redisKey.toString(),currentUser);
        //设置有效期
        redisTemplate.expire(redisKey.toString(),30, TimeUnit.MINUTES);
        //相应给客户端cookie
        response.addCookie(cookie);

        //获得点击登录时的页面
        String referer = request.getParameter("referer");
        //登陆成功后，应该调用购物车和并的接口
        //需要一个工具，来模拟浏览器发送http请求
        //httpClient--->apache
        //默认是不携带cookie的许收到发cookie
        StringBuilder value = new StringBuilder("user_token=");
        value.append(uuid);
        value.append(";");
        value.append("user_cart=");
        value.append(userCartToken);

        Map<String,String > params = new HashMap<>();
        params.put("Cookie",value.toString());
        HttpClientUtils.doGetWithHeaders("http://cart.qf.com:9099/cart/merge",params);

        if(referer != null && !"".equals(referer)){
            return "redirect:"+referer;
        }
        return "redirect:http://localhost:9091";
    }

    //同下，旧版
//    @RequestMapping("checkIsLoginOld")
//    @ResponseBody
//    public ResultBean checkIsLoginOld(HttpServletRequest request){
//        //获取到保存在cookie的uuid
//        Cookie[] cookies = request.getCookies();
//        //通过uuid在去redis中找到用户信息
//        if(cookies == null){
//            return ResultBean.errorResult("当前用户没有登录");
//        }
//        for (Cookie cookie : cookies) {
//            if("user_token".equals(cookie.getName())){
//                String uuid = cookie.getValue();
//                StringBuilder redisKey = new StringBuilder("user_token:").append(uuid);
//                //序列化
//                redisTemplate.setKeySerializer(new StringRedisSerializer());
//                TUser user = (TUser) redisTemplate.opsForValue().get(redisKey.toString());
//                if(user != null){
//                    //刷新凭证的有效期
//                    redisTemplate.expire(redisKey.toString(),30,TimeUnit.MINUTES);
//                    return ResultBean.successResult(user.getUsername());
//                }
//            }
//
//        }
//        return ResultBean.errorResult("用户未登录");
//    }

    //验证登陆
    @RequestMapping("checkIsLogin")
    @ResponseBody
    @CrossOrigin(origins = "*",allowCredentials = "true")
    public ResultBean checkIsLogin(@CookieValue(name = "user_token",required = false)String uuid){
        //获取到保存在cookie的uuid
        //通过uuid在去redis中找到用户信息
        if(uuid == null){
            return ResultBean.errorResult("当前用户没有登录");
        }
        return userService.checkIsLogin(uuid);
    }

    //验证登陆
    @RequestMapping("checkIsLoginForJsonp")
    @ResponseBody
    public String checkIsLoginForJson(@CookieValue(name = "user_token",required = false)String uuid,String callback){
        //获取到保存在cookie的uuid
        //通过uuid在去redis中找到用户信息
        Gson gson = new Gson();
        if(uuid == null){
            ResultBean resultBean = ResultBean.errorResult("当前用户没有登录");
            String json = gson.toJson(resultBean);
            return callback + "("+json+")" ;
        }
        //拼接成RedisKey
        StringBuilder redisKey = new StringBuilder("user_token:").append(uuid);
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        TUser user = (TUser) redisTemplate.opsForValue().get(redisKey.toString());
        if(user != null){
            //刷新凭证的有效期
            redisTemplate.expire(redisKey.toString(),30,TimeUnit.MINUTES);
            ResultBean resultBean = ResultBean.successResult(user.getUsername());
            String json = gson.toJson(resultBean);
            return callback + "("+json+")" ;
        }
        ResultBean resultBean = ResultBean.errorResult("用户未登录");
        String json = gson.toJson(resultBean);
        return callback + "("+json+")" ;
    }

    //注销
    @RequestMapping("loginOut")
    @ResponseBody
    public ResultBean loginOut(
            @CookieValue(name = "user_token",required = false)String uuid
            ,HttpServletResponse response){
        if(uuid == null){
            return ResultBean.errorResult("注销失败");
        }
        //删除cookie
        Cookie cookie = new Cookie("user_token",uuid);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //设置成父域名，这样所有子域名系统都能共享到
        cookie.setDomain("qf.com");
        //让cookie失效
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        //删除redis的凭证
        //拼接成RedisKey
        StringBuilder redisKey = new StringBuilder("user_token:").append(uuid);
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.delete(redisKey.toString());
        return ResultBean.successResult("注销成功");
    }
}
