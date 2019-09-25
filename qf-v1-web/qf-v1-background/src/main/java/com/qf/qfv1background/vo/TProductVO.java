package com.qf.qfv1background.vo;

import com.qf.v1.entity.TProduct;

import java.io.Serializable;

public class TProductVO implements Serializable {
    //组合方式
    private TProduct product;

    private String productDesc;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public TProductVO(TProduct product, String productDesc) {
        this.product = product;
        this.productDesc = productDesc;
    }
    public TProductVO() {
    }

    @Override
    public String toString() {
        return "TProductVO{" +
                "product=" + product +
                ", productDesc='" + productDesc + '\'' +
                '}';
    }
}
