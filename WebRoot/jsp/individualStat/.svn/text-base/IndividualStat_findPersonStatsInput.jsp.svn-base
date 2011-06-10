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
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;统计查询&nbsp;>&nbsp;个人产量统计</div>
		
		<table  cellpadding="1" cellspacing="1"  style="width: 800px;text-align: left;" align="center">
	   		<tr>
	   			<td>
	   				<form action="IndividualOutput!findUsersAll.action" method="post">
	   					<input type="submit" class="button" value="个人产量明细" />
	   				</form>
	   			</td>
	   		</tr>
	   	</table>
		
		<form action="IndividualOutput!findPersonStats.action" method="post" style="text-align: center;">
			<table  cellpadding="1" cellspacing="1"  style="width: 800px;">
		   		<tr>
		   			<td>
		   				时间段：<input type="text" name="biginTime"  value="<s:property value='biginTime'/>" onClick="WdatePicker()" class="Wdate"  />
		   				－<input type="text" name="endTime" value="<s:property value='endTime'/>" onClick="WdatePicker()" class="Wdate"  />
		   				<input type="submit" class="button" value="查询统计" />
		   			</td>
		   		</tr>
		   	</table>
		</form>
		
		<s:if test="outputStatses != null && outputStatses.size() > 0">
			<div>
				<table  cellpadding="1" cellspacing="1"  style="width: 800px;" align="center">
					<tr class="th">
						<td colspan="3"  style="color: red;font-size: 25px;height: 40px;line-height: 40px;">个人产量统计表</td>
					</tr>
					<tr class="th" style="text-align: right;">
						<td colspan="3" style="font-size: 12px;padding-right: 5px;">时间(<s:property value="biginTime"/><s:if test="endTime == null || endTime.equals('')"> 至今</s:if><s:else> 至<s:property value="endTime"/></s:else>)&nbsp;&nbsp;|&nbsp;单位：块</td>
					</tr>
					<tr class="th">
						<td>序号</td>
						<td>用户</td>
						<td>总产量</td>
					</tr>
					<s:iterator value="outputStatses" var="outputStats" status="status">
						<tr>
							<td>
								<s:property value="#status.index + 1"/>
							</td>
							<td>
								<s:property value="#outputStats.userName"/>
							</td>
							<td>
								<s:property value="#outputStats.output"/>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</s:if>


	</body>
</html>
