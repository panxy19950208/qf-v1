package com.qf.v1.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {
    /**
     * 具体实现由子类负责
     * @return
     */
    public abstract IBaseDao<T> getBaseDao();
    public int deleteByPrimaryKey(Long id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    public int insert(T record) {
        return getBaseDao().insert(record);
    }

    public int insertSelective(T record) {
        return getBaseDao().insertSelective(record);
    }

    public T selectByPrimaryKey(Long id) {
        return (T) getBaseDao().selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T record) {
        return getBaseDao().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(T record) {
        return getBaseDao().updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(T record) {
        return getBaseDao().updateByPrimaryKey(record);
    }

    public List<T> getList(){
        return getBaseDao().getList();
    }

    public PageInfo<T> getPageList(Integer pageIndex,Integer pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        List<T> list = getList();
        PageInfo<T> pageInfo = new PageInfo<T>(list,3);
        return pageInfo;
    }
}
