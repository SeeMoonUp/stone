package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.common.utils.web.CookieUtils;
import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.vo.article.ArticleVideoListVO;
import com.javalemon.stone.service.ArticleService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lemon
 * @date 2019-09-14
 * @desc
 */

@Controller
@Configuration
@RequestMapping("article")
public class ArticleController {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ArticleController.class.getName());

    @Resource
    ArticleService articleService;

    @PostMapping("save")
    @ResponseBody
    public Result save(@RequestBody ArticleVideoDTO articleVideoDTO) {

        Result result = articleService.addArticleVideo(articleVideoDTO);
        if (result.isSuccess()) {
            return Result.success(Result.CodeEnum.SUCCESS);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }

    @PostMapping("list")
    @ResponseBody
    public Result list(HttpServletRequest request) {

        Result<List<ArticleVideoDTO>> videosRes = articleService.listArticleVideo();
        if (videosRes.isSuccess()) {
            ArticleVideoListVO data = ArticleVideoListVO.builder()
                    .items(videosRes.getData())
                    .total(videosRes.getData().size())
                    .build();
            return Result.success(data);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }

    @PostMapping("detail")
    @ResponseBody
    public Result detail(HttpServletRequest request) {

        String articleId = StringUtils.trimToEmpty(request.getParameter("articleId"));
        if (StringUtils.isBlank(articleId)) {
            return Result.error(Result.CodeEnum.PARAM_ERROR);
        }


        Result<ArticleVideoDTO> result = articleService.getArticleDetail(NumberUtils.toInt(articleId));

        if (result.isSuccess()) {
            return result;
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }
}
