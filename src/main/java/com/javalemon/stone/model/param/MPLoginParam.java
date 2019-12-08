package com.javalemon.stone.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@Data
public class MPLoginParam implements Serializable {

    private static final long serialVersionUID = 1294243121227128173L;
    private String code;
}
