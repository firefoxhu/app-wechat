package com.xyls.wechat.appwechat.controller;

import com.google.common.collect.Lists;
import com.xyls.wechat.appwechat.dto.SimpleFileDTO;
import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/upload/")
public class FileUploadController {

    @Autowired
    private ProjectProperties projectProperties;

    @PostMapping("image")
    public ServerResponse upload(HttpServletRequest request) throws FileNotFoundException {
        // 获取上传图片列表
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("imgFiles");


        if(files.size() == 0){
            return ServerResponse.failure("没有文件！");
        }

        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists())
            path = new File("");

        File upload = new File(path.getAbsolutePath(),projectProperties.getFile().getFileTemp());
        if(!upload.exists()) upload.mkdirs();

        String destDir =upload.getAbsolutePath();

        File dir = new File(destDir);

        if(!dir.exists()){
            dir.mkdir();
        }

        List<SimpleFileDTO>  result = Lists.newArrayList();
        for(MultipartFile item:files){
            String tempName =  System.currentTimeMillis()+"."+StringUtils.substringAfterLast(item.getOriginalFilename(),".");
            try {
                // 重新定义 生成临时名称
                Thumbnails.of(item.getInputStream()).scale(0.2f).outputQuality(0.25f).toFile(destDir+"/"+ tempName);
                //.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:/w.png")), 0.5f) 水映
            } catch (IOException e) {
                try {
                    item.transferTo(new File(destDir+ "/"+tempName));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }finally {
                result.add(new SimpleFileDTO("/"+tempName));
            }
        }

        return ServerResponse.success(result);
    }

}
