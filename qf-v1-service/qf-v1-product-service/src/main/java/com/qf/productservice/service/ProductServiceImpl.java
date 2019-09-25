package com.qf.productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v1.api.product.IProductService;
import com.qf.v1.api.product.vo.TProductVO;
import com.qf.v1.common.base.BaseServiceImpl;
import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.entity.TProduct;
import com.qf.v1.entity.TProductDesc;
import com.qf.v1.mapper.TProductDescMapper;
import com.qf.v1.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }


    @Override
    public Long save(TProductVO productVO) {
        //1.保存商品的基本信息。做主键回填
        TProduct product = productVO.getProduct();
        productMapper.insertSelective(product); 
        System.out.println(product);
		
		//2.保存商品的描述信息
        TProductDesc desc = new TProductDesc();
        desc.setProductDesc(productVO.getProductDesc());
        desc.setProductId(product.getId());
        productDescMapper.insertSelective(desc);
		
        return product.getId();
    }
}
