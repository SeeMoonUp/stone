package com.javalemon.stone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    private int id;
    private String qiniuKey;
    private String title;
    private Date createTime;
}
