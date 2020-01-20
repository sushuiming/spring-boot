package com.boot.system.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtil {

	/**
	 * 使用GET方式，获取数据
	 * 
	 * @author ssm
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param url
	 * @return json字符串
	 * @throws Exception 
	 */
	public static String doGet(String url) throws IOException {
		String result = "";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			// 通过址默认配置创建一个httpClient实例
			httpClient = HttpClients.createDefault();
			// 创建httpGet远程连接实例
			HttpGet httpGet = new HttpGet(url);
			// 设置请求头信息，鉴权
			httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
			// 设置配置请求参数
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(35000)// 连接主机服务超时时间
					.setConnectionRequestTimeout(35000)// 请求超时时间
					.setSocketTimeout(60000)// 数据读取超时时间
					.build();
			// 为httpGet实例设置配置
			httpGet.setConfig(requestConfig);
			
			// 执行get请求得到返回对象
			response = httpClient.execute(httpGet);
			// 通过返回对象获取返回数据
			HttpEntity entity = response.getEntity();
			// 通过EntityUtils中的toString方法将结果转换为字符串
			result = EntityUtils.toString(entity, Charset.forName("utf-8"));
		} catch (ClientProtocolException e) {
			throw new ClientProtocolException("创建GET连接失败：" + e.getMessage());
		} catch (IOException e) {
			throw new IOException("GET返回数据失败:" + e.getMessage());
		} finally {
			// 关闭资源
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 使用POST方式，获取数据
	 * 
	 * @author ssm
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param url
	 * @param map 附带参数
	 * @return json字符串
	 * @throws Exception 
	 */
	public static String doPost(String url, Map<String, Object> map) throws IOException {
		String result = "";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;		
		try {
			// 创建httpClient实例
			httpClient = HttpClients.createDefault();
			// 创建httpPost远程连接实例
			HttpPost httpPost = new HttpPost(url);
			// 配置请求参数实例
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(35000)// 设置连接主机服务超时时间
					.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
					.setSocketTimeout(60000)// 设置读取数据连接超时时间
					.build();
			// 为httpPost实例设置配置
			httpPost.setConfig(requestConfig);
			// 设置请求头
			httpPost.addHeader("Content-Type", "application/json");			
			// 封装post请求参数
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(map);
			httpPost.setEntity(new StringEntity(jsonStr, Charset.forName("utf-8")));
			// httpClient对象执行post请求,并返回响应参数对象
			httpResponse = httpClient.execute(httpPost);
			// 从响应对象中获取响应内容
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity, Charset.forName("utf-8"));
		} catch (ClientProtocolException e) {
			throw new ClientProtocolException("创建POST连接失败：" + e.getMessage());
		} catch (IOException e) {
			throw new IOException("POST获取数据失败" + e.getMessage());
		} finally {
			// 关闭资源
			if (null != httpResponse) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}		

	/**
	 * 使用上传文件
	 * 
	 * @author ssm
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param url
	 * @param file 上传的文件
	 * @return json字符串
	 * @throws ClientProtocolException 
	 */
	public static String uploadImage(String url, File file) throws IOException {
		String result = "";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;	
		try {			
			// 创建httpClient实例
			httpClient = HttpClients.createDefault();
			// 创建httpPost远程连接实例
			HttpPost httpPost = new HttpPost(url);
			// 配置请求参数实例
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(35000)// 设置连接主机服务超时时间
					.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
					.setSocketTimeout(60000)// 设置读取数据连接超时时间
					.build();
			// 为httpPost实例设置配置
			httpPost.setConfig(requestConfig);
			// 封装post请求参数
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.addBinaryBody("fileName", file);
			multipartEntityBuilder.addTextBody("comment", "this is comment");
			HttpEntity httpEntity = multipartEntityBuilder.build();
			httpPost.setEntity(httpEntity);
			// httpClient对象执行post请求,并返回响应参数对象
			httpResponse = httpClient.execute(httpPost);
			// 从响应对象中获取响应内容
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			throw new ClientProtocolException("创建连接失败：" + e.getMessage());
		} catch (IOException e) {
			throw new IOException("获取数据失败" + e.getMessage());
		} finally {
			// 关闭资源
			if (null != httpResponse) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}