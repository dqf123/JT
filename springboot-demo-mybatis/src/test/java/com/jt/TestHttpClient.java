package com.jt;
/**
 * 调用步骤:
 * 	1.确定url的访问地址.
 *  2.确定请求的方式类型 get/post
 *  3.实例化httpClient对象.
 *  4.发起请求. 获取响应response.
 *  5.判断程序调用是否正确  200 302 400参数异常 406 参数转化异常
 *  	404 500 502 504 访问超时...
 *  6.获取返回值数据一般都是String.   JSON
 * @throws IOException 
 * @throws ClientProtocolException 
 */

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

	@Test
	public void testGet() throws ClientProtocolException, IOException {
		String url="https://www.baidu.com";
		HttpGet get = new HttpGet(url);
		HttpClient htClient =HttpClients.createDefault();
		HttpResponse response = htClient.execute(get);
		//获取状态码信息
		if(200==response.getStatusLine().getStatusCode()) {
			String result = EntityUtils.toString(response.getEntity(), "utf-8");
			System.err.println(result);
		}
	}
}
