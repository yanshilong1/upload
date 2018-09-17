package cn.cloud.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.batik.apps.rasterizer.Main;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import jvc.util.DBUtils;
import jvc.util.security.MD5;

public class Utility {

	public static String getParamValue(String keyName){
		return DBUtils.getString("select keyValue from AC_SystemConfig where keyName='"+keyName+"'");
	}
	
	public static String getCheckcode(){
		String ckcode = "";
		Random random = new Random();
		for(int i = 0 ; i < 6 ; i ++){
			ckcode += random.nextInt(10);
		}
		return ckcode;
	}
	
	public static String getNow(boolean flag){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		String day = now.split(" ")[0];
		String time = now.split(" ")[1];
		now = day.split("-")[0] + "年" + day.split("-")[1] + "月" + day.split("-")[2] + "日";
		if(flag){
			now += "  " + time;  
		}
		return now;
	}
	
	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip != null && !"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(ip != null && !"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	public static JsonObject postDownloadJson(String path){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            JsonParser parse = new JsonParser();
            return (JsonObject)parse.parse(bos.toString("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/** 
	 * 将分为单位的数额转换为元 
	 * @param amount 
	 * @return 
	 */  
	public static String feeToYuan(Long amount){  
		try {  
			if(!amount.toString().matches("\\-?[0-9]+"));  
		}catch (Exception e) {  
			e.printStackTrace();  
		}  
		int flag = 0;  
		String amString = amount.toString();
		if(amString.charAt(0)=='-'){  
			flag = 1;      
			amString = amString.substring(1);      
		   }      
		   StringBuffer result = new StringBuffer();      
	       if(amString.length()==1){      
	           result.append("0.0").append(amString);      
	       }else if(amString.length() == 2){      
	           result.append("0.").append(amString);      
	       }else{      
	            String intString = amString.substring(0,amString.length()-2);      
	            for(int i=1; i<=intString.length();i++){  
	                if( (i-1)%3 == 0 && i !=1){      
	                    result.append(",");      
	                }      
	                result.append(intString.substring(intString.length()-i,intString.length()-i+1));      
	            }      
	            result.reverse().append(".").append(amString.substring(amString.length()-2));      
	     }      
	     if(flag == 1){  
	      return "-"+result.toString();      
	     }else{      
	         return result.toString();      
	     }      
	}
	
	public static String extractMessageByRegular(String msg){  
		String res = "";
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");  
        Matcher m = p.matcher(msg);  
        if(m.find()){  
        	res = m.group().substring(1, m.group().length()-1);
        }  
        return res;  
    }
	
	public static void main(String[] args) {
		String aaa = "18630858252111111";
		System.out.println(new MD5().getMD5ofStr(aaa));
	}
	
}
