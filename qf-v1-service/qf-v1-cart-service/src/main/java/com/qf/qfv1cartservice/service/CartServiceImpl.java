package com.qf.qfv1cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.api.cart.ICartService;
import com.qf.api.cart.pojo.CartItem;
import com.qf.api.cart.vo.CartItemVO;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TProduct;
import com.qf.v1.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TProductMapper productMapper;

    @Override
    public ResultBean add(String key, Long productId, Integer count) {
        //根据key找到购物车
        List<CartItem> cart = (List<CartItem>) redisTemplate.opsForValue().get(key);
        //判断购物车是否存在
        if(cart == null){
            cart = new ArrayList<>();
            cart.add(new CartItem(productId,count,new Date()));
            //保存在Redis中,刷新有效期
            return resetToRedis(key, cart);
        }
        //购物车存在
        //遍历查看当前添加的商品是否在购物车里
        for (CartItem cartItem : cart) {
            if(cartItem.getProductId().longValue() == productId.longValue()){
                cartItem.setCount(cartItem.getCount()+count);
                cartItem.setUpdateTime(new Date());
                //保存在Redis中，刷新有效时间
                return resetToRedis(key, cart);
            }
        }
        //商品不存在购物车中
        cart.add(new CartItem(productId,count,new Date()));
        //保存在Redis中,刷新有效期
        return resetToRedis(key, cart);
    }

    @Override
    public ResultBean updateCount(String key, Long productId, Integer count) {
        //获取购物车
        List<CartItem> carts = (List<CartItem>) redisTemplate.opsForValue().get(key);
        if(carts != null){
            //遍历更新数量
            for (CartItem cart : carts) {
                if(productId.longValue() == cart.getProductId().longValue()){
                    cart.setCount(count);
                    cart.setUpdateTime(new Date());
                    //在存进redis
                    return resetToRedis(key, carts);
                }
            }
        }
        return ResultBean.errorResult("跟新失败");
    }

    @Override
    public ResultBean list(String key) {
        List<CartItem> cartItems = (List<CartItem>) redisTemplate.opsForValue().get(key);
        //List<CartItem> --->List<CartItemVO>
        if(cartItems == null){
            return ResultBean.errorResult("当前购物车没有商品");
        }
        //tree可排序
        List<CartItemVO> cartItemVOS = new ArrayList<>(cartItems.size());
        for (CartItem cartItem : cartItems) {
            CartItemVO vo = new CartItemVO();
            vo.setCount(cartItem.getCount());
            vo.setUpdateTime(cartItem.getUpdateTime());
            //缓存在这里发挥作用
            //根据2/8原则，提前做好热门商品预热
            //key------------value
            //product:100----productInfo
            StringBuilder stringBuilder = new StringBuilder("product:").append(cartItem.getProductId());
            TProduct product = (TProduct) redisTemplate.opsForValue().get(stringBuilder.toString());
            if(product == null){
                //查询数据库,说明不是热门商品
                product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                //缓存一下，设置有效期
                redisTemplate.opsForValue().set(stringBuilder.toString(),product);
                redisTemplate.expire(stringBuilder.toString(),60,TimeUnit.MINUTES);
            }
            vo.setProduct(product);
            cartItemVOS.add(vo);
        }
        Collections.sort(cartItemVOS, new Comparator<CartItemVO>() {
            @Override
            public int compare(CartItemVO o1, CartItemVO o2) {
                return (int) (o2.getUpdateTime().getTime()-o1.getUpdateTime().getTime());
            }
        });
        return new ResultBean(0,cartItemVOS);
    }

    @Override
    public ResultBean del(String key, Long productId) {

        //获取购物车
        List<CartItem> carts = (List<CartItem>) redisTemplate.opsForValue().get(key);
        //购物车不用为空的情况下
        if(carts != null){
            //遍历集合
            for (CartItem cart : carts) {
                //在遍历中找到相应id的商品
                if(productId.longValue() == cart.getProductId().longValue()){
                    //在集合中删除商品
                    carts.remove(cart);
                    //更新reids
                    return resetToRedis(key, carts);
                }
            }
        }
        return ResultBean.errorResult("删除失败");
    }

    @Override
    public ResultBean merge(String noLoginKey, String loginKey) {
        //获取未登录的购物车
        List<CartItem> noLoginCarts = (List<CartItem>) redisTemplate.opsForValue().get(noLoginKey);
        if(noLoginCarts == null){
            return ResultBean.errorResult("未登录时没添加购物车，不用合并");
        }

        //获取登录候的购物车
        List<CartItem> LoginCarts = (List<CartItem>) redisTemplate.opsForValue().get(loginKey);
        if(LoginCarts == null){
            //不存在已登录的购物车
            redisTemplate.opsForValue().set(loginKey,noLoginCarts);
            return resetToRedis(loginKey,noLoginCarts);
        }
        //未登录和已登录都有购物车
        //两个for循环
        //hashmap key value ------>cartItem
        HashMap<Long,CartItem> map = new HashMap<>();
        for (CartItem noLoginCart : noLoginCarts) {
            map.put(noLoginCart.getProductId(),noLoginCart);
        }
        for (CartItem loginCart : LoginCarts) {
            CartItem cartItem = map.get(loginCart.getProductId());
            if(cartItem == null){
                map.put(loginCart.getProductId(),loginCart);
            }else{
                cartItem.setCount(cartItem.getCount()+loginCart.getCount());
            }
        }
        //map--->list
        Collection<CartItem> values = map.values();
        List<CartItem> list = new ArrayList<>(values);
        //将list写到已登录购物车
        ResultBean resultBean = resetToRedis(loginKey, list);
        //删除未登录购物车
        redisTemplate.delete(noLoginKey);
        return resultBean;
    }

    //抽出的方法
    private ResultBean resetToRedis(String key, List<CartItem> carts) {
        redisTemplate.opsForValue().set(key,carts);
        redisTemplate.expire(key,30, TimeUnit.DAYS);
        return ResultBean.successResult(String.valueOf(carts.size()));
    }
}
