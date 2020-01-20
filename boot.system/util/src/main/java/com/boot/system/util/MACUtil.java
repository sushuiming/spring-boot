package com.boot.system.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 	获取网卡MAC地址。
 * </pre>
 *
 * @author CHJY
 * @date 2019/9/27 17:08
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class MACUtil {

    /**
     * 执行单条指令
     * @param cmd 命令
     * @return 执行结果
     * @throws IOException 
     * @throws InterruptedException 
     * @throws Exception
     */

    public static String command(String cmd) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        StringBuilder result = new StringBuilder();
        byte[] data = new byte[256];
        while(in.read(data) != -1){
            String encoding = System.getProperty("sun.jnu.encoding");
            result.append(new String(data,encoding));
        }
        return result.toString();
    }

    /**
     * 获取MAC地址
     * @param ip 目标ip
     * @return  Mac Address
     * @throws InterruptedException 
     * @throws IOException 
     * @throws Exception
     */
    public static String getMacAddress(String ip) throws IOException, InterruptedException {
    	String localHostIp = getLocalHostIp();
    	if (localHostIp.equals(ip)) {  // 如果为本地IP， 直接返回本地MAC
    		return getMacAddress();
    	}
        String macAddress = "";
        String result = command("ping "+ip+" -n 2");
        if(result.contains("TTL")){
            result = command("arp -a "+ip);
        }
        String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(result);
        StringBuilder sb = new StringBuilder();
        while(matcher.find()) {
            String temp = matcher.group();
            sb.append(temp);
        }
        macAddress = sb.toString().trim().toUpperCase();
        return macAddress;
    }

    /**
     * 获取本机MAC地址
     * @return  Mac Address
     * @throws UnknownHostException 
     * @throws SocketException 
     * @throws Exception
     */
    public static String getMacAddress() throws UnknownHostException, SocketException {
        String macAddress = "";
        InetAddress inetAddress = InetAddress.getLocalHost();

        byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
                .getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        macAddress = sb.toString().trim().toUpperCase();
        return macAddress;
    }
    
    public static String getLocalHostIp() {
		String localHostIp = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            localHostIp = addr.getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
        	localHostIp = "127.0.0.1";
        }
        return localHostIp;
    }
    
}
