package com.xzs.imgTest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzs.utils.EncryptUtil;

@Controller
public class ImgController {

	@RequestMapping(value = "/images/imagecode")
	public String imagecode1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		OutputStream os = response.getOutputStream();
		Map<String, Object> map = VerifyCodeUtils.outputImage(184, 70, os, verifyCode);
		String shaCode = EncryptUtil.encrypt(verifyCode + "xzs", "SHA-256");
		System.err.println("原始验证码：" + verifyCode + "::::::::::::::加盐xzs使用SHA-256加密后的验证码：" + shaCode);
		//获取验证码之前先清空一次
		request.getSession().removeAttribute("imgCody");
		Object o = request.getSession().getAttribute("imgCody");
		if (o == null) {
			o = "spring boot 牛逼了!!!有端口" + request.getLocalPort() + "生成";
			System.err.println(o.toString() + "::::::::::" + shaCode);
			request.getSession().setAttribute("imgCody", shaCode);
		}

		try {
			ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
		} catch (IOException e) {
			return "";
		}
		return null;
	}

	@GetMapping(value = "/verifiCode")
	@ResponseBody
	public String imgVrifi(@RequestParam String verifiCode, HttpServletRequest request, HttpServletResponse response) {
		String o = (String) request.getSession().getAttribute("imgCody");
		System.err.println("获得的session为：" + o);
		String shaCode = EncryptUtil.encrypt(verifiCode + "xzs", "SHA-256");
		if (o == null) {
			return "验证码过期了，重新刷新";
		} else {
			if (o.equals(shaCode)) {
				return "对了，验证码为：" + verifiCode + "::::::session为：" + o;
			}
			return "验证码错了";
		}

	}

}
