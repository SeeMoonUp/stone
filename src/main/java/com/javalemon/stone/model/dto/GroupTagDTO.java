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
public class GroupTagDTO {
    private int id;
    private int userId;
    private int groupId;
    private String groupName;
    private String tagName;
    private String tagLink;
    private int sort;
    private int status;
    private Date createTime;
}
