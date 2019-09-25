package com.qf.qfv1background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.qf.v1.api.product.IProductService;
import com.qf.v1.api.product.IProductTypeService;
import com.qf.v1.common.pojo.MultiResultBean;
import com.qf.v1.entity.TProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("common")
public class CommonController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Reference
    private IProductTypeService productTypeService;

    @Value("${images.server}")
    private String IMAGE_SERVER;

    @RequestMapping("upload")
    @ResponseBody
    public MultiResultBean upload(MultipartFile[] files){
        String[] paths = new String[files.length];
        for(int i=0;i<files.length;i++){
            String originalFilename = files[i].getOriginalFilename();
            String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            try {
                StorePath storePath =
                        fastFileStorageClient.uploadImageAndCrtThumbImage(
                                files[i].getInputStream(), files[i].getSize(), fileExtName, null);
                //
                String fullPath = storePath.getFullPath();
                String path = IMAGE_SERVER+fullPath;
                //将上传的路径保存到paths中
                paths[i] = path;
            } catch (IOException e) {
                e.printStackTrace();
                return MultiResultBean.errorResult("当前系统访问繁忙，请稍后再试！");
            }
        }
        return MultiResultBean.successResult(paths);
    }

    @RequestMapping("getTypeList")
    @ResponseBody
    public List<TProductType> list(){
        return productTypeService.getList();
    }

    @RequestMapping("getTypeListForJsonp")
    @ResponseBody
    public String listForJsonp(String callback){
        List<TProductType> list = productTypeService.getList();
        //list--->json
        Gson gson = new Gson();
        String json = gson.toJson(list);
        //call()
        //jsonp padding 填充
        return callback+"("+json+")";
    }
}
