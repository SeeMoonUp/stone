package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.model.dto.VideoDTO;
import com.javalemon.stone.model.vo.video.VideoListVO;
import com.javalemon.stone.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lemon
 * @date 2019-09-14
 * @desc
 */

@Controller
@Configuration
@RequestMapping("article")
public class ArticleController {

    @Resource
    VideoService videoService;

    @PostMapping("save")
    @ResponseBody
    public Result save(HttpServletRequest request, @RequestBody Map<String, String> map) {

        if (true) {
            return Result.success(Result.CodeEnum.SUCCESS);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }

    @PostMapping("list")
    @ResponseBody
    public Result list(HttpServletRequest request) {

        Result<List<VideoDTO>> videosRes = videoService.listVideo();
        if (videosRes.isSuccess()) {
            VideoListVO data = VideoListVO.builder()
                    .items(videosRes.getData())
                    .total(videosRes.getData().size())
                    .build();
            return Result.success(data);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }
}
