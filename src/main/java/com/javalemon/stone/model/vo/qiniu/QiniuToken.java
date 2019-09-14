package com.javalemon.stone.model.vo.qiniu;

import lombok.Builder;
import lombok.Data;

/**
 * @author lemon
 * @date 2019-09-14
 * @desc
 */

@Data
@Builder
public class QiniuToken {
    private String token;
    private String key;
}
