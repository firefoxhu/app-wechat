package com.xyls.wechat.appwechat.dto.support;

import com.xyls.wechat.appwechat.dto.NewsDTO;
import com.xyls.wechat.appwechat.model.News;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.List;

public class News2DTOConvert {

    public static List<NewsDTO> news2Dto(Page<News> page,String urlPrefix){
        List<NewsDTO> newsDTOList = new ArrayList<>();

        if(page.getContent().size() > 0){
            page.getContent().forEach(n -> {
                NewsDTO  newsDTO = new NewsDTO();
                newsDTO.setId(n.getNewsId());
                newsDTO.setTitle(n.getNewsTitle());
                newsDTO.setResource(n.getNewsSource());
                newsDTO.setAuthor(n.getNewsAuthor());
                newsDTO.setShowType(n.getNewsShowType());
                String[] origin = n.getNewsHomeThumbnail().split(",");
                String[]  temp = new String[origin.length];
                for(int i= 0;i<origin.length;i++){
                    temp[i] = urlPrefix+origin[i];
                }
                newsDTO.setImages(temp);
                newsDTO.setContent(n.getNewsContent());
                newsDTO.setViews(n.getNewsViews());
                newsDTO.setFabulous(n.getFabulous());
                newsDTOList.add(newsDTO);
            });
        }
        return newsDTOList;
    }
}
