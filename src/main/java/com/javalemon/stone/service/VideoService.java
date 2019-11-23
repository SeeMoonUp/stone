package com.javalemon.stone.service;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.dao.GroupDao;
import com.javalemon.stone.dao.VideoDao;
import com.javalemon.stone.model.dto.GroupDTO;
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
public class VideoService {

    @Resource
    private VideoDao videoDao;

    public Result addVideo(VideoDTO videoDTO) {
        try {
            int res = videoDao.addVideo(videoDTO);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<List<VideoDTO>> listVideo() {
        try {
            List<VideoDTO> videoDTOS = videoDao.listVideo();
            return Result.success(videoDTOS);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<List<VideoDTO>> searchList(String keyword) {
        try {
            List<VideoDTO> videoDTOS = videoDao.searchList(keyword);
            return Result.success(videoDTOS);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<VideoDTO> getVideoById(int videoId) {
        try {
            VideoDTO videoDTO = videoDao.getVideoById(videoId);
            return Result.success(videoDTO);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }
}
