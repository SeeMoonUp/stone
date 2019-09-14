package com.javalemon.stone.controller;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.model.dto.GroupDTO;
import com.javalemon.guide.service.GroupService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lemon
 * @date 2019-07-19
 * @desc
 */

@Controller
@RequestMapping("group")
public class GroupController extends BaseController{

    @Resource
    private GroupService groupService;

    @GetMapping("showGroup")
    public String showTag(HttpServletRequest request, Model model) {
        int groupId = NumberUtils.toInt(request.getParameter("groupId"));
        model.addAttribute("groupId", groupId);
        Result<GroupDTO> groupResult = groupService.getGroup(groupId);
        if (!groupResult.isSuccess()) {
            return "group";
        }

        model.addAttribute("groupInfo", groupResult.getData());

        return "group";
    }

    @PostMapping("addGroup")
    @ResponseBody
    public Result addGroup(HttpServletRequest request, Model model) {
        int userId = getUserId(request);
        if (userId <= 0) {
            return Result.error(Result.CodeEnum.NO_LOGIN);
        }

        int groupId = NumberUtils.toInt(request.getParameter("groupId"));
        String groupName = StringUtils.trimToEmpty(request.getParameter("groupName"));
        int sort = NumberUtils.toInt(request.getParameter("sort"));

        if (groupId == 0) {
            Result result = groupService.addGroup(
                    GroupDTO.builder()
                            .userId(userId)
                            .sort(sort)
                            .groupName(groupName)
                            .status(1)
                            .createTime(new Date())
                            .build()
            );
            return result;
        } else {
            Result result = groupService.updateGroup(groupId, groupName, sort);
            return result;
        }

    }

    @PostMapping("deleteGroup")
    @ResponseBody
    public Result deleteGroup(HttpServletRequest request) {
        int groupId = NumberUtils.toInt(request.getParameter("groupId"));
        if (groupId <= 0) {
            return Result.error(Result.CodeEnum.NO_GROUP_ERROR);
        }

        Result result = groupService.deleteGroup(groupId);
        return result;
    }
}
