<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<script type="text/javascript" src="js/calenderJS.js"></script>
  </head>
  
  <body>		
  	<div style="font-size: 14px;">
				<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;仓库物资管理&nbsp;>&nbsp;发货管理&nbsp;>&nbsp;发货地址</div>
		<span  style="font-size: 14px	;padding: 10px;text-align: center;" ><h4 style="text-align: center;">
			<a href="BigBatchNumber!findIsDeliverGoods.action">发货查询</a>
&nbsp;
			<a
				href="StatisticsInput!findFinishedList.action">发货统计</a>&nbsp;
			<a
				href="PostAddress!list.action">发货地址</a>&nbsp;
			<a
				href="StatisticsInput!boxstat.action">发货箱数</a>

		</h4></span>
  		<hr/>
  		<div class="in" align="center" style="margin-top:15px;">
			<input type="button" class="button" value="添加发货地址" align="left" onclick="location.href='PostAddress!addInput.action'"/>
	  		<table  cellpadding="1" cellspacing="1"  style="width:70%;" >
				<tr class="th">
					<td colspan="7" >发货地址列表</td>
				</tr>
				<tr>
					<td>
						地区
					</td>
					<td>
						车管所
					</td>
					<td>
						联系人
					</td>
					<td>
						联系电话
					</td>
					<td>
						地址
					</td>
					<td>
						备注
					</td>
					<td>
						操作
					</td>
				</tr>
				<s:iterator value="PostAddressList" var="postAddress">
					<tr>
						<td>
							<s:property value="#postAddress.place" />
						</td>
						<td>
							<s:property	value="#postAddress.department" />
						</td>
						<td>
							<s:property	value="#postAddress.receiver" />
						</td>
						<td>
							<s:property	value="#postAddress.telephone" />
						</td>
						<td>
							<s:property	value="#postAddress.address" />
						</td>
						<td>
							<s:property	value="#postAddress.remark" />
						</td>
						<td>
							<a href="PostAddress!detail.action?postAddress.id=<s:property value="#postAddress.id" />" >修改</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>

  </body>
</html>
