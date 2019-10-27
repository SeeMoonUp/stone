package com.javalemon.stone.dao.mapper;

import com.javalemon.stone.model.dto.VideoDTO;

import java.util.List;

/**
 *
 */
public interface VideoMapper {

    int addVideo(VideoDTO videoDTO);

    List<VideoDTO> listVideo();

    List<VideoDTO> searchList(String keyword);
}
