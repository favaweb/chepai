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
		
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;系统管理&nbsp;>&nbsp;更换角色权限</div>
		<form action="Roler!modifyPrivilege.action" method="post">
			<input type="hidden" name="roler.id"
				value="<s:property value='roler.id'/>" />
			
			
			<div>
				<table  cellpadding="1" cellspacing="1" >
					
					<tr class="th">
						<td colspan="3">角色名: <span style="color:red;"><s:property value="roler.name" /></span></td>
					</tr>
					<tr class="th">
						<td>
							选择
						</td>
						<td>
							模块名称
						</td>
						<td>
							访问地址
						</td>
					</tr>
	
					<s:iterator value="privileges" var="privilege">
	
						<tr>
							<td>
								<s:if test="roler.privileges.contains(#privilege)">
									<input style="width: 25px;height:25px;" type="checkbox" name="modifyPrivileges"
										value="<s:property value='#privilege.id'/>" checked="checked">
								</s:if>
								<s:else>
									<input style="width: 25px;height:25px;" type="checkbox" name="modifyPrivileges"
										value="<s:property value='#privilege.id'/>">
								</s:else>
							</td>
							<td>
								<s:property value="#privilege.model" />
							</td>
							<td>
								<s:property value="#privilege.url" />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<input type="submit" class="button" value="修改权限" />
		</form>













	</body>
</html>
