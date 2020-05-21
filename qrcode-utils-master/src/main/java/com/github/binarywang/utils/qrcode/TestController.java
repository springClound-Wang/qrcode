package com.github.binarywang.utils.qrcode;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 成就客户、专业之上、持续创新、诚信敬业、合作共赢
 * 成为一家世界级的数字商业云服务厂商
 *
 * @Author: xingyan,
 * @Date: 2020/5/20
 * @DESC:
 */

@Api(tags = "图片")
@Controller
@RequestMapping(value = "/image")

public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    private List<Path> generatedQrcodePaths = Lists.newArrayList();
    @ApiOperation("生成二维码")
    @GetMapping(value = "/getImg",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImg(String content) throws IOException {
        byte[] bytes = QrcodeUtils.createQrcode(content, 800, null);
        Path path = Files.createTempFile("qrcode_800_", ".jpg");
        generatedQrcodePaths.add(path);
        logger.info("===={}", path.toAbsolutePath());
        path= Files.write(path, bytes);
        File file = new File(path.toAbsolutePath().toString());
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes1 = new byte[inputStream.available()];
        inputStream.read(bytes1, 0, inputStream.available());
        return bytes;
    }

    @Autowired
    private OssUtil aliyunOSSUtil;

    @ApiOperation("上传新的图片图片")
    @ResponseBody
    @PostMapping("/upfile")
    public String upfile(@RequestParam("file") MultipartFile file){
        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        String uploadUrl="";
        try {

            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                     uploadUrl = aliyunOSSUtil.upLoad(newFile);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  "访问地址{}:"+"http://59.110.64.4:8090/img/index.html?id="+uploadUrl;
    }

    @ApiOperation("覆盖图片")
    @ResponseBody
    @PostMapping("/img2")
    public String img2(@RequestParam("file") MultipartFile file){
        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        String uploadUrl="";
        try {

            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    uploadUrl = aliyunOSSUtil.upLoad2(newFile);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  "访问地址{}:"+"http://59.110.64.4:8090/img/index1.html";
    }
}
