package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.common.utils.qiniu.QiniuUtils;
import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.dto.VideoDTO;
import com.javalemon.stone.model.param.HomePageParam;
import com.javalemon.stone.model.param.Page;
import com.javalemon.stone.model.param.PostRequest;
import com.javalemon.stone.model.param.VideoDetailParam;
import com.javalemon.stone.model.vo.article.ArticleVideoListVO;
import com.javalemon.stone.model.vo.bit.ArticleDetailVO;
import com.javalemon.stone.model.vo.bit.ArticleVideoVO;
import com.javalemon.stone.model.vo.bit.HomePageVO;
import com.javalemon.stone.model.vo.bit.WeekHotVO;
import com.javalemon.stone.service.ArticleService;
import com.javalemon.stone.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.expression.Lists;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-13
 * @desc
 */

@Controller
@Configuration
@RequestMapping("bit")
public class BitController extends BaseController {

    public static final String authorImage = "http://img.bitxueyuan.com/ab4a07ee-2802-4fe1-9bae-e234e66384f2";

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ArticleService.class.getName());


    @Resource
    ArticleService articleService;

    @Resource
    VideoService videoService;

    private final static int homePageNum = 10;

    @RequestMapping("home")
    @ResponseBody
    public Result home(HttpServletRequest request, @RequestBody PostRequest<HomePageParam> postRequest) {
        int lastVideoIndex = postRequest.getParam().getLastVideoIndex();

        Result<Integer> articleVideoCount = articleService.countArticleVideo();
        if (!articleVideoCount.isSuccess()) {
            LOGGER.error("countArticleVideo error");
            Result.error(Result.CodeEnum.SERVICE_ERROR);
        }

        Integer allCount = articleVideoCount.getData();
        if (lastVideoIndex >= allCount) {
            LOGGER.error("param lastVideoIndex error");
            Result.error(Result.CodeEnum.PARAM_ERROR);
        }

        Result<List<ArticleVideoDTO>> videosRes = articleService.listPageArticleVideo(
                Page.builder().start(lastVideoIndex).limit(homePageNum).build()
        );


        if (videosRes.isSuccess()) {
            List<ArticleVideoDTO> articleVideoDTOS = videosRes.getData();
            if (CollectionUtils.isEmpty(articleVideoDTOS)) {
                return Result.success();
            }

            List<ArticleVideoVO> articleVideoVOS = new ArrayList<>();
            for (ArticleVideoDTO articleVideoDTO : articleVideoDTOS) {

                Result<VideoDTO> videoRes = videoService.getVideoById(articleVideoDTO.getVideoId());
                if (!videoRes.isSuccess() || videoRes.getData() == null) {
                    continue;
                }

                ArticleVideoVO articleVideoVO = buildArticleVideoVO(articleVideoDTO, videoRes.getData());

                articleVideoVOS.add(articleVideoVO);

            }

            int endIndex = lastVideoIndex + homePageNum;
            if (endIndex >= allCount) {
                endIndex = -1;
            }

            HomePageVO data = HomePageVO.builder().videos(articleVideoVOS).lastVideoIndex(endIndex).build();

            return Result.success(data);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);

    }

    @RequestMapping("detail")
    @ResponseBody
    public Result detail(HttpServletRequest request, @RequestBody PostRequest<VideoDetailParam> postRequest) {

        int articleId = postRequest.getParam().getArticleVideoId();

        Result<ArticleVideoDTO> result = articleService.getArticleDetail(articleId);


        if (result.isSuccess()) {
            ArticleVideoDTO articleVideoDTO = result.getData();


            Result<VideoDTO> videoRes = videoService.getVideoById(articleVideoDTO.getVideoId());
            if (!videoRes.isSuccess() || videoRes.getData() == null) {
                return Result.error(Result.CodeEnum.SERVICE_ERROR);
            }

            ArticleVideoVO articleVideoVO = buildArticleVideoVO(articleVideoDTO, videoRes.getData());

            List<ArticleVideoVO> relationVideos = getRelationVideos(articleVideoDTO.getId());

            ArticleDetailVO data = ArticleDetailVO.builder().detailInfo(articleVideoVO).relationVideos(relationVideos).build();

            return Result.success(data);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }

    private List<ArticleVideoVO> getRelationVideos(int articleVideoId) {
        Result<List<ArticleVideoDTO>> videosRes = articleService.listPageArticleVideo(
                Page.builder().start(0).limit(homePageNum).build()
        );
        if (videosRes.isSuccess()) {
            List<ArticleVideoDTO> articleVideoDTOS = videosRes.getData();
            if (CollectionUtils.isEmpty(articleVideoDTOS)) {
                return new ArrayList<>();
            }

            List<ArticleVideoVO> articleVideoVOS = new ArrayList<>();
            for (ArticleVideoDTO articleVideoDTO : articleVideoDTOS) {

                if (articleVideoDTO.getId() == articleVideoId) {
                    continue;
                }

                Result<VideoDTO> videoRes = videoService.getVideoById(articleVideoDTO.getVideoId());
                if (!videoRes.isSuccess() || videoRes.getData() == null) {
                    continue;
                }

                ArticleVideoVO articleVideoVO = buildArticleVideoVO(articleVideoDTO, videoRes.getData());

                articleVideoVOS.add(articleVideoVO);

            }

            return articleVideoVOS;
        }

        return new ArrayList<>();
    }

    private ArticleVideoVO buildArticleVideoVO(ArticleVideoDTO articleVideoDTO, VideoDTO videoDTO) {
        DateTime displayTime = new DateTime(articleVideoDTO.getDisplayTime());
        String showTime = displayTime.toString("yyyy-MM-dd HH:ss");

        ArticleVideoVO articleVideoVO = ArticleVideoVO.builder()
                .id(articleVideoDTO.getId())
                .videoId(articleVideoDTO.getVideoId())
                .videoUrl(QiniuUtils.getVideoUrl(videoDTO.getQiniuKey()))
                .title(articleVideoDTO.getTitle())
                .mdContent(articleVideoDTO.getMdContent())
                .content(articleVideoDTO.getContent())
                .desc(articleVideoDTO.getDesc())
                .displayTime(articleVideoDTO.getDisplayTime())
                .importance(articleVideoDTO.getImportance())
                .likeNum(articleVideoDTO.getLikeNum())
                .viewNum(articleVideoDTO.getViewNum())
                .authorImage(authorImage)
                .showTime(showTime)
                .author("比特er")
                .build();
        return articleVideoVO;
    }

}
