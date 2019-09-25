package com.qf.qfv1userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.api.search.IUserService;
import com.qf.v1.common.base.BaseServiceImpl;
import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;
import com.qf.v1.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service
public class IUserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {

    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public TUser checkLogin(TUser user) {
       TUser currentUser = userMapper.selectByUsername(user.getUsername());
       if(passwordEncoder.matches(user.getPassword(),currentUser.getPassword())){
            return currentUser;
       }
        return null;
    }

    @Override
    public ResultBean checkIsLogin(String uuid) {
        //拼接成RedisKey
        StringBuilder redisKey = new StringBuilder("user_token:").append(uuid);
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        TUser user = (TUser) redisTemplate.opsForValue().get(redisKey.toString());
        if(user != null){
            //刷新凭证的有效期
            redisTemplate.expire(redisKey.toString(),30, TimeUnit.MINUTES);
            //return ResultBean.successResult(user.getUsername());
            return new ResultBean(0,user);
        }
        return ResultBean.errorResult("用户未登录");
    }

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return userMapper;
    }
}
