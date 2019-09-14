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
public class MessageDTO {
    private int id;
    private String content;
    private int sendUserId;
    private String sendUserName;
    private int receiveUserId;
    private Date createTime;
}
