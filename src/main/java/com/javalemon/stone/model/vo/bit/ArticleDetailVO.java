package com.javalemon.stone.model.vo.bit;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@Data
@Builder
public class ArticleDetailVO implements Serializable {

    private static final long serialVersionUID = -5851260848815292872L;

    private ArticleVideoVO detailInfo;

    private List<ArticleVideoVO> relationVideos;
}
