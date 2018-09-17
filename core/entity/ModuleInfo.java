package cn.cloud.core.entity;

public class ModuleInfo {
	
	private int moduleId = 0;// 菜单编号
	private int parentModuleId = 0;// 上层菜单编号
	private String moduleName = "";// 菜单名称
	private String moduleLink = "";// 功能链接
	private int orderId = 0;// 顺序号
	private int isActive = 0;// 是否启用
	
	private String prantModuleName = "";// 上级菜单名称

	public String getPrantModuleName() {
		return prantModuleName;
	}

	public void setPrantModuleName(String prantModuleName) {
		this.prantModuleName = prantModuleName;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getParentModuleId() {
		return parentModuleId;
	}

	public void setParentModuleId(int parentModuleId) {
		this.parentModuleId = parentModuleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleLink() {
		return moduleLink;
	}

	public void setModuleLink(String moduleLink) {
		this.moduleLink = moduleLink;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}
