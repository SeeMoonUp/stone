package com.javalemon.stone.service;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.controller.ArticleController;
import com.javalemon.stone.dao.ArticleDao;
import com.javalemon.stone.dao.VideoDao;
import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.dto.VideoDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Service
public class ArticleService {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ArticleService.class.getName());

    @Resource
    private ArticleDao articleDao;

    public Result addArticleVideo(ArticleVideoDTO articleVideoDTO) {
        try {
            int res = articleDao.addArticleVideo(articleVideoDTO);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("addArticleVideo error", e);
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<List<ArticleVideoDTO>> listArticleVideo() {
        try {
            List<ArticleVideoDTO> videoDTOS = articleDao.listArticleVideo();
            return Result.success(videoDTOS);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

}
