<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  </head>
  
  <body>
   
   <div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;系统管理&nbsp;&gt;&nbsp;修改员工基本资料</div>
		<form action="User!modifyInfo.action" method="post">
			<input type="hidden" value="<s:property value='user.id' />" name="user.id">
			<input type="hidden" value="<s:property value='user.password' />" name="user.password">
		   	<table  cellpadding="1" cellspacing="1"  style="width: 800px;">
		   		<tr class="th">
		   			<td colspan="2">修改基本资料</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				账户
		   			</td>
		   			<td>
		   				<input type="text" name="user.account" value="<s:property value="user.account" />">
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				姓名
		   			</td>
		   			<td>
		   				<input type="text" name="user.name" value="<s:property value="user.name" />">
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				性别
		   			</td>
		   			<td>
		   				男<input <s:if test="user.sex == true">checked='checked'</s:if> type="radio" value="true" name="user.sex">女<input <s:if test="user.sex == false">checked='checked'</s:if> type="radio" value="false" name="user.sex">
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				值班类型
		   			</td>
		   			<td>
		   				白班<input <s:if test="user.dayNight == 1">checked='checked'</s:if> type="radio" value="1" name="user.dayNight">夜班<input <s:if test="user.dayNight == 0">checked='checked'</s:if> type="radio" value="0" name="user.dayNight">
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				是否启用
		   			</td>
		   			<td>
		   				是<input <s:if test="user.isvalid == 1">checked='checked'</s:if> type="radio" value="1" name="user.isvalid">否<input <s:if test="user.isvalid == 0">checked='checked'</s:if> type="radio" value="0" name="user.isvalid">
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>岗位 
		   			</td>
		   			<td>
		   				<select name="user.roler"> 
	   						<option value="0" <s:if test="user.roler == 0">selected="selected"</s:if>>普通员工</option>
	   						<option value="1" <s:if test="user.roler == 1">selected="selected"</s:if>>组长</option>
	   						<option value="2" <s:if test="user.roler == 2">selected="selected"</s:if>>跟单员</option>
	   						<option value="3" <s:if test="user.roler == 3">selected="selected"</s:if>>管理员</option>
	   					</select>
		   			</td>
		   		</tr>
		   	</table>
		   	<input type="submit" class="button" value="修改员工信息" />
   		</form>
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
  </body>
</html>
