package com.qf.miaosha.service;

import com.qf.miaosha.entity.TProduct;

public interface IProductService {
    TProduct getById(Long id);

    boolean saleById(Long id);

}
