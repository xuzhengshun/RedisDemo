package com.xzs.imgTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzs.redis.service.CacheService;
import com.xzs.utils.EncryptUtil;


/**
 * 验证码工具类
 * 
 * @Description:TODO
 * @exception:
 * @author: 徐正顺
 * @time:2017年11月2日 下午4:21:21
 */
public class VerifiCodeUtil {

	/**
	 * 图片验证码存储
	 * 
	 * @Description:TODO
	 * @param verifyCode
	 *            明文验证码
	 * @param response
	 *            void
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月2日 下午4:37:58
	 */
	public static void codeStorage(String verifyCode, CacheService redisService, HttpServletResponse response) {
		long time = 5*60; // 过期时间
		// 1：获取当前时间戳加盐加密 做key
		String redisKey = EncryptUtil.encrypt(getTimeStamp() , "SHA-256");
		// 2：验证码sha加盐加密 做value
		String redisValue = EncryptUtil.encrypt(verifyCode , "SHA-256");
		// 3 :存入redis中
		redisService.set(redisKey, redisValue, time);
		// 4：将key放入cooike中
		CookieUtil.addCookie(CookieUtil.IMGCHECKNAME, redisKey, null, (int) time, "/", response);

	}

	/**
	 * 校验验证码
	 * @Description:TODO
	 * @param redisService
	 * @param result
	 * @param verification
	 * @param request
	 * @return
	 * Result<Boolean>
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月2日 下午5:25:55
	 */
	public static Result<Boolean> checkCode(CacheService redisService, Result<Boolean> result, String verification,
			HttpServletRequest request) {
		// 1：用户输入转大写
		verification = verification.toUpperCase();
		// 2:用户输入做同等加密
		String value = EncryptUtil.encrypt(verification , "SHA-256");
		// 2:获取cookie中存放的key
		String redisKey = CookieUtil.getValue(CookieUtil.IMGCHECKNAME, request);

		if (redisKey == null) {// 达成此条件说明cookie已过期
			result.setData(false);
			result.setMessage("验证码已过期，重新刷新");
		} else {
			// 3: 根据key取出redis中的value
			Object redisValue = redisService.getValue(redisKey);
			if (redisValue == null) {// redis过期
				result.setData(false);
				result.setMessage("验证码已过期，重新刷新");
			} else {
				if (redisValue.equals(value)) {
					result.setData(true);
					result.setMessage("验证码输入正确");
				} else {
					result.setData(false);
					result.setMessage("验证码输入错误");
				}
			}
		}
		return result;
	}

	/**
	 * 获得当前时间戳
	 * 
	 * @Description:TODO
	 * @return long
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月2日 下午4:28:40
	 */
	public static String getTimeStamp() {
		return System.currentTimeMillis() + "";
	}

}
