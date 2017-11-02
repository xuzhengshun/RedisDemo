package com.xzs.imgTest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 * 
 * @Description:TODO
 * @exception:
 * @author: 徐正顺
 * @time:2017年10月16日 上午10:24:37
 */
public class CookieUtil {
	// private static final Logger _LOGGER =
	// LoggerFactory.getLogger(CookieUtil.class);
	private static final String DOMAIN = "";

	// 图片验证码 cookiename
	public static final String IMGCHECKNAME = "imgCode";

	/**
	 * 设置cookie
	 * 
	 * @Description:TODO
	 * @param name
	 * @param value
	 * @param domain
	 * @param expiry
	 * @param isHttpOnly
	 * @param response
	 *            void
	 */
	public static void setCookie(String name, String value, String domain, int expiry, boolean isHttpOnly,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		// cookie.setDomain(domain);
		cookie.setMaxAge(expiry);
		cookie.setPath("/");
		// cookie.setHttpOnly(isHttpOnly);
		response.addCookie(cookie);
	}

	/**
	 * 设置cookie
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param expiry
	 * @param response
	 */
	public static void setCookie(String name, String value, String domain, int expiry, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		// cookie.setDomain(domain);
		cookie.setMaxAge(expiry);
		cookie.setPath("/");
		response.addCookie(cookie);

	}

	/**
	 * cookie永不过期
	 * 
	 * @param name
	 * @param value
	 * @param response
	 */
	public static void setCookie(String name, String value, HttpServletResponse response) {
		setCookie(name, value, DOMAIN, -1, response);
	}

	/**
	 * 设置过期
	 * 
	 * @param name
	 * @param value
	 * @param expiry
	 * @param response
	 */
	public static void setCookie(String name, String value, int expiry, HttpServletResponse response) {
		setCookie(name, value, DOMAIN, expiry, response);
	}

	/**
	 * 设置域名
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param response
	 */
	public static void setCookie(String name, String value, String domain, HttpServletResponse response) {
		setCookie(name, value, domain, -1, response);
	}

	/**
	 * 返回cookie给客户端
	 * 
	 * @param cookie
	 * @param response
	 */
	public static void setCookie(Cookie cookie, HttpServletResponse response) {
		response.addCookie(cookie);
	}

	/**
	 * 获取全部cookie
	 * 
	 * @param name
	 * @param request
	 * @return
	 */
	public static Cookie getCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	/**
	 * 获取cookie值
	 * 
	 * @param name
	 * @param request
	 * @return
	 */
	public static String getValue(String name, HttpServletRequest request) {
		Cookie ck = getCookie(name, request);
		return null != ck ? ck.getValue() : null;
	}

	/**
	 * 清除所有cookie
	 * 
	 * @param request
	 * @param response
	 */
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				// cookie.setDomain(DOMAIN);
				response.addCookie(cookie);
			}
		}
	}

	/**
	 * 添加cookie
	 * 
	 * @param name
	 *            cookie的key
	 * @param value
	 *            cookie的value
	 * @param domain
	 *            domain
	 * @param path
	 *            path
	 * @param maxage
	 *            最长存活时间 单位为秒
	 * @param response
	 */
	public static void addCookie(String name, String value, String domain, int maxage, String path,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setMaxAge(maxage);
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	/**
	 * 往根下面存一个cookie * @param name cookie的key
	 * 
	 * @param value
	 *            cookie的value
	 * @param domain
	 *            domain
	 * @param maxage
	 *            最长存活时间 单位为秒
	 * @param response
	 */
	public static void addCookie(String name, String value, String domain, int maxage, HttpServletResponse response) {
		addCookie(name, value, domain, maxage, "/", response);
	}

	/**
	 * 从cookie值返回cookie值，如果没有返回 null
	 * 
	 * @param req
	 * @param name
	 * @return cookie的值
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	public static void removeCookie(String name, String domain, HttpServletRequest request,
			HttpServletResponse response) {
		String cookieVal = getCookie(request, name);
		if (cookieVal != null) {
			CookieUtil.addCookie(name, null, domain, 0, response);
		}
	}

	public static void removeCookie(String name, HttpServletRequest request, HttpServletResponse response) {
		CookieUtil.removeCookie(name, ".1010bao.com", request, response);
	}
}