package com.javalemon.stone.model.vo.bit;

import lombok.Builder;
import lombok.Data;

/**
 * @author lemon
 * @date 2019-09-06
 * @desc 每周热门精选
 */

@Data
@Builder
public class WeekHotVO {
    private String videoId;//视频唯一标志
    private String videoImg;//视频封面
    private String authorImg;//作者头像
    private String title;//标题
    private String desc;//内容描述
    private String videoDuration;//视频时长 服务端做格式处理
    private String author;//作者
    private Integer viewNum;//浏览次数
    private String publishTime;//发布时间
}
