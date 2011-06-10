<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>

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
		
		<div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;系统管理&nbsp;&gt; 员工管理</div>
		<hr/>
		<div>
			<table  cellpadding="1" cellspacing="1" >
				<tr class="th">
					<td>
						流程类型
					</td>
					<td>
						操作人
					</td>
				</tr>
	
				<%
					List<Operator> operators = (List<Operator>)request.getAttribute("operatores");
					List<FlowType> flowTypes = (List<FlowType>)request.getAttribute("flowTypes");
					for(Operator o : operators) {
				%>
				
					<tr>
						<td>
							<%
								for(FlowType ft : flowTypes) {
									if(ft.getId() == o.getFlowType().getId()) {
										out.print(ft.getTypeName());
									}
								}
							 %>
						</td>
						<td>
							<%
								for(Users u : o.getUsers()) {
									out.print(u.getName() + "&nbsp;&nbsp;");
								}
							 %>
						</td>
					</tr>
				<%
					}
				 %>
			</table>
		<input type="button" class="button" value="返回" onclick="javascript:window.history.go(-1);"/><br/>
		</div>





	</body>
</html>
