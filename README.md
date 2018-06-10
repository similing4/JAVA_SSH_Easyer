# JAVA_SSH_Easyer
SSH框架的部分简单封装，操作更简易方便
# 安装方法
正常建立一个javaweb项目后，添加Hibernate到项目中。逆向生成中只选中DataObject，后面内容根据自己的数据库设置即可（我用的mysql，id generator选identity）。添加BaseAction.java到你的Action包中，你的Action继承自此action并重写check方法。将com.pingjia.tools包加入你的项目中。将WebRoot下Public文件夹下的error.jsp与success.jsp加入你的项目中。xml中每个package加入下方代码就可以了。
```
<global-results>
	<result name="input">/Public/success.jsp</result>
	<result name="error">/Public/error.jsp</result>  
</global-results>
```
# Action方面的精简
让Action继承自BaseAction即可使用来自BaseAction的方法：
## M方法
仿TP的M方法，M("实体类名")即可对该实体的表进行操作：
### 查询
#### 查询全部
```
M("实体类名").select(); //查询全部
```
#### 根据条件查询
```
DB_Where where = new DB_Where();
where.setWhere("属性名", 属性值);//查询条件
M("实体类名").where(where).select(); //根据条件查询
```
#### 根据主键查询
```
M("实体类名").find(主键id);
```
### 增加
创建实体的对象后使用add方法即可添加到数据库
```
M("实体类名").add(实体对象);
```
### 修改
必须先查询到指定结果后再将查询结果的实体对象用于修改：
```
User u = M("实体类名").find(uid);
u.setPassword("123");
M("实体类名").save(u);
```
### 删除
已知主键id即可：
```
M("实体类名").delete(实体主键id);
```
## assign/setVal方法
assign方法主要是用来简化操作的，是以下内容的简化，assign后数据前端s标签就可以拿到了：
```
ActionContext ctx = ActionContext.getContext();
ctx.put(key, val);
```
## check方法
check方法是validate的简写。
因为validate返回值是void，错误需要addFieldError。而一般业务验证需要的是真伪判断。于是封装了一下。
因为引入了success与error方法，多了个提示页面，于是我把validate需要的验证部分给了一个抽象方法让子类实现，验证失败就返回false：
```
@Override
public boolean check() {
  if (session("UD") != null)
		return true;
  error("请登录", "User/login");
  return false;
}
```
## error/success方法
这两个方法作为Action返回值使用(check方法除外)，简化一般返回值只有SUCCESS，success("...")，error("...")：
(如需指定url可用 “命名空间”+“/”+“action名” 作为第二个参数)
```
public String delete(){
  String id = p("id");
  int iid = 0;
  try {
    iid=Integer.parseInt(id);
  } catch (Exception e) {
    e.printStackTrace();
    return error("删除失败，参数错误");
  }
  if(M("User").delete(iid))
    return success("删除成功");
  return error("删除失败");
}
```
## p方法
直接获取前端传来的参数：
```
String username = p('username');
```
## U方法
根据struts.xml中规定的命名空间与action名获取完整url：
```
String url = U("User/login");
```
## session方法
获取或设置session，一般用来存储登录状态：
```
List<?> us = M("User").where(where).select();
session("UD",(User)us.get(0));
User u = (User)session("UD");
```
