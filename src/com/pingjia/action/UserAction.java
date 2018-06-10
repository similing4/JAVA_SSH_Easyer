package com.pingjia.action;

import java.util.List;

import com.pingjia.hibernate.User;
import com.pingjia.tools.DB_Where;

public class UserAction extends BaseAction {
	private static final long serialVersionUID = -291901314554100442L;

	public String login() {
		//只有页面的直接返回SUCCESS就行了。
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
		DB_Where where = new DB_Where();//DB_Where对象用于select的条件处理
		where.setWhere("username", p("username"));
		where.setWhere("password", p("password"));
		List<?> lists = M("User").where(where).select();//设置where条件后执行查询操作
		if(lists.size()==0){//没查到
			return error("用户名或密码错误");//返回错误页，前端会跳转到错误页并显示错误信息跳转到提交到本页面的页面
		}else{
			User u = (User)lists.get(0);//取出用户
			session("UD",u);//设置session
			if(u.getPower()==1)
				return success("登录成功","Index/index");//跳转到Index命名空间下的index方法
			else
				return success("登录成功","Home/index");//跳转到Home命名空间下的index方法
		}
	}
	public String checkReg() {
		DB_Where where = new DB_Where();
		where.setWhere("username", p("username"));
		List<?> lists = M("User").where(where).select();
		if(lists.size()!=0){
			return error("用户名已存在");
		}else{
			User user = new User();//新建Hibernate实体对象
			user.setUsername(p("username"));//设置参数
			user.setPassword(p("password"));
			user.setPower(0);
			if(M("User").add(user))//直接M(类名).add方法添加就行了
				return success("注册成功","User/login");
			else
				return error("注册失败","User/reg");
		}
	}
	@Override
	public boolean check() {
		// 没有权限验证的地方直接返回true。
		return true;
	}
}
