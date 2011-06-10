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

		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<META http-equiv=Content-Type content="text/html; charset=gbk" />
		<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

table {
	width: 100%;
	text-align: center;
}

tr {
	
}
</style>

<SCRIPT type="text/javascript"> 
function printsetup(){  
 wb.execwb(8,1); // 打印页面设置
} 
function printpreview(){  
 wb.execwb(7,1);// 打印页面预览
} 
function printit() { 
  //wb.execwb(6,1);
  window.print();
  window.close();
} 
　　</SCRIPT>
<!--media=print 这个属性说明可以在打印时有效-->
<!--希望打印时不显示的内容设置class="Noprint"样式-->
<!--希望人为设置分页的位置设置class="PageNext"样式-->
<style media="print">
<!--
.Noprint{display:none;}
.PageNext{page-break-after:always;}
-->
</style>

	</head>
	<body onload="javascript:printit();">
	<input type="button" value="打印" onclick="javascript:printit();" class="Noprint">
<OBJECT id="wb" height="0" width="0" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb"></OBJECT>
		<form>
		<table cellpadding="0" cellspacing="0" border="1" >
			<tr>
				<td colspan="4" style="font-weight: bold;">
					省厅制证中心
					<s:property value="smallnessBatchNumber.smallnessBatchNumber" />
					
				</td>
			</tr>
			<s:iterator value="numberPlates" var="numberPlate">
				<tr style="font-size: 14px;">
					<td width="10%">
						<s:property value="#numberPlate.orderNumber" />
					</td>
					<td width="20%">
						<s:property value="#numberPlate.businessDepartment.department" />
					</td>
					<td width="30%">
						<s:property value="#numberPlate.numberPlateType.typeName" />
					</td>
					<td style="font-weight: bold;">
						<s:property value="#numberPlate.licensePlateNumber" />
					</td>
				</tr>
			</s:iterator>
		</table>
		</form>
	</body>
</html>
