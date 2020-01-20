/**
 * Copyright(c) Goldeye Science & Technology Ltd. 
 */
package com.boot.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author CHJY
 * @date 2018年8月9日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class FileTypeUtil {
	
	private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
	
	static {
		
		// images
        FILE_TYPE_MAP.put("FFD8FF", "jpg"); //JPEG (jpg)       
        FILE_TYPE_MAP.put("89504E47", "png"); //PNG (png)       
        FILE_TYPE_MAP.put("47494638", "gif"); //GIF (gif)       
        FILE_TYPE_MAP.put("49492A00", "tif"); //TIFF (tif)       
        FILE_TYPE_MAP.put("424D228C01", "bmp"); //16色位图(bmp)       
        FILE_TYPE_MAP.put("424D824009", "bmp"); //24位位图(bmp)       
        FILE_TYPE_MAP.put("424D8E1B03", "bmp"); //256色位图(bmp)  
        
        FILE_TYPE_MAP.put("41433130", "dwg"); // CAD
        FILE_TYPE_MAP.put("38425053", "psd");//PhotoShop
        FILE_TYPE_MAP.put("7B5C727466", "rtf"); //Rich Text Format
        FILE_TYPE_MAP.put("3C3F786D6C", "xml");
        FILE_TYPE_MAP.put("68746D6C3E", "html");//HTML
        FILE_TYPE_MAP.put("44656C69766572792D646174653A", "eml"); // 邮件
        FILE_TYPE_MAP.put("D0CF11E0", "office"); //office类型，包括doc、xls和ppt
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");//MS Access
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462D312E", "pdf");//Adobe Acrobat
        FILE_TYPE_MAP.put("52617221", "rar");
        FILE_TYPE_MAP.put("57415645", "wav");//Wave
        FILE_TYPE_MAP.put("41564920", "avi");
        FILE_TYPE_MAP.put("2E524D46", "rm");//Real Media
        FILE_TYPE_MAP.put("000001BA", "mpg");
        FILE_TYPE_MAP.put("6D6F6F76", "mov");//Quicktime
        FILE_TYPE_MAP.put("3026B2758E66CF11", "asf");//Windows Media
        FILE_TYPE_MAP.put("4D546864", "mid");//MIDI (mid)
        FILE_TYPE_MAP.put("1F8B08", "gz");
        
    }
	
	/** 
     * @author chenjianye 
     * 
     * 方法描述：根据文件路径获取文件头信息 
     * @param filePath 文件路径
     * @return 文件头信息 
     */  
    public static String getFileType(String filePath) {
    	String suffix = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase().trim();
    	System.out.println(suffix);
    	File file = new File(filePath);
    	return getFileType(file);
    }
	
	/** 
     * @author chenjianye 
     * 
     * 方法描述：根据文件获取文件类型
     * @param file 
     * @return 文件头信息 
     */  
    public static String getFileType(File file) {  
        FileInputStream fis = null;  
        String fileType = null;  
        try {  
        	fis = new FileInputStream(file);  
            byte[] b = new byte[4];  
            /* 
             * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length 
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len) 
             * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。 
             */  
            fis.read(b, 0, b.length);  
            String fileCode = bytesToHexString(b);  
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();    
            while(keyIter.hasNext()){
                String key = keyIter.next();
                 //比较前几位是否相同就可以判断文件格式（相同格式文件文件头后面几位会有所变化）  
                if(key.toLowerCase().startsWith(fileCode.toLowerCase())){    
                	fileType = FILE_TYPE_MAP.get(key);    
                	if(fileType.equals("office")) {  
                		fileType = getOfficeFileType(fis);  
                    }
                    break;    
                }    
            }  
            
            //如果不是上述类型，则判断扩展名  
            if(fileType == null) {
            	//System.out.println("不是上述类型");
            	String fileName = file.getName();  
             //如果无扩展名，则直接返回空串  
            	if(-1 == fileName.lastIndexOf(".")) {  
            		return null;  
            	}  
             //如果有扩展名，则返回扩展名  
            	fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();  
            }
            //System.out.println("文件头:"+fileCode+"-----文件类型:"+fileType);
        } catch(FileNotFoundException e){  
            e.printStackTrace();      
        } catch (Exception e) { 
        	e.printStackTrace();
        } finally {  
        	try {  
                fis.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }
        }  
        return fileType;  
    } 
	
	/** 
     * @author chenjianye 
     * 
     * 方法描述：得到上传文件的文件头，将要读取文件头信息的文件的byte数组转换成string类型表示 
     * @param src 要读取文件头信息的文件的byte数组 
     * @return   文件头信息 
     */  
	public static String bytesToHexString(byte[] src){      
        StringBuilder builder = new StringBuilder();      
        if(src == null || src.length <= 0){      
            return null;      
        }
        for(int i = 0; i < src.length; i++){  
        	// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为小写
            String hv = Integer.toHexString(src[i] & 0xFF).toLowerCase();      
            if(hv.length() < 2){      
            	builder.append(0);      
            }
            builder.append(hv);      
        }
        return builder.toString();    
    }
	
	/** 
	  * 判断office文件的具体类型 
	  * @param fileInputStream 
	  * @return office文件具体类型 
	  * @throws BaseException 
	  */  
	  private static String getOfficeFileType(FileInputStream fis) {
		  
		  String officeFileType = "";  
		  byte[] b = new byte[512];    
	      try { 
	    	  fis.read(b, 0, b.length);  
	    	  String filetypeHex = String.valueOf(bytesToHexString(b));   
	    	  String flagString = filetypeHex.substring(992, filetypeHex.length());  
	    	  //System.out.println(flagString);
	    	  if(flagString.toLowerCase().contains("eca5c")){  
	    		  officeFileType = "doc";  
	    	  } else if(flagString.toLowerCase().contains("fffdffffff")){  
	    		  officeFileType = "xls";
	    	  } else if(flagString.toLowerCase().contains("09081000")){  
	    		  officeFileType = "xls";  
	    	  } else if(flagString.toLowerCase().contains("97330")){  
	    		  officeFileType = "ppt";  
	    	  } else {
	    		  officeFileType = null;  
	    	  }
	    	 
	      } catch(FileNotFoundException e){  
	            e.printStackTrace();      
	      } catch (Exception e) { 
	        	e.printStackTrace();
	      } finally {
	    	  try {  
	              fis.close();  
	          }catch (IOException e) {  
	              e.printStackTrace();  
	          }
	      } 
	      return officeFileType;  
	  }
	
	/** 
     * @author chenjianye 
     * 
     * 方法描述：文件流转File
     * @param ins 
     * @param file 
     * @return 文件头信息 
     */  
	public static void inputStreamToFile(InputStream ins, File file) {
		
		try {  
			OutputStream os = new FileOutputStream(file);  
			int bytesRead = 0;  
			byte[] buffer = new byte[8192];  
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {  
				os.write(buffer, 0, bytesRead);  
			}  
			os.close();  
			ins.close();  
	    } catch (Exception e) {  
	       e.printStackTrace();  
	    }  
	}  		
	
}
