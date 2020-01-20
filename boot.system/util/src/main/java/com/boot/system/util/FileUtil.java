package com.boot.system.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	
	/**
	 * 删除文件（文件夹）
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param dirPath 删除文件（路径 + 文件名）
	 * @return boolean
	 */
	public static boolean deleteFile(String dirPath) {
		return deleteFile(new File(dirPath));
	}
	
	/**
	 * 删除文件（文件夹）
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param dirPath 删除文件路径
	 * @param fileName 文件名
	 * @return boolean
	 */
	public static boolean deleteFile(String dirPath, String fileName) {
		return deleteFile(new File(dirPath, fileName));
	}
	
	
	/**
	 * 删除文件（文件夹）
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param dir 删除文件（文件夹）
	 * @return boolean
	 */
	public static boolean deleteFile(File dir) {
		// 如果dir对应的文件不存在，则退出
		if (dir == null || !dir.exists()) {
			return false;
		}

		// 如果dir是文件夹，则删除该文件夹中所有的文件
		if (dir.isDirectory()) { 
			for (File file : dir.listFiles()) {
				deleteFile(file);    // 递归
			}
		} 
		
		return dir.delete();
	}
	
	/**
	 * 备份文件
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param src 原文件
	 * @param det 新文件
	 * @return boolean
	 */
	public static boolean copyFile(File src, File dst) {
		boolean result = false;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
			File parentFile = dst.getParentFile();
			if (parentFile == null || !parentFile.exists()) { // 创建父目录
				parentFile.mkdirs();
			}
			if (dst == null || !dst.exists()) {
				dst.createNewFile();
			}
			out = new BufferedOutputStream(new FileOutputStream(dst), 16 * 1024);
			byte[] buffer = new byte[16 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 备份文件(jdk 1.7 及以上)
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param source 原文件
	 * @param dest   新文件
	 * @return boolean
	 */
	public static boolean copyFile7(File source, File dest) {
		boolean result = false;
		try {
			File parentFile = dest.getParentFile();
			if (parentFile == null || !parentFile.exists()) { // 创建父目录
				parentFile.mkdirs();
			}
			Files.copy(source.toPath(), dest.toPath()); // 复制文件
			result = true;
		} catch (IOException e) {
			System.out.println("备份文件失败：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 移动文件
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param source 原文件
	 * @param dest   新文件
	 * @return boolean
	 */
	public static boolean renameTo(File source, File dest) {
		File parentFile = dest.getParentFile();
		if (parentFile == null || !parentFile.exists()) { // 创建父目录
			parentFile.mkdirs();
		}
		return source.renameTo(dest);
	}
	

	/**
	 * 根据文件，获取文件最后修改时间
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param fileName 文件名
	 * @return result
	 */
	public static String getCreateFileTime(File file) {
		String result = "";
		if (file != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = sdf.format(new Date(file.lastModified()));
		}
		return result;
	}

	/**
	 * 根据文件，获取文件后缀名
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param fileName 文件名
	 * @return result
	 */
	public static String getFileSuffix(File file) {
		if (file == null || !file.exists()) {
			throw new NullPointerException("file不能为空");
		}
		return getFileSuffix(file.getName());
	}
	
	/**
	 * 根据文件名，获取文件后缀名
	 * 
	 * @author sushuiming
	 * @version 1.0
	 * @date 2019年8月13日
	 * @param fileName 文件名
	 * @return result
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

}
