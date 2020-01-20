package com.boot.system.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

/**
 * <pre>
 * 	发送手机短信工具。
 * </pre>
 *
 */
public class SendMessageUtil {

	// key
    private final static String KEY = "d7847d78c95e6f3de0569861546ff82b";
    
    // 发送短信url
    private final static String SEND_SMS_URL = "http://sms-api.luosimao.com/v1/send.json";
    
    // 短信状态余额获取url
    private final static String CHECK_SMS_URL = "http://sms-api.luosimao.com/v1/status.json";


    /**
	 * @功能: 发送短信
	 * @参数: mobile, message
	 * @返回: String
	 * @作者: ssm
	 * @返回值解析:
	   0			发送成功
	   -10			验证信息失败
	   -11			用户接口被禁用
	   -20			短信余额不足
	   -30			短信内容为空
	   -31			短信内容存在敏感词
	   -32			短信内容缺少签名信息
	   -33			短信过长，超过300字（含签名）
	   -34			签名不可用
	   -40			错误的手机号
	   -41			号码在黑名单中
	   -42			验证码类短信发送频率过快
	   -50			请求发送IP不在白名单内
	*/
    public static String sendSms(String mobile, String message) {   	    		
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "key-" + KEY));
        WebResource webResource = client.resource(SEND_SMS_URL);
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", mobile);
        formData.add("message", message);

        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
        		.post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
      
        String code = "";
        try {
            JSONObject jsonObj = new JSONObject(textEntity);
            code = jsonObj.getString("error");
        } catch (JSONException e) {        
            e.printStackTrace();
        }
        return code;
    }

    /**
	 * @功能: 短信状态余额获取
	 * @参数: 
	 * @返回: String
	 * @作者: ssm
	 *
	   0			发送成功
	   -10			验证信息失败
	*/	
    public static String statusSms() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "key-" + KEY));
        WebResource webResource = client.resource(CHECK_SMS_URL);       
        ClientResponse response = webResource.get(ClientResponse.class);
        String textEntity = response.getEntity(String.class);                 
        String deposit = "";
        try {
            JSONObject jsonObj = new JSONObject(textEntity);
            deposit = jsonObj.getString("deposit");
        } catch (JSONException e) {           
            e.printStackTrace();
        }
        return deposit;
    }      

}
