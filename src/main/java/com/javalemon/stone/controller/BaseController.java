package com.javalemon.stone.controller;

import com.javalemon.guide.common.utils.web.CookieUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lemon
 * @date 2019-07-25
 * @desc
 */

public class BaseController {

    public int getUserId(HttpServletRequest request) {
        String userInfo = CookieUtils.getCookieByName(request, "userInfo");
        return NumberUtils.toInt(userInfo);
    }
}
