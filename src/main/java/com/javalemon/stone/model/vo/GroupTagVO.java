package com.javalemon.stone.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author lemon
 * @date 2019-07-18
 * @desc
 */

@Data
@Builder
public class GroupTagVO {
    private int groupTagId;
    private String groupTagName;
    private String groupTagLink;
}
