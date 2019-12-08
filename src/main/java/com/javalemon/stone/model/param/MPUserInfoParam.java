package com.javalemon.stone.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@Data
public class MPUserInfoParam implements Serializable {

    private static final long serialVersionUID = -1345860946966292759L;

    private String nickName;

    private String avatarUrl;
}
