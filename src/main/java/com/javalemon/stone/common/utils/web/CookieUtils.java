/**
 * 
 */
package com.javalemon.stone.common.utils.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Fermi(fermi@youleyu.com)
 * @date Dec 8, 2013
 * @desc
 */
public class CookieUtils {
	private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(CookieUtils.class.getName());

	public static void writeCookie(final HttpServletResponse response,
			String name, String value) {
		Cookie cookie=null;
		try {
			cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("url转码出现异常", e);
			return ;
		}
		cookie.setPath("/");
		cookie.setDomain("javalemon.com");
		cookie.setMaxAge(365 * 24 * 60 * 60);
		response.addCookie(cookie);
	}

	public static void writeCookie(final HttpServletResponse response,
								   String domain, String name, String value) {
		Cookie cookie=null;
		try {
			cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("url转码出现异常", e);
			return ;
		}
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(365 * 24 * 60 * 60);
		response.addCookie(cookie);
	}
	
	public static void writeCookie(final HttpServletResponse response,
			String name, String value, int seconds) {
		Cookie cookie=null;
		try {
			cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("url转码出现异常", e);
			return ;
		}
		cookie.setPath("/");
		cookie.setDomain("javalemon.com");
		cookie.setMaxAge(seconds);//秒
		response.addCookie(cookie);
	}

	public static String getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			try {
				return  URLDecoder.decode(cookie.getValue(),"utf-8");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("url转码出现异常", e);
			}
			return null;
		} else {
			return null;
		}
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie userCookie = new Cookie(name, StringUtils.EMPTY);
		userCookie.setPath("/");
		userCookie.setDomain("javalemon.com");
		userCookie.setMaxAge(0);
		response.addCookie(userCookie);
	}

	public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			return cookieMap.get(name);
		} else {
			return null;
		}
	}

}
