package com.javalemon.stone.dao;

import com.javalemon.stone.dao.mapper.UserMapper;
import com.javalemon.stone.model.dto.UserDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 消息
 */
@Repository
public class UserDao {
    @Resource
    private UserMapper userMapper;

    public int addUser(UserDTO userDTO) {
        return userMapper.addUser(userDTO);
    }

    public UserDTO getUser(int userId) {
        return userMapper.getUser(userId);
    }

    public UserDTO getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public UserDTO getUserByOpenId(String openId) {
        return userMapper.getUserByOpenId(openId);
    }

    public UserDTO getUserByToken(String token) {
        return userMapper.getUserByToken(token);
    }

    public int updateUserInfo(UserDTO userDTO) {
        return userMapper.updateUserInfo(userDTO);
    }
}
