package com.javalemon.stone.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lemon
 * @date 2019-10-27
 * @desc
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVideoDTO {

    private int id;
    private int videoId;
    private String videoImg;
    private String title;
    private String mdContent;
    private String content;
    private String desc;
    private Date displayTime;
    private String importance;
    private int likeNum;
    private int viewNum;
}
