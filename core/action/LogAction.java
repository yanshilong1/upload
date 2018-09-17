package cn.cloud.core.action;

import jvc.util.db.MyDB;
import jvc.web.action.ActionContent;
import jvc.web.action.BaseAction;
import cn.cloud.core.common.Constants;
import cn.cloud.core.entity.CustomerInfo;
import cn.cloud.core.util.LogUtils;

public class LogAction implements BaseAction
{

	public String Excute(ActionContent input, ActionContent output, MyDB mydb)
	{
		// �?��时间
		Long startTime = System.currentTimeMillis();
		String content = input.getParam("content");
		String strModule = input.getParam("moduleId");
		String strParentModule = input.getParam("parentModuleId");
		int module = 0;
		int parentModuleId = 0;
		if (strModule != null && !"".equals(strModule))
		{
			module = Integer.parseInt(strModule);
		}
		if (strParentModule != null && !"".equals(strParentModule))
		{
			parentModuleId = Integer.parseInt(strParentModule);
		}
		CustomerInfo user = (CustomerInfo) input.getSessionMap().get(Constants.CLIENT_USER_SESSION);
		if(user == null){
			return "";
		}
		if(module != parentModuleId)
			LogUtils.logOpc(user.getUserId(), module, parentModuleId, 1, content, startTime);
		return "";
	}

}
