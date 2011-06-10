<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
   
   <a href="Privilege!addInput.action">增加权限</a><br/>
   <a href="Privilege!list.action">权限列表</a><br/><br/>
   
   
   <a href="Roler!addInput.action">添加角色</a><br/>
   <a href="Roler!list.action">角色列表</a><br/><br/>
   <a href="User!addInput.action">添加用户</a><br/>
   <a href="User!list.action">用户列表</a><br/>
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
  </body>
</html>
