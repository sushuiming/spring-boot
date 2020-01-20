package com.boot.system.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.system.dataorm.config.CheckParamUtil;
import com.boot.system.dataorm.config.PageBean;
import com.boot.system.dataorm.config.ParamException;
import com.boot.system.dataorm.config.ResultMsg;
import com.boot.system.dataorm.enums.ResultStatusCode;
import com.boot.system.service.BaseService;


/**
 * <pre>
 * 	基础controller类，包含了常用方法
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

public class BaseController<T extends Object, ID extends Serializable> {

	private final static Logger log = LoggerFactory.getLogger(BaseController.class);
    			
	@Autowired
	private BaseService<T, ID> baseService;
	
	public BaseController() {
		/*try {
			// 通过反射获取T的实际类型
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
			// System.out.println("model的实际类型为：" + clazz);

			// 通过反射生成对象实例
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}			*/		
	}
			
	/**
    *
    * <pre>
    *      添加
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param model	
    * @return resultMsg
    *	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg insert(HttpServletRequest request, @RequestBody T model) {	
		ResultMsg resultMsg = null;
		try {			
			String onlineId = request.getHeader("id");
			CheckParamUtil.checkObject(model);
			int res = baseService.insert(onlineId, model);
			if (res > 0) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.ERROR_ADD.getErrorCode(), 
						ResultStatusCode.ERROR_ADD.getErrorMsg(), null);
			}			
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      选择性添加
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param model	
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/insertSelective", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg insertSelective(HttpServletRequest request, @RequestBody T model) {	
		ResultMsg resultMsg = null;
		try {			
			String onlineId = request.getHeader("id");
			CheckParamUtil.checkObject(model);
			int res = baseService.insertSelective(onlineId, model);
			if (res > 0) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.ERROR_ADD.getErrorCode(), 
						ResultStatusCode.ERROR_ADD.getErrorMsg(), null);
			}	
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      修改
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param model	
    * @return resultMsg
    *   	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg update(HttpServletRequest request, @RequestBody T model) {	
		ResultMsg resultMsg = null;
		try {			
			String onlineId = request.getHeader("id");
			CheckParamUtil.checkObject(model);
			int res = baseService.update(onlineId, model);
			if (res > 0) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
						ResultStatusCode.SYSTEM_ERR.getErrorMsg(), null);
			}	
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      选择性修改
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param model	
    * @return resultMsg
    *   	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/updateSelective", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg updateSelective(HttpServletRequest request, @RequestBody T model) {	
		ResultMsg resultMsg = null;
		try {			
			String onlineId = request.getHeader("id");
			CheckParamUtil.checkObject(model);
			int res = baseService.updateSelective(onlineId, model);
			if (res > 0) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.ERROR_UPDATE.getErrorCode(), 
						ResultStatusCode.ERROR_UPDATE.getErrorMsg(), null);
			}	
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      删除
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map {"id": "this is delete id"}	
    * @return resultMsg
    *   	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg delete(HttpServletRequest request, @RequestBody Map<String, Object> map) {	
		ResultMsg resultMsg = null;
		try {	
			ID id = (ID) CheckParamUtil.checkMap(map, "id", true);
			int res = baseService.delete(id);
			if (res > 0) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.ERROR_DELETE.getErrorCode(), 
						ResultStatusCode.ERROR_DELETE.getErrorMsg(), null);
			}	
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      删除多条记录
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map {"ids": ["this is delete id1", "this is delete id2"]}	
    * @return resultMsg
    *   	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg deleteBatch(HttpServletRequest request, @RequestBody Map<String, Object> map) {	
		ResultMsg resultMsg = null;
		try {		
			List<ID> ids = (List<ID>) CheckParamUtil.checkMap(map, "ids", true);
			int res = baseService.deleteBatch(ids);
			if (res > 0) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.ERROR_DELETE.getErrorCode(), 
						ResultStatusCode.ERROR_DELETE.getErrorMsg(), null);
			}	
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      根据id，获取一条记录
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map {"id": "this is get entity id"}	
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: {entity: {}}}
    * @throws 
    */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getEntityById", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg getEntityById(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;
		try {	
			Map<String, Object> body = new HashMap<String, Object> ();
			ID id = (ID) CheckParamUtil.checkMap(map, "id", true);
			T entity = baseService.getEntityById(id);
			if (entity == null) {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_RECORD_NULL.getErrorCode(), 
						ResultStatusCode.INVALID_RECORD_NULL.getErrorMsg(), null);
				return resultMsg;
			}
			body.put("entity", entity);
			resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
				ResultStatusCode.SUCCESS.getErrorMsg(), body);
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
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
    *      全字段模糊, 获取全部记录
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map 	
    * @return resultMsg
    * 	resultMsg =	{errorCode: 0, errorMsg: "success", body: {list: [{},...]}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg getList(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;		
		try {	
			Map<String, Object> body = new HashMap<String, Object> ();
			List<T> list = baseService.getList(map);
			body.put("list", list);
			resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
				ResultStatusCode.SUCCESS.getErrorMsg(), body);
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
    *      全字段模糊, 获得全部记录, 并分页
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map {currentPage: 当前页号, pageSize: 每页数据大小, otherParam: otherVal}	
    * @return resultMsg 
    * 	resultMsg =	{errorCode: 0, errorMsg: "success", body: {totalPage: 1, totalRecord: 10, list: [{},...]}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/getListByPage", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg getListByPage(HttpServletRequest request, @RequestBody Map<String, Object> map){		
		ResultMsg resultMsg = null;			
		try {		
			Map<String, Object> body = new HashMap<String, Object> ();
			int currentPage = PageBean.getCurrentPage(map);//当前页为第几页
	        int pageSize = PageBean.getPageSize(map);//每页显示的记录数
	        body = baseService.getListByPage(map, currentPage, pageSize);
	       
			resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					ResultStatusCode.SUCCESS.getErrorMsg(), body);			
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
    *      根据条件，获取全部记录统计数
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map 	
    * @return resultMsg 
    * 	resultMsg =	{errorCode: 0, errorMsg: "success", body: {count: 100}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/getCount", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg getCount(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;		
		try {			
			Map<String, Object> body = new HashMap<String, Object> ();
			Long count = baseService.getCount(map);
			body.put("count", count);
			resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
				ResultStatusCode.SUCCESS.getErrorMsg(), body);
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}
			
}
