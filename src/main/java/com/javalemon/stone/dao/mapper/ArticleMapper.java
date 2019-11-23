package com.javalemon.stone.dao.mapper;

import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.dto.VideoDTO;
import com.javalemon.stone.model.param.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface ArticleMapper {

    int addArticleVideo(ArticleVideoDTO videoDTO);

    List<ArticleVideoDTO> listArticleVideo();

    ArticleVideoDTO getArticleDetail( @Param("articleId") int articleId);

    int updateArticleVideo(ArticleVideoDTO articleVideoDTO);

    List<ArticleVideoDTO> listPageArticleVideo(Page page);

    int countArticleVideo();
}
