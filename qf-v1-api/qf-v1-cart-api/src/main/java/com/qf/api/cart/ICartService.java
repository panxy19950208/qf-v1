package com.qf.api.cart;

import com.qf.v1.common.pojo.ResultBean;

public interface ICartService {

    //添加 参数：key确定购物车，商品id,购买数量
    public ResultBean add(String key,Long productId,Integer count);
    //修改(更新数量)
    public ResultBean updateCount(String key,Long productId,Integer count);
    //查询
    public ResultBean list(String key);
    //删除
    public ResultBean del(String Key,Long productId);
    //合并未登录购物车
    public ResultBean merge(String noLoginKey,String loginKey);



}
