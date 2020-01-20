package com.boot.system.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;


public class UploadFileUtil {
	
		
	public static String uploadImage(String uploadPath, MultipartFile file) 
			throws IllegalStateException, IOException {
		String fileName = ProduceUtil.UUID();		
    	File dest = new File(uploadPath, fileName);
		File parentFile = dest.getParentFile();
		if (parentFile == null || !parentFile.exists()) { // 创建父目录
			parentFile.mkdirs();
		}    	    	
    	file.transferTo(dest); // MultipartFile自带的解析方法    	
		return fileName;		
	}
	
	
	public static String uploadFile(String uploadPath, String origName, MultipartFile file) 
			throws IllegalStateException, IOException {
		
		String suffix = FileUtil.getFileSuffix(origName);
		String fileName = ProduceUtil.UUID() + "." + suffix;
    	File dest = new File(uploadPath, fileName);
		File parentFile = dest.getParentFile();
		if (parentFile == null || !parentFile.exists()) { // 创建父目录
			parentFile.mkdirs();
		}    	    	
    	file.transferTo(dest); // MultipartFile自带的解析方法    	
		return fileName;		
	}
	
	@SuppressWarnings("resource")
	public static void downloadFile(String uploadPath, String fileName, HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
		//转码，免得文件名中文乱码  
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8"); 
        //获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(uploadPath,fileName)));         
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);    
        //设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while ((len = bis.read()) != -1) {  
            out.write(len);  
            out.flush();  
        }  
        out.close();
	}
	
	public static void showImage(String uploadPath, String fileName, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
        File file = new File(uploadPath, fileName);
        InputStream resourceAsStream = FileUtils.openInputStream(file);
    	ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(resourceAsStream, output);  
	}

}
