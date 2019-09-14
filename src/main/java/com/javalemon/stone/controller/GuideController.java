package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.model.dto.GroupDTO;
import com.javalemon.stone.model.dto.GroupTagDTO;
import com.javalemon.stone.model.vo.GroupTagInfoVO;
import com.javalemon.stone.model.vo.GroupTagVO;
import com.javalemon.stone.service.GroupService;
import com.javalemon.stone.service.GroupTagService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-13
 * @desc
 */

@Controller
@Configuration
@Data
public class GuideController extends BaseController{

    @Resource
    GroupService groupService;

    @Resource
    GroupTagService groupTagService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("userInfo", port);
        int userId = getUserId(request);

        Result<List<GroupDTO>> groupList = groupService.listGroup(userId);
        if (!groupList.isSuccess() || CollectionUtils.isEmpty(groupList.getData())) {
            return "guide";
        }

        List<GroupTagInfoVO> tagInfoVOs = new ArrayList<>();
        for (GroupDTO groupDTO : groupList.getData()) {

            GroupTagInfoVO groupTagInfoVO = new GroupTagInfoVO();
            groupTagInfoVO.setGroupId(groupDTO.getId());
            groupTagInfoVO.setGroupName(groupDTO.getGroupName());

            Result<List<GroupTagDTO>> tagListResult = groupTagService.listGroupTag(userId, groupDTO.getId());
            if (!tagListResult.isSuccess()) {
                continue;
            }

            List<GroupTagVO> groupTagVOS = new ArrayList<>();
            if (!CollectionUtils.isEmpty(tagListResult.getData())) {
                for (GroupTagDTO groupTagDTO : tagListResult.getData()) {
                    groupTagVOS.add(GroupTagVO.builder().groupTagId(groupTagDTO.getId())
                            .groupTagName(groupTagDTO.getTagName())
                            .groupTagLink(groupTagDTO.getTagLink()).build()
                    );
                }
            }


            groupTagInfoVO.setGroupTagVOList(groupTagVOS);
            tagInfoVOs.add(groupTagInfoVO);
        }
        model.addAttribute("tagInfoVOs", tagInfoVOs);

        return "guide";
    }

    @GetMapping("/guide")
    public String guide(Model model) {

        return "guide";
    }

}
