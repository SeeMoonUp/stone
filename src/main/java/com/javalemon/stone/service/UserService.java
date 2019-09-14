package com.javalemon.stone.service;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.dao.UserDao;
import com.javalemon.guide.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public Result addUser(UserDTO userDTO) {
        try {
            int res = userDao.addUser(userDTO);
            if (res >= 0) {
                return Result.success(userDTO.getId());
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<UserDTO> getUser(int userId) {
        try {
            UserDTO userDTO = userDao.getUser(userId);
            return Result.success(userDTO);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<UserDTO> getUserByEmail(String email) {
        try {
            UserDTO userDTO = userDao.getUserByEmail(email);
            return Result.success(userDTO);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }
}
