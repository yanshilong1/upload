package cn.cloud.core.util;

import java.util.ArrayList;
import java.util.List;

import cn.cloud.core.entity.AcOpcLog;
import cn.cloud.core.entity.ModuleInfo;

import jvc.module.JObject;
import jvc.util.DBUtils;

public class ModuleUtil {
	public List<ModuleInfo> showNearModule(int userId){
		List<ModuleInfo> moduleList = new ArrayList<ModuleInfo>();
		String sql = "SELECT DISTINCT operateModule, parentModule FROM (SELECT * FROM AC_OpcLog ORDER BY operateTime DESC) AS opclog WHERE opclog.operator = " + userId + " AND opclog.parentModule > 0";
		ArrayList list = DBUtils.getResultSet(AcOpcLog.class, sql);
		int size = list.size() > 5 ? 5 : list.size();
		for(int i = 0; i< size; i++){
			AcOpcLog log = (AcOpcLog)list.get(i);
			ModuleInfo module = (ModuleInfo)DBUtils.getResultObject(ModuleInfo.class, "SELECT *,(select moduleName from AC_Module where moduleId="+log.getParentModule()+") as prantModuleName FROM AC_Module WHERE moduleId = "+log.getOperateModule());
			if(module == null)continue;
			moduleList.add(module);
		}
		return moduleList;
	}
	
}
