package com.boot.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.boot.system.dataorm.config.ResultMsg;
import com.boot.system.dataorm.enums.ResultStatusCode;
import com.boot.system.util.FileUtil;
import com.boot.system.util.UploadFileUtil;


/**
 * <pre>
 * 文件上传下载接口
 * </pre>
 *
 * @author sushuiming
 * @date 2019-05-01
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {
	
	@Value("${file.uploadPath}")
	private String uploadPath;       // 文件上传保存路径

	private final static Log log = LogFactory.getLog(FileController.class);
	
	/**
    *
    * <pre>
    *     上传图片，上传成功后，去掉后缀名，方便直接调用showImage方法
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param file    
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: {origName: "原文件名", fileName: "上传后文件ID"}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public ResultMsg uploadImage(@RequestParam("fileName") MultipartFile file) {
		ResultMsg resultMsg = null;		
		try {			
			if (file != null && !file.isEmpty()) {		
				Map<String, Object> body = new HashMap<String, Object>();
				String fileName = UploadFileUtil.uploadImage(uploadPath, file);
				String origName = new String(file.getOriginalFilename().getBytes(), "UTF-8");
				body.put("origName", origName);
				body.put("fileName", fileName);
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), body);			
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
						"文件不能为空！", null);
			}			
		} catch (IOException e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}
	
	 /**
    *
    * <pre>
    *     上传文件
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param file    
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: {origName: "原文件名", fileName: "上传后文件ID"}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResultMsg uploadFile(@RequestParam("fileName") MultipartFile file) {
		ResultMsg resultMsg = null;		
		try {			
			if (file != null && !file.isEmpty()) {		
				Map<String, Object> body = new HashMap<String, Object>();
				String origName = new String(file.getOriginalFilename().getBytes(), "UTF-8");
				String fileName = UploadFileUtil.uploadFile(uploadPath, origName, file);
				body.put("origName", origName);
				body.put("fileName", fileName);
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), body);			
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
						"文件不能为空！", null);
			}			
		} catch (IOException e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}
	
	/**
    *
    * <pre>
    *     多个上传文件
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param file    
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: {origNames: []", fileNames: []}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	public Object uploadFiles(HttpServletRequest request) {
		ResultMsg resultMsg = null;
		Map<String, Object> body = new HashMap<String, Object>();
		try {		
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileNames");
			if (!files.isEmpty()) {	
				String origName = "";
				String fileName = "";
				List<String> origNames = new ArrayList<String> ();
				List<String> fileNames = new ArrayList<String> ();
				for (MultipartFile file : files) {
					origName = new String(file.getOriginalFilename().getBytes(), "UTF-8");
					fileName = UploadFileUtil.uploadFile(uploadPath, origName, file);
					origNames.add(origName);
					fileNames.add(fileName);
				}				
				body.put("origNames", origNames);
				body.put("fileNames", fileNames);
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), body);			
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
						"文件不能为空！", null);
			}			
		} catch (IOException e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}
	
	/**
    *
    * <pre>
    *     显示图片，fileName不需要加后缀名
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param fileName    
    * @return void
    * @throws 
    */
	@ResponseBody
	@RequestMapping("/showImage")
	public void showImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fileName", required = true) String fileName) {
		try {
			UploadFileUtil.showImage(uploadPath, fileName, request, response);
		} catch (IOException e) {
			log.error(String.format("显示图片失败: %s", e.getMessage()));
		} catch (Exception e) {
			log.error(String.format("显示失败: %s", e.getMessage()));
		}
	}
	
	/**
    *
    * <pre>
    *     下载文件
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param fileName    
    * @return void
    * @throws 
    */
	@ResponseBody
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fileName", required = true) String fileName) {
		try {
			UploadFileUtil.downloadFile(uploadPath, fileName, request, response);
		} catch (IOException e) {
			log.error(String.format("下载文件失败: %s", e.getMessage()));
		} catch (Exception e) {
			log.error(String.format("下载文件失败: %s", e.getMessage()));
		}
	}
	
	/**
    *
    * <pre>
    *     删除文件
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param fileName 需要删除的文件名    
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public ResultMsg deleteFile(@RequestParam(value = "fileName", required = true) String fileName) {
		ResultMsg resultMsg = null;		
		try {	
			boolean isDelete = FileUtil.deleteFile(uploadPath, fileName);
			if (isDelete) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);			
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
						"删除文件失败", null);
			}			
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}
	
	
}
