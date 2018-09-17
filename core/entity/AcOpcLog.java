package cn.cloud.core.entity;

public class AcOpcLog {
	
	private Integer logId = 0;// 日志编号
	private String operateTime = "";// 操作时间
	private Integer operator = 0;// 操作人
	private Integer parentModule = 0;// 上层模块编号
	private Integer operateModule = 0;// 操作模块编号
	private String logInfo = "";// 日志内容
	private Integer operateResult = 0;// 操作结果
	private Integer consumeTime = 0;// 操作消耗时间

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getParentModule() {
		return parentModule;
	}

	public void setParentModule(Integer parentModule) {
		this.parentModule = parentModule;
	}

	public Integer getOperateModule() {
		return operateModule;
	}

	public void setOperateModule(Integer operateModule) {
		this.operateModule = operateModule;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public Integer getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}

	public Integer getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Integer consumeTime) {
		this.consumeTime = consumeTime;
	}
}
