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
public class HomePageVO implements Serializable{
    private static final long serialVersionUID = -8051160031095890327L;

    private List<ArticleVideoVO> videos;

    private int lastVideoIndex;
}
