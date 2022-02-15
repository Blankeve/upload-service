package com.novedu.nov.upload_service.controller;

import com.novedu.nov.upload_service.common.BaseResult;
import com.novedu.nov.upload_service.helper.SnowFlake;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：juam
 * @date ：2022/1/5 9:52
 * @description：
 * @modified By：
 * @version:
 */
@Api("上传的接口文档")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private SnowFlake snowFlake;
    @Value("${user.filePath}")
    private String filePath;

    @Value("${server.port}")
    private String localPort;


    @PostMapping("/img")
    public BaseResult<Map> uploadImg(MultipartFile img) {
        String imgName = img.getOriginalFilename();
        if (img.isEmpty() || !StringUtils.hasText(imgName)
                || imgName.lastIndexOf(".") == -1
        ) {
            return BaseResult.error("上传失败,图片不能为空");
        }
        String suffix = imgName.substring(imgName.lastIndexOf(".")).toLowerCase(); //文件后缀
        Set<String> allowSuffix = new HashSet<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));
        if (!allowSuffix.contains(suffix)) {
            return BaseResult.error("上传失败,不允许的文件类型");
        }
        if (img.getSize() / 1024 / 1024 > 10) {
            return BaseResult.error("上传失败,图片需在10MB以下");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        String[] time = today.split("-");
        String imgPath = String.format("img/%s/%s/%s/", time[0], time[1], time[2]);
        String savePath = String.format("%s%s", filePath, imgPath);
        File dest = new File(savePath);
        try {
            if (!dest.exists())
                dest.mkdirs();

            imgName = snowFlake.nextValue() + suffix;
            File dest2 = new File(savePath, imgName);
            img.transferTo(dest2);
            return BaseResult.success().mapSet("path", "/" + imgPath + imgName);
        } catch (IOException e) {
            return BaseResult.error(e.getMessage());
        } catch (Exception e) {
            return BaseResult.error(e.getMessage());
        }
    }

    @PostMapping("/video")
    public BaseResult<Map> uploadVideo(MultipartFile video) {
        String videoName = video.getOriginalFilename();
        if (video.isEmpty() || !StringUtils.hasText(videoName)
                || videoName.lastIndexOf(".") == -1
        ) {
            return BaseResult.error("上传失败,视频不能为空");
        }
        String suffix = videoName.substring(videoName.lastIndexOf(".")).toLowerCase(); //文件后缀
        Set<String> allowSuffix = new HashSet<>(Arrays.asList(".avi", ".mov", ".rmvb", ".rm", ".flv", ".mp4", ".3gp"));
        if (!allowSuffix.contains(suffix)) {
            return BaseResult.error("上传失败,不允许的文件类型");
        }
        float videoSize = video.getSize() / 1024f / 1024;
        videoSize = new BigDecimal(videoSize).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if (videoSize > 300) {
            return BaseResult.error("上传失败,视频需在300MB以下");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        String[] time = today.split("-");
        String videoPath = String.format("video/%s/%s/%s/", time[0], time[1], time[2]);
        String savePath = String.format("%s%s", filePath, videoPath);
        File dest = new File(savePath);
        try {
            if (!dest.exists())
                dest.mkdirs();
            videoName = snowFlake.nextValue() + suffix;
            File dest2 = new File(savePath, videoName);
            video.transferTo(dest2);
            MultimediaObject multimediaObject = new MultimediaObject(dest2);
            MultimediaInfo multimediaInfo = multimediaObject.getInfo();
            long videoTime = multimediaInfo.getDuration() / 1000;
            return BaseResult.success().mapSet("path", "/" + videoPath + videoName)
                    .mapSet("name", videoName)
                    .mapSet("duration", videoTime)
                    .mapSet("size", videoSize)
                    ;
        } catch (IOException e) {
            return BaseResult.error(e.getMessage());
        } catch (Exception e) {
            return BaseResult.error(e.getMessage());
        }

    }

}
