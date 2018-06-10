package com.pingjia.tools;

import java.util.Hashtable;

public class DB_Where {
	private Hashtable<String, String> where = new Hashtable<String, String>();
	private Hashtable<String, String> special = new Hashtable<String, String>();

	public void setWhere(String key, String val) {
		this.where.put(key, val);
	}

	public void setSpecial(String key, String val) {//注意，val传入需要验证值是否包含单引号等防止sql注入
		this.special.put(key, val);
	}

	public boolean isEmpty(){
		return (where.size() == 0 && special.size() == 0);
	}
	public String getWhereSQL() {
		if (isEmpty())
			return "";
		String sql = null;
		StringBuffer buffer = new StringBuffer();
		for (String key : where.keySet()) {
			buffer.append(key + "='" + where.get(key).replace('\'', '\"') + "' and ");
		}
		for (String key : special.keySet()) {
			buffer.append(key + "" + where.get(key) + " and ");
		}
		sql = buffer.substring(0, buffer.length() - 4);
		return sql;
	}
}
