package cn.cloud.core.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jvc.util.DBUtils;
import jvc.util.RequestUtils;
import jvc.util.cache.CachePool;

public class SystemConfigUtils {
	 public static String getKeyValue(String keyName){
			String cacheName="SystemConfig_StringValue1_"+keyName;
			String StringValue1=(String)CachePool.getObject(cacheName);
			if(StringValue1!=null){ return StringValue1;}
			StringValue1= jvc.util.DBUtils.getString("select keyValue from SM_SystemConfig where keyName='"+keyName+"'");
			CachePool.putObject(cacheName, StringValue1,5*60*1000);
			return StringValue1;
		 }
	 public static String getKeyValue(String keyName,String defaultValue){
			String cacheName="SystemConfig_StringValue1_"+keyName;
			String StringValue1=(String)CachePool.getObject(cacheName);
			if(StringValue1!=null){ return StringValue1;}
			StringValue1= jvc.util.DBUtils.getString("select keyValue from SM_SystemConfig where keyName='"+keyName+"'");
			//如果没有查询到参数 则返回默认值
			if(StringValue1 == null || "".equals(StringValue1))
			{
				return defaultValue;
			}
			CachePool.putObject(cacheName, StringValue1,5*60*1000);
			return StringValue1;
		 } 
	 public static Map<String,String> getUnitMap(String userId){
		 Map<String,String> map = new HashMap<String,String>();
		 map.put("departmentId", "0");   //部门
		 map.put("filialeId", "0");    //分公司
		 map.put("detachmentId", "0"); //小分队
		 map.put("villageId", "0");  //小区
		 
		 int unitId = DBUtils.getInt("select unitId from PoliceUser where userId='" + userId+ "'"); // 小区
		 int level = DBUtils.getInt("select unitLevel from UnitInfo where unitId="+unitId); 
		 if(level ==1){
			 map.put("departmentId", unitId+"");   
		 } else if(level ==2){
			 map.put("filialeId", unitId+"");   
			 unitId = DBUtils.getInt("select unitParent from UnitInfo where unitId="+unitId); 
			 map.put("departmentId", unitId+"");   
		 } else if(level ==3){
			 map.put("detachmentId", unitId+"");   
			 unitId = DBUtils.getInt("select unitParent from UnitInfo where unitId="+unitId); 
			 map.put("filialeId", unitId+"");   
			 unitId = DBUtils.getInt("select unitParent from UnitInfo where unitId="+unitId); 
			 map.put("departmentId", unitId+"");   
		 }else if(level ==4){
			 map.put("villageId", unitId+"");   
			 unitId = DBUtils.getInt("select unitParent from UnitInfo where unitId="+unitId); 
			 map.put("detachmentId", unitId+"");   
			 unitId = DBUtils.getInt("select unitParent from UnitInfo where unitId="+unitId); 
			 map.put("filialeId", unitId+"");  
			 unitId = DBUtils.getInt("select unitParent from UnitInfo where unitId="+unitId); 
			 map.put("departmentId", unitId+"");  
		 }
		 
		 return map;
	 }
	 
	 public static String getCallBack(HttpServletRequest request,String res){
		String functitonName=RequestUtils.getString(request, "callback"); 
		if(functitonName.equals("")){
			return res;
		}
		return functitonName+"("+res+")";
	}  
	 
}
