package com.javalemon.stone.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@Data
public class HomePageParam implements Serializable {
    private static final long serialVersionUID = -4496825316022662243L;

    private int lastVideoIndex;
}
