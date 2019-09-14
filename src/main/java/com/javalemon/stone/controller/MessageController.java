package com.javalemon.stone.controller;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.model.MessageVO;
import com.javalemon.guide.model.dto.MessageDTO;
import com.javalemon.guide.service.MessageService;
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
@RequestMapping("message")
public class MessageController extends BaseController{

    @Resource
    private MessageService messageService;

    @GetMapping("showMessage")
    public String showMessage(HttpServletRequest request, Model model) {
        int userId = getUserId(request);
        Result<List<MessageVO>> listResult = messageService.listMessage(userId);
        if (listResult.isSuccess()) {
            model.addAttribute("messageList", listResult.getData());
        }
        return "message";
    }

    @PostMapping("addMessage")
    @ResponseBody
    public Result addMessage(HttpServletRequest request, @RequestParam String content) {
        int userId = getUserId(request);
        if (userId <= 0) {
            return Result.error(Result.CodeEnum.NO_LOGIN);
        }

        Result result = messageService.addMessage(MessageDTO.builder()
                .content(content)
                .sendUserId(userId)
                .sendUserName("lemon")
                .receiveUserId(2)
                .createTime(new Date())
                .build());
        return result;
    }
}
