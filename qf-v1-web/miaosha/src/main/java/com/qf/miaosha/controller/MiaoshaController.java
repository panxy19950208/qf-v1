package com.qf.miaosha.controller;

import com.qf.miaosha.entity.TProduct;
import com.qf.miaosha.exception.SeckillException;
import com.qf.miaosha.pojo.ResultBean;
import com.qf.miaosha.service.IProductService;
import com.qf.miaosha.service.ISeckillService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/miaosha")
@Controller
public class MiaoshaController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ISeckillService seckillService;

    @RequestMapping("getById")
    public String getById(Long id, Model model){
        TProduct product = productService.getById(id);
        model.addAttribute("product",product);
        return "detail";
    }

    @RequestMapping("saleById")
    @ResponseBody
    public String saleById(Long id){
        boolean result = productService.saleById(id);
        if(result){
            return "success";
        }
        return "faild";
    }

    @RequestMapping("seckill")
    @ResponseBody
    public ResultBean seckill(Long seckillId,Long userId){
        try{
            seckillService.seckill(seckillId,userId);
            String orderNo = seckillService.sendMessageToOrder(seckillId, userId);
            //将订单编号回传给前台页面，前端根据订单编号查询订单状态
            //如果订单生成，则进行订单支付页面
            return new ResultBean("200",orderNo);
        }catch (SeckillException e){
            return new ResultBean("200",e.getMessage());
        }

    }

}
