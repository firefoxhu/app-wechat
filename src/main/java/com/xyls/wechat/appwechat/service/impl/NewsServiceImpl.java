package com.xyls.wechat.appwechat.service.impl;
import com.xyls.wechat.appwechat.consts.MapKeyConst;
import com.xyls.wechat.appwechat.dto.NewsDTO;
import com.xyls.wechat.appwechat.dto.support.News2DTOConvert;
import com.xyls.wechat.appwechat.model.News;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import com.xyls.wechat.appwechat.repository.NewsRepository;
import com.xyls.wechat.appwechat.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ProjectProperties projectProperties;


    @Override
    public Map<String,Object> findRecommend(Pageable pageable) {

        Map<String,Object>  result = new HashMap();

        Page<News> page=newsRepository.findRecommend(pageable);

        result.put(MapKeyConst.HAS_NEXT,page.hasNext());

        result.put(MapKeyConst.LIST, News2DTOConvert.news2Dto(page,projectProperties.getFile().getUrlPrefix()));

        return  result;

    }

    @Override
    public Map<String, Object> findByCategory(String TypeId,Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<News> page = newsRepository.findByNewsTypeId(TypeId, pageable);
        result.put(MapKeyConst.HAS_NEXT, page.hasNext());
        result.put(MapKeyConst.LIST, News2DTOConvert.news2Dto(page, projectProperties.getFile().getUrlPrefix()));

        return result;
    }



    @Override
    public NewsDTO findById(String id) {

        News n = newsRepository.getOne(id);

        NewsDTO newsDTO = new NewsDTO();

        newsDTO.setId(n.getNewsId());
        newsDTO.setTitle(n.getNewsTitle());
        newsDTO.setResource(n.getNewsSource());
        newsDTO.setAuthor(n.getNewsAuthor());
        newsDTO.setShowType(n.getNewsShowType());
        String[] origin = n.getNewsHomeThumbnail().split(",");
        String[]  temp = new String[origin.length];
        for(int i= 0;i<origin.length;i++){
            temp[i] = projectProperties.getFile().getUrlPrefix()+origin[i];
        }
        newsDTO.setImages(temp);
        newsDTO.setFabulous(n.getFabulous());
        newsDTO.setContent(n.getNewsContent());
        newsDTO.setViews(n.getNewsViews());

        return newsDTO;
    }

    @Transactional
    @Override
    public void modifyFabulous(String id) {
        newsRepository.modifyFabulous(String.valueOf(Integer.parseInt(newsRepository.findById(id).orElseThrow(RuntimeException::new).getFabulous())+1),id);
    }

    @Transactional
    @Override
    public void modifyViews(String id) {
        newsRepository.modifyViews(String.valueOf(Integer.parseInt(newsRepository.findById(id).orElseThrow(RuntimeException::new).getNewsViews())+1),id);
    }

    @Transactional
    @Override
    public void modifyFabulousAndViews() {

        List<News> newsList = newsRepository.findAll();
        log.info("size:{}",newsList);

        for(int i=0;i<newsList.size();i++){
            int fabulous  = RandomUtils.nextInt(10,30);
            int views = RandomUtils.nextInt(50,300);
            log.info("标题：{} ，赞：{} ，浏览量：{}",newsList.get(i).getNewsTitle(),fabulous,views);
            newsRepository.modifyFabulousAndViews(String.valueOf(fabulous),String.valueOf(views),newsList.get(i).getNewsId());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("数据已处理完毕！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");


    }

}
