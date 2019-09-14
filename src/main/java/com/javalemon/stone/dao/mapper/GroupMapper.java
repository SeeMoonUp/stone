package com.javalemon.stone.dao.mapper;

import com.javalemon.stone.model.dto.GroupDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface GroupMapper {

    int addGroup(GroupDTO groupDTO);

    List<GroupDTO> listGroup(int userId);

    int deleteGroup(int groupId);

    GroupDTO getGroup(int groupId);

    int updateGroup(
            @Param("groupId") int groupId,
            @Param("groupName") String groupName,
            @Param("sort") int sort
    );
}
