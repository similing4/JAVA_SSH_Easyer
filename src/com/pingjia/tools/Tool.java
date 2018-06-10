package com.pingjia.tools;

import org.apache.struts2.ServletActionContext;

public class Tool {
	public static String u(String struts){
		return ServletActionContext.getRequest().getContextPath()+"/"+struts;
	}
}
