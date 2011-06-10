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
				var place = document.getElementById("place").value;
				var receiver = document.getElementById("receiver").value;
				var telephone = document.getElementById("telephone").value;
				var address = document.getElementById("address").value;
				if(place == null || place == "" ) {
					alert("请填写地区");
				}else if(receiver == null || receiver == "" ) {
					alert("请填写联系人");
				} else if(telephone == 0 || telephone == "" || telephone == null) {
					alert("请填写电话");
				} else if(address == null || address == "") {
					alert("请填写地址");
				} else {
					document.getElementById("myform").submit();
				}
			}
		</script>
	</head>

	<body>
	<div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt;&nbsp;发货管理&nbsp;&gt; 发货地址详情<br></div><br/>
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
<hr />
		<form method="post" action="PostAddress!modify.action?postAddress.id=<s:property value='postAddress.id' />" id="myform" style="text-align: center;">
			<s:token></s:token>
			<table cellpadding="1" cellspacing="1" style="width: 500px;" align="center">
				<tr class="th">
					<td colspan="2">发货地址详情</td>
				</tr>
				<tr>
					<td>车管所</td>
					<td>
						<input type="text" id="department" name="postAddress.department"
						 value="<s:property value='postAddress.department' />" />
					</td>
				</tr>
				<tr>
					<td>地区</td>
					<td>
						<input type="text" id="place" name="postAddress.place"
						value="<s:property value='postAddress.place' />" />
					</td>
				</tr>
				<tr>
					<td>联系人<br /></td>
					<td>
						<input type="text" name="postAddress.receiver" id="receiver" 
						value="<s:property value="postAddress.receiver" />" />
					</td>
				</tr>
				<tr>
					<td>电话<br />
					</td>
					<td><input type="text" name="postAddress.telephone" id="telephone" 
					value="<s:property value="postAddress.telephone" />" />
					</td>
				</tr>
				<tr>
					<td>地址<br /></td>
					<td>
					<input type="text" name="postAddress.address" id="address"
					value="<s:property value="postAddress.address" />" />
					</td>
				</tr>
				<tr>
					<td>备注<br /></td>
					<td>
					<input type="text" name="postAddress.remark" id="remark"
					value="<s:property value="postAddress.remark" />" />
					</td>
				</tr>
			</table>
			<input type="button" class="button" value="修改" onclick="submitForm();" />
			<input type="reset" class="button" value="恢复" />
		</form>


	</body>
</html>
