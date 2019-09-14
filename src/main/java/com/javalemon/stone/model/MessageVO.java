package com.javalemon.stone.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Data
@Builder
public class MessageVO {
    private String content;

    private String date;

    private String sender;
}
