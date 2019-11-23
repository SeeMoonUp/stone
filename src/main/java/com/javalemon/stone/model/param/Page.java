package com.javalemon.stone.model.param;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@Data
@Builder
public class Page implements Serializable {

    private static final long serialVersionUID = 3429998080623012902L;

    private int start;

    private int limit;

    private Map<String, Object> param;
}
