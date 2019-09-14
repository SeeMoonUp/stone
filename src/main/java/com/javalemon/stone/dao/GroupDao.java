package com.javalemon.stone.dao;

import com.javalemon.guide.dao.mapper.GroupMapper;
import com.javalemon.guide.model.dto.GroupDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息
 */
@Repository
public class GroupDao {
    @Resource
    private GroupMapper groupMapper;

    public int addGroup(GroupDTO groupDTO) {
        return groupMapper.addGroup(groupDTO);
    }

    public List<GroupDTO> listGroup(int userId) {
        return groupMapper.listGroup(userId);
    }

    public int deleteGroup(int groupId) {
        return groupMapper.deleteGroup(groupId);
    }

    public GroupDTO getGroup(int groupId) {
        return groupMapper.getGroup(groupId);
    }

    public int updateGroup(int groupId, String groupName, int sort) {
        return groupMapper.updateGroup(groupId, groupName, sort);
    }
}
