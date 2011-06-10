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
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
 <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;仓库物资管理&nbsp;>&nbsp;仓库统计&nbsp;>&nbsp;废牌统计</div><br/>
<span  style="font-size: 14px	;padding: 10px;text-align: center;" ><h4 style="text-align: center;" >
	<a href="StatisticsInput!findList.action">半成品出入库统计</a>&nbsp;
	<a href="NumberPlate!typeStats.action">半成品废牌统计</a>&nbsp;
	<a href="DisuseMaterial!stats.action">废牌统计</a>&nbsp;
	<a href="DisuseMaterial!addInput.action">添加废牌</a>
</h4>
</span>
		<hr />
		<form method="post" action="DisuseMaterial!stats.action" style="text-align: center;">
			时间段：<input type="text" name="biginTime"  value="<s:property value='biginTime'/>"   onClick="WdatePicker()" class="Wdate" readonly="readonly" />
			－<input type="text" name="endTime" value="<s:property value='endTime'/>" onClick="WdatePicker()" class="Wdate" readonly="readonly" />
			<input type="submit" value="查询" class="button" />
		</form>
		<br/>
		
		<table  cellpadding="1" cellspacing="1"  style="width:600px;" align="center">
			<tr class="th">
				<td colspan="3" style="color: red;">废牌统计表</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: right;padding-right: 5px;">时间(<s:property value="biginTime"/>至<s:property value="endTime"/>)</td>
			</tr>
			<tr class="th">
				<td>序号</td>
				<td>损坏类型</td>
				<td>数量</td>
			</tr>
		<s:if test="statses == null || statses.size() == 0">
			<tr>
				<td colspan="3">没有任何记录</td>
			</tr>
		</s:if>
		<s:iterator value="statses" var="stats" status="s">
			<tr>
				<td>
					<s:property value="#s.index + 1"/>
				</td>
				<td>
					<s:if test="#stats.marType == 1">废品</s:if>
					<s:if test="#stats.marType == 2">半成品</s:if>
					<s:if test="#stats.marType == 4">试机</s:if>
					<s:if test="#stats.marType == 5">样牌</s:if>
					<s:if test="#stats.marType == 6">重复车牌</s:if>
					<s:if test="#stats.marType == 7">漏制车牌</s:if>
				</td>
				<td>
					<s:property value="#stats.stats"/>
				</td>
			</tr>
		</s:iterator>
		</table>











	</body>
</html>
