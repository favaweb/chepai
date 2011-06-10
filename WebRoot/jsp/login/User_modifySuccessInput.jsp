<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'User_loginInput.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript">
		function show(o){
			 //重载验证码,为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳   
			 var timenow = new Date().getTime();
			 o.src="servlet/image?d="+timenow;
			 /*
			 //超时执行;
			 setTimeout(function(){
			  o.src="random.jsp?d="+timenow;
			 }
			  ,20);
			  */
			}
	</script>
  </head>
  
  <body>
		<div class="login" align="center">

			<form action="login/Login!login.action" method="post" target="_parent">
				<table  cellpadding="1" cellspacing="1"  style="width: 500px;text-align: left;">
					<tr class="th">
						<td colspan="2">密码修改成功!请重新登陆!</td>
					</tr>
					<tr>
						<td>用户名</td>
						<td><input type="text" name="user.name" style="width: 150px;"></td>
					</tr>
					<tr>
						<td>密码</td>
						<td><input type="password" name="user.password" style="width: 150px;" /></td>
					</tr>
					<tr>
						<td>验证码</td>
						<td>
							<input type="text" name="validate" style="width: 150px;" />
							<img id="img" border=0 src="servlet/image">
							<a href="javascript: show(document.getElementById('img'))">重新获取</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" class="button" value="登陆" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
