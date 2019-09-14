package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.common.utils.md5.MD5Object;
import com.javalemon.stone.common.utils.web.CookieUtils;
import com.javalemon.stone.model.dto.UserDTO;
import com.javalemon.stone.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Controller
@RequestMapping("user")
public class UserController extends BaseController{

    @Resource
    private UserService userService;

    @GetMapping("showUser")
    public String showUser(HttpServletRequest request, Model model) {
        int userId = getUserId(request);
        model.addAttribute("userId", userId);
        Result<UserDTO> userInfo = userService.getUser(userId);
        if (userInfo.isSuccess()) {
            model.addAttribute("userInfo", userInfo.getData());
        }
        return "user";
    }

    @PostMapping("login")
    @ResponseBody
    public Result login(HttpServletRequest request, HttpServletResponse response) {

        String name = StringUtils.trimToEmpty(request.getParameter("name"));
        String email = StringUtils.trimToEmpty(request.getParameter("email"));
        String password = StringUtils.trimToEmpty(request.getParameter("password"));
        MD5Object md5 = new MD5Object();
        String passwordData = md5.SHASum(md5.MD5Sum(password).toLowerCase()).toUpperCase();

        Result<UserDTO> userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isSuccess() && userByEmail.getData() != null) {
            if (!StringUtils.equals(passwordData, userByEmail.getData().getPassword())) {
                return Result.error(Result.CodeEnum.NO_GROUP_ERROR);
            } else {
                CookieUtils.writeCookie(response, "userInfo", userByEmail.getData().getId() + "");
                return Result.success();
            }
        }


        Result result = userService.addUser(UserDTO.builder()
                .createTime(new Date())
                .name(name)
                .email(email)
                .password(passwordData)
                .build()
        );

        if (result.isSuccess()) {
            CookieUtils.writeCookie(response, "userInfo", result.getData().toString());
        }

        return result;
    }

    @PostMapping("logout")
    @ResponseBody
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.removeCookie(response, "userInfo");
        return Result.success();
    }

}
