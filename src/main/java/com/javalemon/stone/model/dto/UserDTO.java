package com.javalemon.stone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String nickName;
    private String avatarUrl;
    private String email;
    private String password;
    private String accessToken;
    private String sessionKey;
    private String unionId;
    private String openId;
    private Date createTime;
}
