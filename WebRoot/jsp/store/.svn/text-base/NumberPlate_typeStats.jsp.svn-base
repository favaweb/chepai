<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
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
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;仓库物资管理&nbsp;>&nbsp;仓库统计&nbsp;>&nbsp;半成品废牌统计</div><br/>
<span  style="font-size: 14px	;padding: 10px;text-align: center;" ><h4 style="text-align: center;" >
	<a href="StatisticsInput!findList.action">半成品出入库统计</a>&nbsp;
	<a href="NumberPlate!typeStats.action">半成品废牌统计</a>&nbsp;
	<a href="DisuseMaterial!stats.action">废牌统计</a>&nbsp;
	<a href="DisuseMaterial!addInput.action">添加废牌</a>
</h4>
</span>
		<hr />
		<form method="post" action="NumberPlate!typeStats.action" style="text-align: center;">
			时间：<input type="text" name="dateTime"  value="<s:property value='dateTime'/>" onClick="WdatePicker()" class="Wdate" />
			<input type="submit" value="查询" class="button" />
		</form>
		
		<table  cellpadding="1" cellspacing="1"  style="width:600px;" align="center">
			<tr class="th">
				<td colspan="5" style="color: red;">半成品废牌统计表：时间(<s:if test="dateTime != null && !dateTime.equals('')"><s:property value='dateTime'/></s:if><s:else><%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%></s:else>)</td>
			</tr>
			<tr class="th">
				<td>车牌类型</td>
				<td>规格</td>
				<td>半成品产量</td>
				<td>废牌数</td>
				<td>总量</td>
			</tr>
		<s:if test="typeStatses == null || typeStatses.size() == 0">
			<tr>
				<td colspan="5">没有任何记录</td>
			</tr>
		</s:if>
		<s:iterator value="typeStatses" var="stats">
			<tr>
				<td>
					<s:property value="#stats.typeName"/>
				</td>
				<td>
					<s:property value="#stats.standard"/>
				</td>
				<td>
					<s:property value="#stats.totle"/>
				</td>
				<td>
					<s:property value="#stats.number"/>
				</td>
				<td>
					<s:property value="#stats.totle + #stats.number"/>
				</td>
			</tr>
		</s:iterator>
		</table>











	</body>
</html>
