package com.qf.miaosha.service;

public interface ISeckillService {

    public void seckill(Long seackillId,Long userId);

    String sendMessageToOrder(Long seckillId, Long userId);
}
