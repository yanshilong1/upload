package cn.cloud.core.severlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import jvc.util.RequestUtils;

public class DownLoad extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int type = RequestUtils.getInt(request, "type");
			String path = "";
			HttpURLConnection urlc = null;
			InputStream inStream = null;
		    if (type == 1) {
		    	path = request.getSession().getAttribute("CHECK_URL").toString();
		    	String fileName = path.substring(path.lastIndexOf("/")+1);
			    String filePath = path.substring(0, path.lastIndexOf("/")+1);
			    
			    path = filePath + URLEncoder.encode(fileName,"UTF-8").replace("+","%20");
			    
			    URL url = new URL(path);
			    urlc = (HttpURLConnection) url.openConnection();
			    
			    urlc.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
			    urlc.setRequestProperty("Accept-Language", "zh-CN");
			    
				// 设置字符编码
			    urlc.setRequestProperty("Charset", "UTF-8");  
			    String realname = fileName;
			    
				//将文件读入文件流
			    inStream = urlc.getInputStream(); 
				//设置浏览器代理信息
			    String agent = request.getHeader("USER-AGENT");
			    
			    /**if(agent != null && agent.indexOf("MSIE") == -1) {// FF      
			    	realname = "=?UTF-8?B?" + (new String(Base64.encodeBase64(realname.getBytes("UTF-8")))) + "?=";
			    	response.setHeader("content-disposition", "attachment;filename=" + realname);
			    }else if (agent.contains("Safari")) {
		            // name.getBytes("UTF-8")处理safari的乱码问题
		            byte[] bytes = fileName.getBytes("UTF-8");
		            // 各浏览器基本都支持ISO编码
		            fileName = new String(bytes, "ISO-8859-1");
		            // 文件名外的双引号处理firefox的空格截断问题
		            response.setHeader("content-disposition", String.format("attachment; filename=\"%s\"", fileName));
		        } else { // IE  
			       response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			    }*/
			    
			    System.out.println(agent);
			    
			    if (agent.contains("MSIE") || agent.contains("Trident") ) {
			    	System.out.println("----------------------------------------------1");
	                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	            } else {
	            	System.out.println("----------------------------------------------2");
	                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	            }
			    
			    response.setHeader("content-disposition", "attachment;filename=" + fileName);
		    	
		    }else if(type == 2){
		    	path = request.getSession().getAttribute("STADARD_URL").toString();
			    
			    URL url = new URL(path);
			    urlc = (HttpURLConnection) url.openConnection();
			    
			    urlc.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
			    urlc.setRequestProperty("Accept-Language", "zh-CN");
			    
				// 设置字符编码
			    urlc.setRequestProperty("Charset", "UTF-8");  
			    String realname = request.getSession().getAttribute("CTYPENAME").toString()+"-标准合同.docx";
			    
				//将文件读入文件流
			    inStream = urlc.getInputStream(); 
				//设置浏览器代理信息
			    String agent = request.getHeader("USER-AGENT");
			    
			    if (agent.contains("MSIE") || agent.contains("Trident") ) {
			    	System.out.println("----------------------------------------------3");
			    	realname = java.net.URLEncoder.encode(realname, "UTF-8");
	            } else {
	            	System.out.println("----------------------------------------------4");
	            	realname = new String(realname.getBytes("UTF-8"), "ISO-8859-1");
	            }
			    
			    response.setHeader("content-disposition", "attachment;filename=" + realname);
		    	
		    }else{
		    	return;
		    }
		    
		    if(path == null || "".equals(path)) return;
	    
			// 循环取出流中的数据
		    byte[] b = new byte[1024];
			int len;
			while ((len = inStream.read(b)) > 0){
				response.getOutputStream().write(b, 0, len);
			}
			
			inStream.close();
			response.getOutputStream().close();
			urlc.disconnect();
			
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
}
