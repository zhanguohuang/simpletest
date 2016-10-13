package org.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.util.sign.SHASign;

import com.alibaba.fastjson.JSONObject;

/**
 * 基础测试控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/test")
public class BaseController{ 	
	
	Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * 验证服务器地址的有效性
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hello")
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
	
	/**
	 * 获取code值，则为换取access_token的票据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCode")
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		logger.info("----获取用户的code开始----");
		String redirect_url = "https://open.weixin.qq.com/connect/oauth2/authorize?"  
					+ "appid=wx6221492b29f89319&redirect_uri=http%3a%2f%2f172.16.3.212%3a8080%2fsimpletest%2ftest%2fgetOpenId" 
					+ "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		try {
			response.sendRedirect(redirect_url);
		} catch (IOException e) {
			logger.info("跳转失败====>" + e);
		}
		logger.info("----获取用户的code结束----");
	}
	
	/**
	 * 获取用户的openId的方法
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getOpenId")
	public void getOpenId(HttpServletRequest request, HttpServletResponse response) {
		logger.info("----获取用户的openid开始----");
		logger.info("request.getQueryString()====>" + request.getQueryString());
		Map<String, String> reqMap  = new TreeMap<String, String>();
		String[] reqStr = request.getQueryString().split("&");
		for(String reqParam : reqStr) {
			String[] keyVal = reqParam.split("=", 2);
			if(keyVal.length > 1) reqMap.put(keyVal[0], keyVal[1]);
		}
		logger.info("reqMap====>" + reqMap);
		if(!reqMap.containsKey("code")) {
			logger.info("getOpenId error : code is null");
			return;
		}
		HttpClient httpClient = HttpClients.createDefault();
		String reqUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx6221492b29f89319"
				+ "&secret=35411638d7b9f0292b9705463481831f&code=" + reqMap.get("code") 
				+ "&grant_type=authorization_code";
		HttpGet getMethod = new HttpGet(reqUrl);
		try {
			HttpResponse resp = httpClient.execute(getMethod);
			String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
            int statusCode = resp.getStatusLine().getStatusCode();
            logger.info("获取的Entity : " + str);
            if (200 == statusCode) {
            	JSONObject jsonResp = JSONObject.parseObject(str);
            	logger.info("openid====>" + jsonResp.getString("openid"));
                logger.info("获取成功");
            }
		} catch (Exception e) {
			logger.info("getOpenId error http.execute exception : " + e);
		} 
		logger.info("----获取用户的openid结束----");
	}
	
	
	/**
	 * 基础类，获取请求的参数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getRequestParam")
	public void getRequestParam(HttpServletRequest request, HttpServletResponse response) {
		logger.info("----获取请求的参数开始----");
		logger.info("request.getQueryString()====>" + request.getQueryString());
		Enumeration<String> enumer = request.getParameterNames();
		while(enumer.hasMoreElements()) {
			logger.info("Element : " + enumer.nextElement() + "=" + request.getParameter(enumer.nextElement()));
		}
		ServletInputStream sin = null;
		try {
			sin = request.getInputStream();
			byte[] content = new byte[sin.available()];
			sin.read(content);
			logger.info("content : " + new String(content));
		} catch (IOException e) {
			logger.info("ServletInputStream error : " + e);
		}
		logger.info("----获取请求的参数结束----");
	}

}