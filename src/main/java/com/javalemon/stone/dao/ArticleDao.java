package com.javalemon.stone.dao;

import com.javalemon.stone.dao.mapper.ArticleMapper;
import com.javalemon.stone.dao.mapper.VideoMapper;
import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.dto.VideoDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息
 */
@Repository
public class ArticleDao {

    @Resource
    private ArticleMapper articleMapper;

    public int addArticleVideo(ArticleVideoDTO articleVideoDTO) {
        return articleMapper.addArticleVideo(articleVideoDTO);
    }

    public List<ArticleVideoDTO> listArticleVideo() {
        return articleMapper.listArticleVideo();
    }

    public ArticleVideoDTO getArticleDetail(int articleId) {
        return articleMapper.getArticleDetail(articleId);
    }
}
