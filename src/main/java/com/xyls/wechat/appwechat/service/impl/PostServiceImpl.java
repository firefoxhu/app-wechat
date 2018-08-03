package com.xyls.wechat.appwechat.service.impl;
import com.google.common.collect.Maps;
import com.xyls.wechat.appwechat.consts.MapKeyConst;
import com.xyls.wechat.appwechat.dto.PostDTO;
import com.xyls.wechat.appwechat.dto.form.PostForm;
import com.xyls.wechat.appwechat.model.Post;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import com.xyls.wechat.appwechat.repository.PostRepository;
import com.xyls.wechat.appwechat.service.PostService;
import com.xyls.wechat.appwechat.util.DateUtil;
import com.xyls.wechat.appwechat.util.GenKeyUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ProjectProperties projectProperties;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Map<String, Object> findAll(Pageable pageable) {


        Page<Post> postPage=postRepository.findAll((root, query, cb)-> cb.and(cb.equal(root.get("status"),"0")),pageable);

        Map<String,Object> result = Maps.newHashMap();

        result.put(MapKeyConst.HAS_NEXT,postPage.hasNext());

        result.put(MapKeyConst.LIST,postPage.getContent().stream().map(e-> {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(e,postDTO);
            String[] temp = StringUtils.split(e.getPic()!= null ? e.getPic():"",",");
            for (int i=0;i<temp.length;i++){
                temp[i] = projectProperties.getFile().getUrlPrefix().concat(temp[i]);
            }
            postDTO.setPic(temp);
            return postDTO;
        }).collect(Collectors.toList()));
        return result;
    }


    @Override
    public PostForm sendPost(PostForm postForm,HttpServletRequest request) {

        if(this.todayCountByIp(postForm.getIp()) >= 5){
            throw new RuntimeException("为了系统不被灌水，限制每人最多发5个消息，请原谅...");
        }


        String[] images = postForm.getPic() == "" ? new String[]{}: postForm.getPic().split(",");

        try {

            if(images.length != 0){
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists())
                    path = new File("");

                File upload = new File(path.getAbsolutePath(),projectProperties.getFile().getFileTemp());
                if(!upload.exists()) upload.mkdirs();

                String destDir =upload.getAbsolutePath();

                String destFolderName = projectProperties.getFile().getFileDir()+"/"+DateUtil.todayDate();

                File destFile = new File(destFolderName);

                if(!destFile.exists()) destFile.mkdirs();

                for(String image:images){
                    try {
                        File sourceFile = new File(destDir+image);
                        if(sourceFile.isFile() && sourceFile.exists()) {
                            FileUtils.copyFile(sourceFile,new File(destFolderName+image));
                            sourceFile.delete();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] fileName = new String[images.length];
        for(int i=0;i<fileName.length;i++){
            fileName[i] = projectProperties.getFile().getFolderPrefix()+"/"+DateUtil.todayDate()+images[i];
        }

        Post post = new Post();
        BeanUtils.copyProperties(postForm,post);
        post.setPid(GenKeyUtil.key());
        // 设置图片的名称保持和主键一致最好
        post.setPic(StringUtils.join(fileName,","));
        post.setStatus("0");
        post.setTop("0");
        post.setCreateDate(DateUtil.todayDate());
        post.setCreateTime(DateUtil.todayDateTime());
        post = postRepository.save(post);
        postForm.setPid(post.getPid());
        return postForm;
    }

    @Override
    public long todayCountByIp(String ip) {
        return postRepository.countPostByCreateDateAndIp(DateUtil.todayDate(),ip);
    }
}
