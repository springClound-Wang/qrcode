package com.github.binarywang.utils.qrcode;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
}
