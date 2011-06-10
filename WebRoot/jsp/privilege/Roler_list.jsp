<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;系统管理&nbsp;>&nbsp;角色管理</div>
		<form action="Roler!add.action" method="post" style="text-align: center;">
			<s:token></s:token>
		   	<table  cellpadding="1" cellspacing="1"  style="width: 500px;">
		   		<tr>
		   			<td>角色名称：
		   			<input type="text" name="roler.name">
		   			<input type="submit" class="button" value="添加角色" /></td>
		   		</tr>
		   	</table>
   		</form>
		
		<hr/>
		<div style="text-align: center;">
			<table  cellpadding="1" cellspacing="1" style="width: 500px;" >
				<tr class="th">
					<td>
						序号
					</td>
					<td>
						角色名称
					</td>
					<td colspan="2">
						操作
					</td>
				</tr>
	
				<s:iterator value="rolers" var="roler" status="status">
					<tr>
						<td>
							<s:property value="#status.index + 1"/>
						</td>
						<td>
							<s:property value="#roler.name" />
						</td>
						<td>
							<a
								href="Roler!modifyPrivilegeInput.action?roler.id=<s:property value='#roler.id'/>">修改权限</a>
						</td>
						<td>
							<a href="Roler!delete.action?roler.id=<s:property value='#roler.id'/>">删除</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>












	</body>
</html>
