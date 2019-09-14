package com.javalemon.stone.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lemon
 * @date 2019-07-18
 * @desc
 */

@Data
public class GroupTagInfoVO {
    private int groupId;
    private String groupName;

    private List<GroupTagVO> groupTagVOList;
}
