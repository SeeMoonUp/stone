package com.javalemon.stone.controller;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.model.dto.GroupDTO;
import com.javalemon.guide.model.dto.GroupTagDTO;
import com.javalemon.guide.service.GroupService;
import com.javalemon.guide.service.GroupTagService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Controller
@RequestMapping("tag")
public class GroupTagController extends BaseController{

    @Resource
    private GroupTagService groupTagService;

    @Resource
    private GroupService groupService;

    @GetMapping("showTag")
    public String showTag(HttpServletRequest request, Model model) {
        int tagId = NumberUtils.toInt(request.getParameter("tagId"));
        Result<GroupTagDTO> groupTagDTOResult = groupTagService.getTag(tagId);
        if (groupTagDTOResult.isSuccess()) {
            model.addAttribute("tagInfo", groupTagDTOResult.getData());
            model.addAttribute("tagId", tagId);
        }

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        Result<GroupDTO> groupResult = groupService.getGroup(groupId);
        if (!groupResult.isSuccess()) {
            return "tag";
        }

        int userId = getUserId(request);
        Result<List<GroupDTO>> groupListResult = groupService.listGroup(userId);
        model.addAttribute("groupList", groupListResult.getData());
        model.addAttribute("groupId", groupId);

        return "tag";
    }

    @PostMapping("addTag")
    @ResponseBody
    public Result addTag(HttpServletRequest request) {
        int userId = getUserId(request);
        if (userId <= 0) {
            return Result.error(Result.CodeEnum.NO_LOGIN);
        }
        int sort = Integer.parseInt(request.getParameter("sort"));
        int groupId = NumberUtils.toInt(StringUtils.trimToEmpty(request.getParameter("groupId")));
        if (groupId == 0) {
            return Result.error(Result.CodeEnum.NO_GROUP_ERROR);
        }
        int tagId = NumberUtils.toInt(StringUtils.trimToEmpty(request.getParameter("tagId")));
        String tagName = request.getParameter("tagName");
        String tagLink = request.getParameter("tagLink");
        Result<GroupDTO> groupResult = groupService.getGroup(groupId);
        if (!groupResult.isSuccess()) {
            return Result.error(Result.CodeEnum.NO_GROUP_ERROR);
        }
        String groupName = groupResult.getData().getGroupName();

        if (tagId == 0) {

            Result result = groupTagService.addGroupTag(
                    GroupTagDTO.builder()
                            .userId(userId)
                            .sort(sort)
                            .groupId(groupId)
                            .groupName(groupName)
                            .tagName(tagName)
                            .tagLink(tagLink)
                            .status(1)
                            .createTime(new Date())
                            .build()
            );
            return result;
        } else {
            Result result = groupTagService.updateGroupTag(tagId, tagName, tagLink, sort);
            return result;
        }

    }

    @PostMapping("deleteTag")
    @ResponseBody
    public Result deleteTag(HttpServletRequest request) {
        int tagId = NumberUtils.toInt(request.getParameter("tagId"));
        if (tagId <= 0) {
            return Result.error(Result.CodeEnum.NO_TAG_ERROR);
        }

        Result result = groupTagService.deleteGroupTag(tagId);
        return result;
    }

    @PostMapping("updateTag")
    @ResponseBody
    public Result updateTag(HttpServletRequest request) {
        int tagId = Integer.valueOf(request.getParameter("tagId"));
        int sort = Integer.valueOf(request.getParameter("sort"));
        String tagName = request.getParameter("tagName");
        String tagLink = request.getParameter("tagLink");

        Result result = groupTagService.updateGroupTag(tagId, tagName, tagLink, sort);
        return result;
    }
}
