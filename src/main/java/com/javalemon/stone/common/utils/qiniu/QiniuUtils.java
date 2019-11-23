package com.javalemon.stone.common.utils.qiniu;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

public class QiniuUtils {

    private final static String qiniuDomain = "http://img.bitxueyuan.com";

    public static String getVideoUrl(String qiniukey) {
        return qiniuDomain +"/" + qiniukey;
    }
}
