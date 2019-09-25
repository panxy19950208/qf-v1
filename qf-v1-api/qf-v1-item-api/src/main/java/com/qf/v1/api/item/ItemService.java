package com.qf.v1.api.item;

import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TProduct;

import java.util.List;

public interface ItemService {
    ResultBean createPage(TProduct product);

    ResultBean batchCreatePage(List<TProduct> products);
}
