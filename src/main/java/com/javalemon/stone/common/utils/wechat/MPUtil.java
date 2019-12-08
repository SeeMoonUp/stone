package com.javalemon.stone.common.utils.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.javalemon.stone.common.constant.CoreConstant;
import com.javalemon.stone.common.utils.web.URLConnUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengbangqi on 2018/5/22.
 * Date: 2018/5/22 11:11
 * Description:
 */
public class MPUtil {

    public static final Log BIT_LOG = LogFactory.getLog(MPUtil.class.getName());


    public static Map getSessionKeyMap(String sessionCode) {
        return getSessionKeyMapWithAppId(CoreConstant.BIT_MP_APPID, CoreConstant.BIT_MP_SECRET, sessionCode);
    }

    private static Map getSessionKeyMapWithAppId(String appId, String secret,String sessionCode) {
        if (StringUtils.isBlank(sessionCode)) {
            return new HashMap();
        }

        try {
            String url = MessageFormat.format(CoreConstant.WECHAT_PROGRAM_LOGIN_URL,
                    appId,
                    secret, sessionCode);
            String response = URLConnUtil.doHttpsGet(url, 15000, 15000);
            BIT_LOG.info("getSessionKey res : " + response);

            JSONObject jsonObject = JSON.parseObject(response);
            return jsonObject;

        } catch (Exception e) {
            BIT_LOG.error("getSessionKeyMapWithAppId Exception,sessionCode:"+sessionCode,e);
            return new HashMap();
        }


    }
}
