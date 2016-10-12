package org.controller;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.util.sign.SHASign;

/**
 * 基础测试控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("test")
public class BaseController{ 	
	
	Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * 验证服务器地址的有效性
	 * @param request
	 * @param response
	 */
	@RequestMapping("hello")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		logger.info("request-getquery====>" + request.getQueryString());
		Map<String, String> reqMap  = new TreeMap<String, String>();
		String[] reqStr = request.getQueryString().split("&");
		for(String reqParam : reqStr) {
			String[] keyVal = reqParam.split("=", 2);
			if(keyVal.length > 1) reqMap.put(keyVal[0], keyVal[1]);
		}
		logger.info("reqMap====>" + reqMap);
		Set<String> signSet = new TreeSet<String>();
		signSet.add("123456");				/*token*/
		signSet.add(reqMap.get("timestamp"));
		signSet.add(reqMap.get("nonce"));
		StringBuilder signStr = new StringBuilder();
		for(String key : signSet) {
			signStr.append(key);
		}
		logger.info("签名的参数signStr====>" + signStr);
		String signature = SHASign.SHA1(signStr.toString());
		logger.info("签名结果signature====>" + signature);
		if(reqMap.get("signature").equals(signature)) {
			logger.info("验签成功");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.write(reqMap.get("echostr"));
			} catch(Exception e) {
				logger.info("PrintWriter writer error : " + e);
			} finally {
				pw.close();
			}
		} else {
			logger.info("验签失败");
		}
	}

}