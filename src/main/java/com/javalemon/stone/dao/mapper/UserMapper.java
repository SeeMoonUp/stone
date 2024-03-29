package com.javalemon.stone.dao.mapper;

import com.javalemon.stone.model.dto.UserDTO;

/**
 *
 */
public interface UserMapper {

    int addUser(UserDTO userDTO);

    UserDTO getUser(int userId);

    UserDTO getUserByEmail(String email);

    UserDTO getUserByOpenId(String openId);

    UserDTO getUserByToken(String token);

    int updateUserInfo(UserDTO userDTO);
}
