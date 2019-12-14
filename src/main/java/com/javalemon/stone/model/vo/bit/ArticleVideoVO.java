package com.javalemon.stone.model.vo.bit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lemon
 * @date 2019-11-17
 * @desc
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVideoVO {

    private int id;
    private int videoId;
    private String videoUrl;
    private String videoImg;
    private String title;
    private String mdContent;
    private String content;
    private String desc;
    private Date displayTime;
    private String importance;
    private int likeNum;
    private int viewNum;
    private String authorImage;
    private String showTime;
    private String author;
}
