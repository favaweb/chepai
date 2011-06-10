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
		
		
		<s:if test="operators != null && operators.size() != 0">
			<table  cellpadding="1" cellspacing="1"  border="1">
			<tr>
				<td>
					大批号
				</td>
				<td>
					小批号
				</td>
				<td>
					地区
				</td>
				<td>
					类型
				</td>
				<td>
					优先级
				</td>
				<td>
					箱号
				</td>
			</tr>
			<tr>
				<td>
					<s:if test="operators[0].bigBatchNumber == null">
						<s:if test="operators[0].smallnessBatchNumber.isRemakes == 1">
							<span style="color:red;">补牌</span>
						</s:if>
						<s:elseif test="operators[0].smallnessBatchNumber.isRemakes == 2">
						   	<span style="color:red;">合并</span>
						</s:elseif>
						&nbsp;
					</s:if>
					<s:else>
						<s:property value="operators[0].smallnessBatchNumber.bigBatchNumber.bigBattchNumber" />
					</s:else>
				</td>
				<td>
					<s:property value="operators[0].smallnessBatchNumber.smallnessBatchNumber" />
				</td>
				<td>
					<s:if test="operators[0].smallnessBatchNumber.place == null">
						<span style="color:red;">混合地区</span>
					</s:if>
					<s:else>
						<s:property value="operators[0].smallnessBatchNumber.place" />
					</s:else>
				</td>
				<td>
					<s:if test="operators[0].smallnessBatchNumber.orderType == 1">补制</s:if>
					<s:elseif test="operators[0].smallnessBatchNumber.orderType == 2">自选</s:elseif>
					<s:else>号段</s:else>
				</td>
				<td>
					<s:if test="operators[0].smallnessBatchNumber.precedence == 1">
						<span style="color: red;">高</span>
					</s:if>
					<s:elseif test="operators[0].smallnessBatchNumber.precedence == 2">
						<span style="color: red;">中</span>
					</s:elseif>
					<s:else>
						<span style="color: red;">低</span>
					</s:else>
				</td>
				<td>
					<s:property value="operators[0].smallnessBatchNumber.boxNumber" />
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="operators != null && operators.size() != 0">
		<table  cellpadding="1" cellspacing="1"  border="1">
			<tr>
				<td>流程类型</td>
				<td>操作人</td>
			</tr>
			<s:iterator value="operators" var="operator">
				<tr>
					<td>
						<s:property value="#operator.flowType.typeName"/>
					</td>
					<td>
						<s:iterator value="#operator.users" var="user">
							<s:property value="#user.name"/> | 
						</s:iterator>
					</td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
	
	
		
	</body>
</html>
