package cn.cloud.core.util;

import cn.cloud.core.common.Constants;

public class LogUtils {
	public static void logOpc(int userId, int operateModule,
			int parentModuleId, int operateResult, String logInfo,
			Long startTime) {

		jvc.util.db.Insert insert = new jvc.util.db.Insert(
				Constants.OPCLOG_TABLE);
		insert.setValue("operateTime", new java.sql.Timestamp(System
				.currentTimeMillis()));
		insert.setValue("operator", userId);
		insert.setValue("logInfo", logInfo);
		insert.setValue("parentModule", parentModuleId);
		insert.setValue("operateResult", operateResult);
		insert.setValue("operateModule", operateModule);
		Long endTime = System.currentTimeMillis();
		insert.setValue("consumeTime", endTime - startTime);
		insert.execute();
	}
}
