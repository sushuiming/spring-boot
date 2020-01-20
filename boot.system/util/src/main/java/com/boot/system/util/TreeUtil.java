package com.boot.system.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.boot.system.dataorm.config.Tree;


/**
 * <pre>
 * 	list转tree工具。
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TreeUtil {
     	
	/**
    *
    * <pre>
    *      根据全部list返回符合要求的tree
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param all	全部list
    * @return roots
    * @throws 
    */
	public static <T extends Tree> List<T> buildTree(List<T> all) {  
        List<T> roots = findRoots(all);  
        List<T> notRoots = (List<T>) CollectionUtils.subtract(all, roots);  
        for (T root : roots) {  
            root.setChildrens(findChildrens(root, notRoots));  
        }  
        return roots;  
    }  
  
	
	/**
    *
    * <pre>
    *      根据全部list返回根节点
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param all	全部list
    * @return roots
    * @throws 
    */
	public static <T extends Tree> List<T> findRoots(List<T> all) {  
	    List<T> roots = new ArrayList<T>();  
	    for (T t : all) {   
	        for (T comparedOne : all) {  
	        	// 当parentId和id不相等时，将改节点添加到roots中
	            if (!t.getParentId().equals(comparedOne.getId())) {  
	            	roots.add(t);
	            	break;
	            }  
	        }              
	    }  
	    return roots;  
	}  
    
	/**
    *
    * <pre>
    *      根据当前根节点和非根节点，返回子节点
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param root	当前根节点
    * @param notRoots	非根节点
    * @return childrens
    * @throws 
    */
	public static <T extends Tree> List<T> findChildrens(T root, List<T> notRoots) {  
        List<T> childrens = new ArrayList<T>();   
        for (T notRoot : notRoots) {  
            if (notRoot.getParentId().equals(root.getId())) {  // notRoot.getParentId() == root.getId()                                 
                childrens.add(notRoot);  
            }  
        }  
		List<T> notChildrens = (List<T>) CollectionUtils.subtract(notRoots, childrens);  
		List<T> tmpChildren = null;
        for (T children : childrens) {  
            tmpChildren = findChildrens(children, notChildrens);            
            children.setChildrens(tmpChildren);  
        }  
        return childrens;  
    }  
       
}
