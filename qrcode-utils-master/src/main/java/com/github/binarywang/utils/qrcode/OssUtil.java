package com.github.binarywang.utils.qrcode;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: xingyan.wzz
 * @create: 2020-05-21 10:32
 */
@Component
public class OssUtil {
    @Autowired
    private ConstantConfig constantConfig;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OssUtil.class);

    /** 上传文件*/
    public String upLoad(File file){
        logger.info("------OSS文件上传开始--------"+file.getName());
        String endpoint=constantConfig.getLXIMAGE_END_POINT();
        String accessKeyId=constantConfig.getLXIMAGE_ACCESS_KEY_ID();
        String accessKeySecret=constantConfig.getLXIMAGE_ACCESS_KEY_SECRET();
        String bucketName=constantConfig.getLXIMAGE_BUCKET_NAME1();
        String fileHost=constantConfig.getLXIMAGE_FILE_HOST();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(new Date());

        // 判断文件
        if(file==null){
            return null;
        }
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            String str= UUID.randomUUID().toString().replace("-", "");
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" +str+".png";
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("------OSS文件上传成功------" + fileUrl);
                return str;
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException ce){
            logger.error(ce.getErrorMessage());
        }finally{
            if(client!=null){
                client.shutdown();
            }
        }
        return null;
    }

    public String upLoad2(File file){
        logger.info("------OSS文件上传开始--------"+file.getName());
        String endpoint=constantConfig.getLXIMAGE_END_POINT();
        String accessKeyId=constantConfig.getLXIMAGE_ACCESS_KEY_ID();
        String accessKeySecret=constantConfig.getLXIMAGE_ACCESS_KEY_SECRET();
        String bucketName=constantConfig.getLXIMAGE_BUCKET_NAME1();
        String fileHost=constantConfig.getLXIMAGE_FILE_HOST();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(new Date());

        // 判断文件
        if(file==null){
            return null;
        }
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            String str= "a";
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" +str+".png";
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("------OSS文件上传成功------" + fileUrl);
                return str;
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException ce){
            logger.error(ce.getErrorMessage());
        }finally{
            if(client!=null){
                client.shutdown();
            }
        }
        return null;
    }
}

