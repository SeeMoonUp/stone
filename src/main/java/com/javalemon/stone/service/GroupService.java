package com.javalemon.stone.service;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.dao.GroupDao;
import com.javalemon.guide.model.dto.GroupDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Service
public class GroupService {

    @Resource
    private GroupDao groupDao;

    public Result addGroup(GroupDTO groupDTO) {
        try {
            int res = groupDao.addGroup(groupDTO);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<List<GroupDTO>> listGroup(int userId) {
        try {
            List<GroupDTO> groupDTOS = groupDao.listGroup(userId);
            return Result.success(groupDTOS);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<GroupDTO> getGroup(int groupId) {
        try {
            GroupDTO groupDTO = groupDao.getGroup(groupId);
            return Result.success(groupDTO);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result deleteGroup(int groupId) {
        try {
            int res = groupDao.deleteGroup(groupId);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result updateGroup(int groupId, String groupName, int sort) {
        try {
            int res = groupDao.updateGroup(groupId, groupName, sort);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }
}
