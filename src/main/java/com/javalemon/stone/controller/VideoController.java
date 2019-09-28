package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.model.dto.VideoDTO;
import com.javalemon.stone.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("video")
public class VideoController {

    @Resource
    VideoService videoService;

    @PostMapping("save")
    @ResponseBody
    public Result save(HttpServletRequest request,  @RequestParam String qiniuKey,  @RequestParam String title) {
        if (StringUtils.isBlank(qiniuKey) || StringUtils.isBlank(title)) {
            return Result.error(Result.CodeEnum.PARAM_ERROR);
        }
        VideoDTO videoDTO = VideoDTO.builder().qiniuKey(qiniuKey).title(title).build();
        Result result= videoService.addVideo(videoDTO);
        if (result.isSuccess()) {
            return Result.success(Result.CodeEnum.SUCCESS);
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }

    @PostMapping("list")
    @ResponseBody
    public Result list(HttpServletRequest request) {

        Result<List<VideoDTO>> videosRes = videoService.listVideo();
        if (videosRes.isSuccess()) {
            return Result.success(videosRes.getData());
        }

        return Result.error(Result.CodeEnum.SERVICE_ERROR);
    }
}
