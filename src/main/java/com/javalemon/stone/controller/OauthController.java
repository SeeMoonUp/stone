package com.javalemon.stone.controller;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.common.utils.wechat.MPUtil;
import com.javalemon.stone.model.dto.UserDTO;
import com.javalemon.stone.model.param.MPLoginParam;
import com.javalemon.stone.model.param.MPUserInfoParam;
import com.javalemon.stone.model.param.PostRequest;
import com.javalemon.stone.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lemon
 * @date 2019-12-08
 * @desc
 */

@Controller
@Configuration
@RequestMapping("oauth")
public class OauthController {

    public static final Log BIT_LOG = LogFactory.getLog(OauthController.class.getName());

    @Resource
    UserService userService;

    @PostMapping("login")
    @ResponseBody
    public Result login(@RequestBody PostRequest<MPLoginParam> postRequest) {
        String code = postRequest.getParam().getCode();
        if (StringUtils.isBlank(code)) {
            return Result.error(Result.CodeEnum.PARAM_ERROR);
        }

        Map keyMap = MPUtil.getSessionKeyMap(code);
        Integer errcode = (Integer) keyMap.get("errcode");
        if (errcode != 1) {
            BIT_LOG.error("login error result:" + keyMap);
            return Result.error(Result.CodeEnum.WECHAT_AUTH_ERROR);
        }

        String openid = (String) keyMap.get("openid");
        String sessionKey = (String) keyMap.get("session_key");
        String unionid = (String) keyMap.get("unionid");
        Result<UserDTO> userByOpenId = userService.getUserByOpenId(openid);
        if (userByOpenId.isSuccess()) {
            return Result.success(userByOpenId.getData().getAccessToken());
        }


        String accessToken = UUID.randomUUID().toString();
        UserDTO userDTO = UserDTO.builder()
                .name("")
                .email("")
                .password("")
                .accessToken(accessToken)
                .openId(openid)
                .unionId(unionid)
                .sessionKey(sessionKey)
                .createTime(new Date())
                .build();

        Result addUserResult = userService.addUser(userDTO);
        if (addUserResult.isSuccess()) {
            Map<String, String> map = new HashMap<>();
            map.put("token", accessToken);
            return Result.success(map);
        }

        return Result.error(Result.CodeEnum.MP_LOGIN_ERROR);
    }

    @PostMapping("userInfo")
    @ResponseBody
    public Result userInfo(@RequestBody PostRequest<MPUserInfoParam> postRequest) {

        String token = postRequest.getCommonParam().getToken();
        if (StringUtils.isBlank(token)) {
            return Result.error(Result.CodeEnum.PARAM_ERROR);
        }

        Result<UserDTO> userDTORes = userService.getUserByToken(token);
        if (!userDTORes.isSuccess()) {
            return Result.error(Result.CodeEnum.NO_USER_ERROR);
        }

        UserDTO userDTO = userDTORes.getData();
        userDTO.setNickName(postRequest.getParam().getNickName());
        userDTO.setAvatarUrl(postRequest.getParam().getAvatarUrl());

        Result result = userService.updateUserInfo(userDTO);
        return result;

    }


}
