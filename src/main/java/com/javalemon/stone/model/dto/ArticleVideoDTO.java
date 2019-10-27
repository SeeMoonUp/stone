package com.javalemon.stone.model.dto;


import lombok.Data;

import java.util.Date;

/**
 * @author lemon
 * @date 2019-10-27
 * @desc
 */

@Data
public class ArticleVideoDTO {

    private int id;
    private int videoId;
    private String title;
    private String mdContent;
    private String content;
    private String desc;
    private Date displayTime;
    private String importance;
    private int likeNum;
    private int viewNum;
}
