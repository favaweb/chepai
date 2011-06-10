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

  </head>
  
  <body>
<div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt;&nbsp;半成品管理&nbsp;&gt;&nbsp;半成品类型</div><br/>
		<span style="font-size: 14px; padding: 10px; text-align: center;"><h4
				style="text-align: center;">
				<a href="SemifinishedProduct!findInputList.action">出入库记录</a>&nbsp;

				<a
					href="SemifinishedProduct!operateInput.action?inputRegister.type=1">入库</a>&nbsp;

				<a
					href="SemifinishedProduct!operateInput.action?inputRegister.type=2">出库</a>&nbsp;

				<a href="SemifinishedProduct!findTypeList.action">半成品类型</a>&nbsp;
				
				<a href="SemifinishedProduct!reserveList.action">剩余库存</a>

			</h4>
		</span>
		<hr/>
		<div style="margin-top:20px;margin-bottom:20px;text-align: center;">

  
  </div>
<div align="center" style="margin-top:15px;" >
  
  <table  cellpadding="1" cellspacing="1"  style="width:50%;">
			<tr class="th">
			<td colspan="2">
				半成品类型列表
				</td>
			</tr>
			<tr class="th">
				<td>
					半成品类型名称
				</td>
				<td>
					规格
				</td>
			</tr>
			<s:iterator value="semifinishedProductTypes" var="semifinishedProductType">
				<tr>
					<td>
						<s:property value="#semifinishedProductType.typeName" />
					</td>
					<td>
						<s:property	value="#semifinishedProductType.standard" />
					</td>
				</tr>
			</s:iterator>
		</table>
</div>
  </body>
</html>
