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
		
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;生产流程管理&nbsp;>&nbsp;设置流程默认领取批数</div>
		<hr/>
		<div>
			<table  cellpadding="1" cellspacing="1" >
				<tr class="th">
					<td>
						流程名称
					</td>
					<td>
						默认值
					</td>
					<td>
						最大获取批数
					</td>
					<td>
						上次修改时间
					</td>
					<td>
						操作
					</td>
				</tr>
	
				<s:iterator value="taskInstalls" var="t">
					<tr>
						<td>
							<s:property value="#t.flowType.typeName" />
						</td>
						<td>
							<s:property value="#t.defaultNumber" />
						</td>
						<td>
							<s:property value="#t.number" />
						</td>
						<td>
							<s:date name="#t.installDate" format="yyyy-MM-dd hh:mm:ss"/>
						</td>
						<td>
							<a href="TaskInstall!modifyInput.action?taskInstall.id=<s:property value='#t.id'/>">修改</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>











	</body>
</html>
