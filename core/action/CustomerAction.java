package cn.cloud.core.action;

import jvc.util.DBUtils;
import jvc.util.db.MyDB;
import jvc.web.action.ActionContent;
import jvc.web.action.BaseAction;

public class CustomerAction implements BaseAction {

	public String Excute(ActionContent input, ActionContent output, MyDB mydb) {
		mydb.check();
		jvc.module.JList rs = new jvc.module.JList(output, "res");
		while (rs.next()) {
			int id = rs.getInt("id");
			
			rs.set("search_company", DBUtils.getInt("select count(id) from search_log where cid="+id+" and stype=2"));
			rs.set("search_person", DBUtils.getInt("select count(id) from search_log where cid="+id+" and stype=1"));
			rs.set("search_contract", DBUtils.getInt("select count(id) from search_log where cid="+id+" and stype=3"));
		}
		return "@json";
	}
}
