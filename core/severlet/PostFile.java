package cn.cloud.core.severlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import cn.cloud.core.util.Utility;

public class PostFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String filePath = Utility.getParamValue("picserver_")+"/file/";
	private static final String FAR_SERVICE_DIR = "http://60.205.0.237:12345/contract/upload";//远程服务器接受文件的路由  
    private static final long yourMaxRequestSize = 10000000;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		Map<String,Object> resultMap = null;
		try {
			
			// 判断enctype属性是否为multipart/form-data  
	        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
	        if (!isMultipart)  
	            throw new IllegalArgumentException(  
	                    "上传内容不是有效的multipart/form-data类型.");  
	  
	        // Create a factory for disk-based file items  
	        DiskFileItemFactory factory = new DiskFileItemFactory();  
	  
	        // Create a new file upload handler  
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        
	        upload.setHeaderEncoding("utf-8");//解决文件名称乱码
	  
	        // 设置上传内容的大小限制（单位：字节）  
	        upload.setSizeMax(yourMaxRequestSize);  
	  
	        // Parse the request  
	        List<?> items = upload.parseRequest(request);  
	  
	        Iterator iter = items.iterator();
	        Map<String,String> postParam = new HashMap<String,String>();
	        
	        File postFile = null;
	        
	        while (iter.hasNext()) {
	            FileItem item = (FileItem) iter.next();  
	  
	            if (item.isFormField()) {  
	                // 如果是普通表单字段  
	                String name = item.getFieldName();  
	                String value = item.getString();  
	                
	                postParam.put(name, value);
	            } else {
	            	InputStream ins = item.getInputStream();
	            	
	            	String fname = item.getName();
	            	
	            	if(fname.indexOf("\\") != -1){
	            		fname = fname.substring(fname.lastIndexOf("\\")+1);
	            	}
	            	
	            	postFile = new File(filePath+fname);
	            	
					OutputStream os = new FileOutputStream(postFile);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
						os.write(buffer, 0, bytesRead);
					}
					os.close();
					ins.close();
	            }  
	        }
	        resultMap = uploadFileByHTTP(postFile,FAR_SERVICE_DIR,postParam);
	        
	        if(resultMap.get("data") != null){
	        	String data = resultMap.get("data").toString();
	        	JSONObject json = JSONObject.parseObject(data);
	        	String url = json.getJSONObject("data").getString("url");
	        	
	        	request.getSession().setAttribute("CHECK_URL", url);
	        }
	        
		} catch (Exception e) {  
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();  
        }finally {
        	out.print(resultMap);
		}
	}

	/** 
     * 模拟表单上传文件 
     */  
     public Map<String,Object> uploadFileByHTTP(File postFile,String postUrl,Map<String,String> postParam){  
         Map<String,Object> resultMap = new HashMap<String,Object>();  
         CloseableHttpClient httpClient = HttpClients.createDefault();
         try{    
             //把一个普通参数和文件上传给下面这个地址    是一个servlet    
             HttpPost httpPost = new HttpPost(postUrl);    
             //把文件转换成流对象FileBody  
             FileBody fundFileBin = new FileBody(postFile);    
             //设置传输参数  
             
             MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
             //MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();    
             reqEntity.addPart("file", fundFileBin);//相当于<input type="file" name="media"/>    
             //设计文件以外的参数  
             Set<String> keySet = postParam.keySet();  
             for (String key : keySet) {  
                //相当于<input type="text" name="name" value=name>    
            	 reqEntity.addPart(key, new StringBody(postParam.get(key), ContentType.create("text/plain", Consts.UTF_8)));    
             }
             
             //HttpEntity reqEntity =  multipartEntity.build();   
             httpPost.setEntity(reqEntity);    
                 
             System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());    
             //发起请求   并返回请求的响应    
             CloseableHttpResponse response = httpClient.execute(httpPost);
             Charset charset = null;
             if (charset == null) {  
                 charset = HTTP.DEF_CONTENT_CHARSET;
             }
             try {    
                 System.out.println("----------------------------------------");    
                 //打印响应状态    
                 System.out.println(response.getStatusLine());  
                 resultMap.put("statusCode", response.getStatusLine().getStatusCode());  
                 //获取响应对象    
                 HttpEntity resEntity = response.getEntity();    
                 if (resEntity != null) {    
                     //打印响应长度    
                     System.out.println("Response content length: " + resEntity.getContentLength());    
                     //打印响应内容    
                     String outstr = new String(EntityUtils.toString(resEntity));
                     resultMap.put("data", outstr);
                 }    
                 //销毁    
                 EntityUtils.consume(resEntity);    
             } catch (Exception e) {  
                 e.printStackTrace();  
             } finally {    
                 response.close();    
             }    
         } catch (ClientProtocolException e1) {  
            e1.printStackTrace();  
         } catch (IOException e1) {  
            e1.printStackTrace();  
         } finally{
             try {
                httpClient.close();
             } catch (IOException e) {
                e.printStackTrace();  
             }
             postFile.delete();
         }
        System.out.println("uploadFileByHTTP result:"+resultMap);
        return resultMap;    
     }
	
}
