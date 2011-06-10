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
		<script type="text/javascript">
			function submitForm() {
				var nType = document.getElementById("nType");
				var sType = document.getElementById("sType");
				var amount = document.getElementById("amount");
				if(nType.value == 0) {
					alert("请选择车牌类型");
				} else if(sType.value == 0) {
					alert("请选择半成品类型");
				} else if(amount.value == null || amount.value == 0) {
					alert("请选择数量");
				} else {
					document.getElementById("myform").submit();
				}
			}
		</script>
	</head>

	<body>
	<div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt;&nbsp;仓库统计&nbsp;&gt;&nbsp;添加废牌<br></div><br/>
<span  style="font-size: 14px	;padding: 10px;text-align: center;" ><h4 style="text-align: center;" >
	<a href="StatisticsInput!findList.action">半成品出入库统计</a>&nbsp;
	<a href="NumberPlate!typeStats.action">半成品废牌统计</a>&nbsp;
	<a href="DisuseMaterial!stats.action">废牌统计</a>&nbsp;
	<a href="DisuseMaterial!addInput.action">添加废牌</a>
</h4>
</span>
<hr />
		<form method="post" action="DisuseMaterial!add.action" id="myform" style="text-align: center;">
			<s:token></s:token>
			<table cellpadding="1" cellspacing="1" style="width: 500px;" align="center">
				<tr class="th">
					<td colspan="2">添加废牌</td>
				</tr>
				<tr>
					<td>损坏类型</td>
					<td>
						<select name="disuseMaterial.marType">
							<option value="2">半成品</option>
							<option value="4">试机</option>
							<option value="5">样牌</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>车牌类型</td>
					<td>
						<select name="disuseMaterial.numberPlateType.id" id="nType">
							<option value="0">--请选择--</option>
							<s:iterator value="numberPlateTypes" var="n">
								<option value="<s:property value="#n.id"/>"><s:property value="#n.typeName"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td>半成品类型</td>
					<td>
						<select name="disuseMaterial.semifinishedProductType.id" id="sType">
							<option value="0">--请选择--</option>
							<s:iterator value="semifinishedProductTypes" var="s">
								<option value="<s:property value="#s.id"/>"><s:property value="#s.typeName"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td>时间</td>
					<td><input type="text" name="biginTime"  onClick="WdatePicker()" class="Wdate" /></td>
				</tr>
				<tr>
					<td>数量</td>
					<td><input type="text" name="amount" id="amount"/></td>
				</tr>
			</table>
			<input type="button" class="button" value="添加废牌" onclick="submitForm()" />
		</form>
		



	</body>
</html>
