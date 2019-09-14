package com.javalemon.stone.service;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.dao.GroupTagDao;
import com.javalemon.guide.model.dto.GroupTagDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Service
public class GroupTagService {

    @Resource
    private GroupTagDao groupTagDao;

    public Result addGroupTag(GroupTagDTO groupTagDTO) {
        try {
            int res = groupTagDao.addGroupTag(groupTagDTO);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<List<GroupTagDTO>> listGroupTag(int userId, int groupId) {
        try {
            List<GroupTagDTO> groupTagDTOS = groupTagDao.listGroupTag(userId, groupId);
            return Result.success(groupTagDTOS);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result deleteGroupTag(int groupTagId) {
        try {
            int res = groupTagDao.deleteGroupTag(groupTagId);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result updateGroupTag(int tagId, String tagName, String tagLink, int sort) {
        try {
            int res = groupTagDao.updateGroupTag(tagId, tagName, tagLink, sort);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<GroupTagDTO> getTag(int tagId) {
        try {
            GroupTagDTO groupTagDTO = groupTagDao.getTag(tagId);
            return Result.success(groupTagDTO);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }
}
