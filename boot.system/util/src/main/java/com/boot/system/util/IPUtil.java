/**
 * Copyright(c) Goldeye Science & Technology Ltd.
 */
package com.boot.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * <pre>
 * 	获取本地IP地址。
 * </pre>
 *
 * @author sushuiming
 * @date 2019-11-08
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class IPUtil {

    /**
     * 执行单条指令
     * @param cmd 命令
     * @return 执行结果
     * @throws Exception
     */
    public static String command(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        StringBuilder result = new StringBuilder();
        String encoding = System.getProperty("sun.jnu.encoding");
        byte[] data = new byte[256];
        while(in.read(data) != -1){          
            result.append(new String(data, encoding));
        }
        return result.toString();
    }

    /**
     * 获取本地IP
     * @param 
     * @return  localHostIp
     * @throws Exception
     */
    public static String getLocalHostIp() {
		String localHostIp = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            localHostIp = addr.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("获取本地IP失败：" + e.getMessage());
        	localHostIp = "127.0.0.1";
        }
        return localHostIp;
    }   
    
    /**
     * 获取本地子网掩码
     * @param 
     * @return  localHostIp
     * @throws UnknownHostException 
     * @throws SocketException 
     */
    public static String getNetMask() throws UnknownHostException, SocketException {   	
        InetAddress addr = InetAddress.getLocalHost(); 
        NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
        List<InterfaceAddress> list = ni.getInterfaceAddresses();
        if (list == null || list.isEmpty()) {
        	return null;
        }
        int mask = list.get(0).getNetworkPrefixLength(); //子网掩码的二进制1的个数
        StringBuilder maskStr = new StringBuilder();
        int[] maskIp = new int[4];
        for (int i = 0; i < maskIp.length; i++) {
            maskIp[i] = (mask >= 8) ? 255 : (mask > 0 ? (mask & 0xff) : 0);
            mask -= 8;
            maskStr.append(maskIp[i]);
            if (i < maskIp.length - 1) {
            	maskStr.append(".");
            }
        }        
        return maskStr.toString();
    }   
    	
    /**
     * 通过路由表的目的网络是'0.0.0.0'获得网关
     * @param 
     * @return  默认网关
     */
	public static String getGateway() {
		String result = "";
		try{
			String command = "route print";
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;			
			String[] tmp = null;			
			while ((line = br.readLine()) != null) {
				tmp = line.trim().split("\\s+");
				if (tmp.length > 0 && tmp[0].equals("0.0.0.0")) {					
					result = tmp[2];
					break;
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("获取默认网关失败：" + e.getMessage());
		}
		return result;			
	}
    
    /**
     * 设置本地IP
     * @param newLinkName 以太网/本地连接
     * @param newIp 新IP地址
     * @param newNetMask 新子网掩码
     * @param newGateway 新默认网关
     * @return  String
     * @throws Exception
     */
	 public static String setNewIP(String newLinkName,String newIp, String newNetMask, String newGateway) 
			 throws Exception {
		 String cmd = "netsh interface ip set addr \"$(newLinkName)\" static $(newIp) $(newNetMask) $(newGateway) 1";
	 	 cmd = cmd.replace("$(newLinkName)", newLinkName)
	 			.replace("$(newIp)", newIp)
	 			.replace("$(newNetMask)", newNetMask)
	 			.replace("$(newGateway)", newGateway);
	 	 return command(cmd);
	 }	 
	
}
