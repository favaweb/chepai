<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<table  cellpadding="1" cellspacing="1"  border="1">
			<tr>
				<td>
					模块名称
				</td>
				<td>
					访问地址
				</td>
				<td>
					父级模块
				</td>
			</tr>


			<s:iterator value="privileges" var="privilege">
				<tr>
					<td>
						<s:property value="#privilege.model" />
					</td>
					<td>
						<s:property value="#privilege.url" />
					</td>
					<td>
						<s:if test="#privilege.code == 1">系统管理</s:if>
						<s:elseif test="#privilege.code == 2">生产单管理</s:elseif>
						<s:elseif test="#privilege.code == 3">生产流程管理</s:elseif>
						<s:elseif test="#privilege.code == 4">仓库物资管理</s:elseif>
						<s:else>查询统计</s:else>
					</td>
				</tr>
			</s:iterator>
		</table>












	</body>
</html>
