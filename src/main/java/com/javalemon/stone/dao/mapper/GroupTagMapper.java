package com.javalemon.stone.dao.mapper;

import com.javalemon.guide.model.dto.GroupTagDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface GroupTagMapper {

    int addGroupTag(GroupTagDTO groupTagDTO);

    List<GroupTagDTO> listGroupTag(
            @Param("userId") int userId,
            @Param("groupId") int groupId);

    int deleteGroupTag(int groupTagId);

    int updateGroupTag(
            @Param("tagId") int tagId,
            @Param("tagName") String tagName,
            @Param("tagLink") String tagLink,
            @Param("sort") int sort
    );

    GroupTagDTO getTag(int tagId);
}
