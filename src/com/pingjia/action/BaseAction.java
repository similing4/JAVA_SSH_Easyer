package com.pingjia.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pingjia.tools.Model;

public abstract class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -7191650949495347784L;
	//获取request对象
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	//assign的另一种写法
	public void setVal(String key, Object val) {
		ActionContext ctx = ActionContext.getContext();
		ctx.put(key, val);
	}
	//将验证功能强制给子类check方法判断，返回真伪值方便操作
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
	//提交成功或失败此类接口可返回该方法即可跳转到成功/失败页，并在seconds秒后跳转到url指定页面。不指定url则上一页，不指定seconds默认2秒
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
	//提交成功或失败此类接口可返回该方法即可跳转到成功/失败页，并在seconds秒后跳转到url指定页面。不指定url则上一页，不指定seconds默认2秒
	public String success(String str, String url, int seconds) {
		setVal("message", str);
		setVal("url", U(url));
		setVal("seconds", seconds + "");
		return INPUT;
	}
	//根据键直接获取前端传来的参数
	public String p(String key){
		return getRequest().getParameter(key);
	}
	//创建Model对象，使用M("Hibernate逆向类名")创建对象，详见com.pingjia.tools.Model类
	public Model M(String tablename){
		return new Model(tablename);
	}
	//根据 命名空间/action名 获取完整url，命名空间与action名请见struts.xml
	public String U(String struts){
		if(struts.startsWith("http://"))
			return struts;
		return getRequest().getContextPath()+"/"+struts;
	}
	//设置或调用session,单参数是获取，双参数是设置
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
	//向前端传值，前端只需要<s:property value="#键名" />即可调用
	public void assign(String key,Object value){
		setVal(key,value);
	}
}
