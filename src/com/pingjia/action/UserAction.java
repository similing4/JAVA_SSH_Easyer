package com.pingjia.action;

import java.util.List;

import com.pingjia.hibernate.User;
import com.pingjia.tools.DB_Where;

public class UserAction extends BaseAction {
	private static final long serialVersionUID = -291901314554100442L;

	public String login() {
		return SUCCESS;
	}
	public String reg() {
		return SUCCESS;
	}
	public String logout(){
		session("UD",null);
		return success("注销成功","User/login");
	}
	public String checkLogin() {
		DB_Where where = new DB_Where();
		where.setWhere("username", p("username"));
		where.setWhere("password", p("password"));
		List<?> lists = M("User").where(where).select();
		if(lists.size()==0){
			return error("用户名或密码错误");
		}else{
			User u = (User)lists.get(0);
			session("UD",u);
			if(u.getPower()==1)
				return success("登录成功","Index/index");
			else
				return success("登录成功","Home/index");
		}
	}
	public String checkReg() {
		DB_Where where = new DB_Where();
		where.setWhere("username", p("username"));
		List<?> lists = M("User").where(where).select();
		if(lists.size()!=0){
			return error("用户名已存在");
		}else{
			User user = new User();
			user.setUsername(p("username"));
			user.setPassword(p("password"));
			user.setPower(0);
			if(M("User").add(user))
				return success("注册成功","User/login");
			else
				return error("注册失败","User/reg");
		}
	}
	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}
}
