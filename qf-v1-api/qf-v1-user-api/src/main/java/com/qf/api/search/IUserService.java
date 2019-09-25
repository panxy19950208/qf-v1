package com.qf.api.search;

import com.qf.v1.common.base.IBaseService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;

public interface IUserService extends IBaseService<TUser>{
     TUser checkLogin(TUser user);

     public ResultBean checkIsLogin(String uuid);
}
