package com.javalemon.stone.model.vo.article;

import com.javalemon.stone.model.dto.ArticleVideoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author lemon
 * @date 2019-10-19
 * @desc
 */

@Data
@Builder
public class ArticleVideoListVO {
    private List<ArticleVideoDTO> items;

    private Integer total;

}
