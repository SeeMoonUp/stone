package com.javalemon.stone.dao;

import com.javalemon.stone.dao.mapper.GroupMapper;
import com.javalemon.stone.dao.mapper.VideoMapper;
import com.javalemon.stone.model.dto.GroupDTO;
import com.javalemon.stone.model.dto.VideoDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息
 */
@Repository
public class VideoDao {

    @Resource
    private VideoMapper videoMapper;


    public List<VideoDTO> listVideo() {
        return videoMapper.listVideo();
    }


    public int addVideo(VideoDTO videoDTO) {
        return videoMapper.addVideo(videoDTO);
    }

}
