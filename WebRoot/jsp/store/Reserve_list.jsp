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
  	<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;仓库物资管理&nbsp;>&nbsp;半成品管理&nbsp;>&nbsp;剩余库存<br/>
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
  		<div class="in" align="center" style="margin-top:15px;">
	  		<table  cellpadding="1" cellspacing="1"  style="width:70%;" >
				<tr class="th">
					<td colspan="3" >库存</td>
				</tr>
  	</td>
				<tr>
					<td>
						半成品类型名称
					</td>
					<td>
						规格
					</td>
					<td>
						剩余数量（块）
					</td>
				</tr>
				<s:iterator value="reserves" var="reserve">
					<tr>
						<td>
							<s:property value="#reserve.semifinishedProductType.typeName" />
						</td>
						<td>
							<s:property	value="#reserve.standard" />
						</td>
						<td>
							<s:property	value="#reserve.remainder" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>

  </body>
</html>
