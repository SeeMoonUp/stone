package com.javalemon.stone.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@Data
public class CommonParams implements Serializable {
    private static final long serialVersionUID = 6808876610074160463L;

    private String token;

    private String deviceId;
}
