package com.qf.qfv1itemservice.service;

import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v1.api.item.ItemService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Component
@Service
public class ItemServiceImpl implements ItemService {

    //创建开启多线程
    private static final Integer CORE_THREAD = Runtime.getRuntime().availableProcessors();
    //创建线程池的方式
    //采用线程池的方式JDK提供的4中线程池（都有缺陷）前三种都会出现OOM异常，内存溢出 队列太长
    // 1.Executors.newFixedThreadPool();---->单实例的线程池，保证提交的任务顺序执行 同上
    //2.Executors.newSingleThreadExecutor();---》创建固定数量的线程的线程池385
    //3.Executors.newCachedThreadPool();---》只要jvm内存还够，就一直创建新的线程
    //4.Executors.newScheduledThreadPool();--》定时任务相关的线程池

    //线程池有4个关键参数：初始化线程数、
    // 最大线程数、
    // 发呆时间（处于空闲一定时间内回收线程）、
    // 队列 缓冲作用
    //min:1 max:10
    private ExecutorService pool =new ThreadPoolExecutor(CORE_THREAD,
            CORE_THREAD*2,10L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(100));



    @Autowired
    private Configuration configuration;

    @Value("${template.path}")
    private String TEMPLATE_PATH;

    @Override
    public ResultBean createPage(TProduct product) {
        //通过Freemarker的技术来生成静态页面
        //模板+数据=输出
        try {
            //1.获取模板对象
            Template template = configuration.getTemplate("detail.ftl");
            //2.构建数据
            Map<String,Object> data = new HashMap<>();
            data.put("product",product);
            //3.输出
            //1.html
            //10000.html
            FileWriter out = new FileWriter(TEMPLATE_PATH+product.getId()+".html");
            template.process(data,out);
            return ResultBean.successResult("success");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return ResultBean.errorResult("生成静态页面失败，id为："+product.getId());
    }

    @Override
    public ResultBean batchCreatePage(List<TProduct> products) {
        //
        List<Future> futures = new ArrayList<>(products.size());
        //优化这个处理方式 1000 64核 单核 多线程
        for (TProduct product : products) {
            //给我们线程池提交任务,开启线程执行任务
            futures.add(pool.submit(new CreatePageTask(product)));
            //createPage(product);
        }
        //输出结果
        for (Future future : futures) {
            try {
                //
                Object o = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return ResultBean.successResult("success");
    }

    class CreatePageTask implements Callable<Boolean>{
        private TProduct product;

        public CreatePageTask(TProduct product){
            this.product = product;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                //1.获取模板对象
                Template template = configuration.getTemplate("detail.ftl");
                //2.构建数据
                Map<String,Object> data = new HashMap<>();
                data.put("product",product);
                //3.输出
                //1.html
                //10000.html
                FileWriter out = new FileWriter(TEMPLATE_PATH+product.getId()+".html");
                template.process(data,out);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    //Thread
    //Runnable
    //Callable

//    class CreatePageTask extends Thread{
//        @Override
//        public void run() {
//
//        }
//    }
//    class CreatePageTask2 implements Runnable {
//        @Override
//        public void run() {
//
//        }
//    }


}
