package com.boot.system.dataorm.config;

import java.util.Map;

/**
 * <pre>
 * 分页存放数据
 * </pre>
 *
 * @author sushuiming
 * @date 2018年6月21日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class PageBean {
	//已知数据
    private int currentPage;    //当前页,从请求那边传过来。
    private int pageSize;    	//每页显示的数据条数。
    private int totalRecord;    //总的记录条数。查询数据库得到的数据
    private final static int DEFAULT_MAXSIZE = 30;
    private final static int DEFAUL_TSIZE = 10;
     
    //需要计算得来
    private int totalPage;    //总页数，通过totalRecord和pageSize计算可以得来
    
    //开始索引，也就是我们在数据库中要从第几行数据开始拿，有了startIndex和pageSize，
    //就知道了limit语句的两个数据，就能获得每页需要显示的数据了
    private int startIndex;       

    //通过currentPage，pageSize，totalRecord计算得来tatalPage和startIndex
    //构造方法中将currentPage，pageSize，totalRecord获得
    public PageBean(int currentPage, int pageSize, int totalRecord) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        
        //totalPage 总页数
        if(totalRecord % pageSize == 0){
            //说明整除，正好每页显示pageSize条数据，没有多余一页要显示少于pageSize条数据的
            this.totalPage = totalRecord / pageSize;
        }else{
            //不整除，就要在加一页，来显示多余的数据。
            this.totalPage = totalRecord / pageSize + 1;
        }
        //当前页总记录数 > 0 && 当前页 * 显示记录数  > 当前页总记录数
		/*if (totalRecord > 0 && currentPage * pageSize > totalRecord) {
			//显示最后一页
			currentPage = this.totalPage;
		}*/
        
        //开始索引
        this.startIndex = (currentPage - 1 ) * pageSize ;
        
    }
    
    //get、set方法。
    public int getCurrentPage() {
        return currentPage;
    }
 
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
 
    public int getPageSize() {
        return pageSize;
    }
 
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
        return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
 
    public int getTotalPage() {
        return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
 
    public int getStartIndex() {
        return startIndex;
    }
 
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    
    //获取当前第几页
    public static int getCurrentPage(Map<String, Object> map) {
		int currentPage = 0;
		//如果currentPage字段存在
		if(map != null && map.containsKey("currentPage")) {
			currentPage = (int) map.get("currentPage");
			//当前页 < 1
			if(currentPage < 1){
				currentPage = 1;
			}
		}else {
			currentPage = 1;//第1页
		}
		return currentPage;
	}
    
    //获取显示记录数
    public static int getPageSize(Map<String, Object> map) {
		int pageSize = 0;
		//如果pageSize字段存在 && pageSize > 0
		if(map != null && map.containsKey("pageSize") && (int) map.get("pageSize") > 0) {
			pageSize = (int) map.get("pageSize");//每页显示的记录数

			//每页显示的记录数 > 分页参数常量每页最大数
			if(pageSize > DEFAULT_MAXSIZE){
				pageSize = DEFAULT_MAXSIZE;
			}
		}else {
			pageSize = DEFAUL_TSIZE;//每页默认记录数
		}
		
		return pageSize;
	}

}
