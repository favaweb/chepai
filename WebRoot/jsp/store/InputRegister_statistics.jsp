<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
  </head>
  
  <body>
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;仓库物资管理&nbsp;>&nbsp;仓库统计&nbsp;>&nbsp;半成品出入库统计</div><br/>
<span  style="font-size: 14px	;padding: 10px;text-align: center;" ><h4 style="text-align: center;" >
	<a href="StatisticsInput!findList.action">半成品出入库统计</a>&nbsp;
	<a href="NumberPlate!typeStats.action">半成品废牌统计</a>&nbsp;
	<a href="DisuseMaterial!stats.action">废牌统计</a>&nbsp;
	<a href="DisuseMaterial!addInput.action">添加废牌</a>
</h4>
</span>
<hr/>
<div style="margin-top:20px;margin-bottom:20px:">
<form method="post" action="StatisticsInput!findListByCondition.action" style="text-align:center;">
开始时间：<input type="text" name="startDate"  onClick="WdatePicker()" class="Wdate" />
结束时间：<input type="text" name="endDate"  onClick="WdatePicker()" class="Wdate" />
类型：<select name="typeid">
			<option value="0"> 
				未选择
			</option>
			<s:iterator value="semifinishedProductTypes" var="semifinishedProductType">
				<option value="<s:property value='#semifinishedProductType.id'/>">
					<s:property value="#semifinishedProductType.typeName"/>
				</option>
			</s:iterator>
		</select>
		<input type="submit" value="查找记录" class="button" />
		</form>
		</div>
<div align="center" style="margin-top:15px;" >
<s:if test="statisticsInputs!=null">
  <table style="width:80%;">
  <tr class="th">
  	<td colspan="6">
  	半成品出入库统计
  	</td>
 
  </tr>
			<tr class="th">
				<td>
					序号
				</td>
				<td>
					日期
				</td>
				<td>
					类别
				</td>
				<td>
					规格
				</td>
				<td> 
					入库数量（块） 
				</td>
				<td> 
					出库数量（块） 
				</td>
			</tr>
			<s:if test="statisticsInputs.size==0"><tr><td colspan="6" align="center">没有记录</td></tr></s:if>
			<s:iterator value="statisticsInputs" var="statisticsInput" status="status">
				<tr>
					<td>
						<s:property value="#status.index"/>
					</td>
				
					<td>
					<s:date name="#statisticsInput.date" format="yyyy-MM-dd" />
						
					</td>
					<td>
						<s:property value="#statisticsInput.type.typeName" />
					</td>
					<td>
						<s:property	value="#statisticsInput.standard" />
					</td>
					<td>
						<s:property	value="#statisticsInput.inCount" />
					</td>
					<td>
						<s:property value="#statisticsInput.outCount" />
					</td>
				</tr>
				
			</s:iterator>
		</table>
		</s:if>
		</div>

		
  </body>
</html>
