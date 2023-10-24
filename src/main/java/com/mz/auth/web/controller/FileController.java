package com.mz.auth.web.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.mz.auth.entity.User;
import com.mz.auth.service.UserService;
import com.mz.auth.util.MzResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description: FileController 处理文件或者图片的类 如上传头像
 */
@Controller
public class FileController {
    //注入service层
    @Autowired
    private UserService userService;

    @Value("${aliyun.oss.file.accessKeyId}")
    String accessKeyId;
    @Value("${aliyun.oss.file.accessKeySecret}")
    String accessKeySecret;
    @Value("${aliyun.oss.file.endpoint}")
    String endpoint;
    @Value("${aliyun.oss.file.bucketName}")
    String bucketName;

    /**
     * 上传方法 uploadFile()
     */
    @PostMapping("/file/uploadFile")  //提交方式post 路径
    @ResponseBody   //返回值 json格式
    public MzResult uploadFile(Long id,@RequestParam("file") MultipartFile file){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //图片为空
            if(file.isEmpty()){
                return MzResult.error("图片为空");
            }
            // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String dir = format; // 用户上传文件时指定的前缀。
            String filename = file.getOriginalFilename();//图片原始名字
            String suffixName = filename.substring(filename.lastIndexOf("."));//后缀名 .png
            //准备唯一的 id 键  uuidStr
            String uuidStr = UUID.randomUUID().toString();
            //新的文件名
            String newFileName = uuidStr+suffixName;

            String objectName = dir+"/"+newFileName;


            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file.getInputStream());
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            if(result.getResponse().getStatusCode()==200){
                User user = new User();
                user.setHeadImg(result.getResponse().getUri());
                user.setId(id);
                userService.updateHeadImgByUser(user);//调用updateHeadImgByUser方法
                return MzResult.ok();
            }
            return  MzResult.error("图片上传失败");
        } catch (OSSException oe) {
          oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
