package com.javalemon.stone.dao.mapper;

import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.dto.VideoDTO;

import java.util.List;

/**
 *
 */
public interface ArticleMapper {

    int addArticleVideo(ArticleVideoDTO videoDTO);

    List<ArticleVideoDTO> listArticleVideo();

}
