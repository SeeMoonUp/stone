package com.javalemon.stone.model.vo.video;

import com.javalemon.stone.model.dto.VideoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author lemon
 * @date 2019-10-19
 * @desc
 */

@Data
@Builder
public class VideoListVO {
    private List<VideoDTO> items;

    private Integer total;

}
