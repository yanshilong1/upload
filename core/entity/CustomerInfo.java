package cn.cloud.core.entity;

public class CustomerInfo {

	private int userId = 0;// 管理员编号
	private String userName = "";// 名称
	private String password = "";// 登录密码
	private String nickName = "";// 昵称
	private String phone = "";// 电话
	private String email = "";// 邮箱
	private String remark = "";// 备注
	private String lastLoginTime = "";// 上次登录时间
	private String lastLoginIp = "";// 上次登录ip
	private String lastLoginAddress = "";// 上次登录地点
	private String loginTime = "";// 当前登录时间
	private String loginAddress = "";// 登录地址
	private String loginIp = "";// 登录ip
	private int loginNum = 0;// 登录次数
	private int isActive = 0;// 是否激活
	private int checkJob = 0;// 是否具有审核职能
	private int sendRight = 0;// 是否具有指派职能
	private int deleteRight = 0;//是否具有删除职能
	
	public int getCheckJob() {
		return checkJob;
	}

	public void setCheckJob(int checkJob) {
		this.checkJob = checkJob;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public int getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getSendRight() {
		return sendRight;
	}

	public void setSendRight(int sendRight) {
		this.sendRight = sendRight;
	}

	public int getDeleteRight() {
		return deleteRight;
	}

	public void setDeleteRight(int deleteRight) {
		this.deleteRight = deleteRight;
	}

}
