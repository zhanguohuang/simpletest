package org.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {

	public static void main(String[] args) {
		HttpClient httpClient = new DefaultHttpClient();
		String reqUrl = "http://localhost:8080/simpletest/getRequestParam?name=hello&password=world";
		HttpGet getMethod = new HttpGet(reqUrl);
		try {
			HttpResponse resp = httpClient.execute(getMethod);
			String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
			System.out.println(str);
		} catch(Exception e){
			;
		}
	}
}
