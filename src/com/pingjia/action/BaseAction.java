package com.pingjia.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pingjia.tools.Model;

public abstract class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -7191650949495347784L;
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	public void setVal(String key, Object val) {
		ActionContext ctx = ActionContext.getContext();
		ctx.put(key, val);
	}
	public abstract boolean check();
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
		if(check())
			return;
		addFieldError("123", "123");
	}
	public String error(String str) {
		return error(str, getRequest().getHeader("Referer"), 5);
	}

	public String error(String str, String url) {
		return error(str, url, 5);
	}

	public String error(String str, String url, int seconds) {
		setVal("message", str);
		setVal("url", U(url));
		setVal("seconds", seconds + "");
		return ERROR;
	}
	public String success(String str) {
		return success(str, getRequest().getHeader("Referer"), 2);
	}

	public String success(String str, String url) {
		return success(str, url, 2);
	}

	public String success(String str, String url, int seconds) {
		setVal("message", str);
		setVal("url", U(url));
		setVal("seconds", seconds + "");
		return INPUT;
	}
	public String p(String key){
		return getRequest().getParameter(key);
	}
	public Model M(String tablename){
		return new Model(tablename);
	}
	public String U(String struts){
		if(struts.startsWith("http://"))
			return struts;
		return getRequest().getContextPath()+"/"+struts;
	}
	public Object session(String key){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(key);
	}
	public void session(String key,Object value){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(key, value);
	}
	public void assign(String key,Object value){
		setVal(key,value);
	}
}
