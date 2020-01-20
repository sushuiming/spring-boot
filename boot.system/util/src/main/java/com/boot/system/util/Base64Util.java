package com.boot.system.util;

import sun.misc.BASE64Encoder;

import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import sun.misc.BASE64Decoder;

/**
 * <pre>
 * 	base转码类。
 * </pre>
 *
 * @author sushuiming
 * @date 2019-10-28
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@SuppressWarnings("restriction")
public class Base64Util {

	
	/**
    *
    * <pre>
    *      本地图片转换成base64字符串
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param content 字符串内容
    * @return result base64字符串
    * @throws IOException
    */
    public static String imageToBase64(String imgFile) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    	String result = null; // 返回Base64编码过的字节数组字符串
        InputStream in = null;
        byte[] data = null; // 读取图片字节数组       
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            result = new BASE64Encoder().encode(data);  // 对字节数组Base64编码
        } catch (IOException e) {
            throw new IOException("本地图片转换成base64字符串异常：" + e.getMessage());
        }  
        return result; 
    }

    /**
    *
    * <pre>
    *      在线图片转换成base64字符串
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param imgURL	图片线上路径
    * @return result base64字符串
    * @throws IOException
    */
    public static String onlineImageToBase64(String imgURL) throws IOException {
    	String result = null;
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {           
            URL url = new URL(imgURL); // 创建URL
            byte[] by = new byte[1024];           
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 创建链接
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            is.close();
            result = new BASE64Encoder().encode(data.toByteArray());  // 对字节数组Base64编码
        } catch (IOException e) {
        	throw new IOException("在线图片转换成base64字符串异常：" + e.getMessage());
        }
       
        return result;
    }

    /**
    *
    * <pre>
    *      base64字符串转换成本地图片
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param imgStr		base64字符串
    * @param imgFilePath	图片存放路径
    * @return boolean base64字符串
    * @throws IOException 
    */
    public static boolean base64ToImage(String base64Str, String imgFilePath) throws IOException { 
    	boolean result = false;
        if (StringUtils.isEmpty(base64Str)) { // 图像数据为空
        	return result;
        }
                    
        try {
        	BASE64Decoder decoder = new BASE64Decoder();            
            byte[] b = decoder.decodeBuffer(base64Str); // Base64解码
            for (int i = 0; i < b.length; ++i) { 
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 对字节数组字符串进行Base64解码并生成图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            result = true;
        } catch (IOException e) {
        	throw new IOException("base64字符串转换成本地图片异常：" + e.getMessage());
        }
        return result;
    }
	
}
