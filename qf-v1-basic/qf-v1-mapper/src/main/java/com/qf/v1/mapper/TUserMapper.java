package com.qf.v1.mapper;

import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.entity.TUser;

public interface TUserMapper extends IBaseDao<TUser>{

    TUser selectByUsername(String username);
}