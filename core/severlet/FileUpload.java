package cn.cloud.core.severlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jvc.util.DBUtils;
import jvc.util.RecordSetUtils;
import jvc.util.db.Insert;
import jvc.util.db.Update;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import cn.cloud.core.util.Utility;

public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String savepath = Utility.getParamValue("picserver_")+"/file/";// 文件存储目录
		String loadpath = Utility.getParamValue("picserver")+"/file/";// 文件获取路径
		DiskFileUpload fu = new DiskFileUpload();
		fu.setHeaderEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		// 开始读取上传信息
		List fileItems = null;
		String url = "";
		String picName="";
		try {
			fileItems = fu.parseRequest(request);
		
			if(fileItems ==null){
				out.print("error");
				return;
			}
			Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
			List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
			while (iter.hasNext()) {
				Map<String,String> map = new HashMap<String,String>();
				FileItem item = (FileItem) iter.next();// 忽略其他不是文件域的所有表单信息
				
				if (!item.isFormField()) {
					String name = item.getName();// 获取上传文件名,包括路径
					name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
					picName = name;
					long size = item.getSize();
					if ((name == null || name.equals("")) && size == 0){
						out.print("error");
						return;
					}
//					String extension = name.substring(name.lastIndexOf(".")+1);
//					if(!"jpg".equals(extension) && !"gif".equals(extension) && !"png".equals(extension) && !"jpeg".equals(extension) && !"bmp".equals(extension)){
//						out.print("wronggs");
//						return;
//					}
//					if(size > 2 * 1024 *1024){
//						out.print("wrongsize");
//						return;
//					}
					int point = name.indexOf(".");
					name = (new Date()).getTime()+((int)(Math.random()*100))+ name.substring(point, name.length());
					File fNew = new File(savepath, name);
					item.write(fNew);
					url = loadpath + "/" + name;
					
					out.print("url:"+url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("error");
		}
	}

}
