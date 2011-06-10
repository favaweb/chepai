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
		<script type="text/javascript">
			function isSubmit() {
				if(window.confirm("确认修改?")) {
					document.getElementById("myform").submit();
				}
			}
		</script>
	</head>

	<body>
		
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;生产流程管理&nbsp;>&nbsp;设置流程默认领取批数</div>
		<hr/>
		
		<form action="TaskInstall!modify.action" method="post" id="myform">
			<input type="hidden" value="<s:property value='taskInstall.id' />" name="taskInstall.id" />
			<input type="hidden" value="<s:property value='taskInstall.flowType.id' />" name="taskInstall.flowType.id" />
			<div>
				<table  cellpadding="1" cellspacing="1" style="width: 500px;" >
					<tr>
						<td>
							流程名称
						</td>
						<td>
							<s:property value="taskInstall.flowType.typeName" />
						</td>
					</tr>
					<tr>
						<td>
							默认领取批数
						</td>
						<td>
							<input type="text" name="taskInstall.defaultNumber" value="<s:property value='taskInstall.defaultNumber' />" />
						</td>
					</tr>
					<tr>
						<td>
							最大领取批数
						</td>
						<td>
							<input type="text" name="taskInstall.number" value="<s:property value='taskInstall.number' />" />
						</td>
					</tr>
		
				</table>
				<input type="button" value="修改" class="button" onclick="isSubmit()" />
			</div>
		</form>











	</body>
</html>
