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
   
   <form action="Privilege!add.action" method="post">
   	模块名称:<input type="text" name="privilege.model"><br/>
   	访问地址:<input type="text" name="privilege.url"><br/>
   	父级类型:<select name="privilege.code">
   				<option value="1">系统管理</option>
   				<option value="2">生产单管理</option>
   				<option value="3">生产流程管理</option>
   				<option value="4">仓库物资管理</option>
   				<option value="5">查询统计</option>
   				<option value="0">没有父级别</option>
   			</select>
   	<input type="submit" value="添加权限" />
   </form>
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
  </body>
</html>
