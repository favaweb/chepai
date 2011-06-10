<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'User_loginInput.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  </head>
  
  <body>
  	<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;修改用户密码</div>

	<form action="User!modifyPassword.action" method="post" target="_parent" >
		<input type="hidden" name="user.id" value="<s:property value='#session.user.id'/>"/>
		
		<div style="text-align: center;">
			<table  cellpadding="1" cellspacing="1"  style="width: 500px;">
				<tr class="th">
					<td colspan="2">修改密码</td>
				</tr>
				<tr>
					<td>用户名</td>
					<td><input type="text" name="user.name" value="<s:property value="#session.user.name"/>" readonly="readonly"></td>
				</tr>
				<tr>
					<td>当前密码</td>
					<td><input type="password" name="nowPassword" /></td>
				</tr>
				<tr>
					<td>新密码</td>
					<td><input type="password" name="modifyPassword" /></td>
				</tr>
				<tr>
					<td>确认密码</td>
					<td><input type="password" name="confirmPassword" /></td>
				</tr>
			</table>
			<input type="submit" class="button" value="修改密码" />
		</div>
	</form>
			
			
			
			
			
			
			
			
			
			
			
			
	</body>
</html>
