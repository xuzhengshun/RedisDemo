package com.xzs.imgTest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xzs.redis.service.CacheService;



/**
 * 图片验证码controller
 * 
 * @Description:TODO
 * @exception:
 * @author: 徐正顺
 * @time:2017年10月16日 上午10:38:44
 */
@RestController
@RequestMapping(value = "/imgVerifi", produces = "application/json")

public class VerifiCodeController {
	
	@Autowired
	private CacheService redisService;

	
	/**
	 * 获取图片验证码
	 * @Description:TODO
	 * @param digit
	 * @param width
	 * @param height
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年10月16日 上午11:14:41
	 */
	
	@GetMapping(value = "/img")
	public String imageCode(@RequestParam(defaultValue = "4")int digit,@RequestParam(defaultValue = "184")int width,@RequestParam(defaultValue = "70")int height,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String verifyCode = VerifyCodeUtils.generateVerifyCode(digit);
		
		OutputStream os = response.getOutputStream();
		Map<String, Object> map = VerifyCodeUtils.outputImage(width, height, os, verifyCode);
		VerifiCodeUtil.codeStorage(verifyCode,redisService, response);
	
		
		try {
			ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
		} catch (IOException e) {
			return "获取验证码异常，请刷新！！！";
		}
		return null;
	}
	
	@GetMapping(value = "/verifiCode")
	public Result<Boolean>  imgVrifi(@RequestParam String verifiCode,HttpServletRequest request, HttpServletResponse response){
		Result<Boolean> result = ResultGenerator.genSuccessResult();
	 
		
		return VerifiCodeUtil.checkCode(redisService,result,verifiCode, request);
		
	}
	
}
