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

		<title>反压</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript">
		
		function checkOperator(select) {
			var changeOperators = document.getElementsByName(select);
			var index = 0;
			for(var i=0; i<changeOperators.length;i++) {
				if(changeOperators[i].checked == true) {
					index = index + 1;
				}
			}
			if(index != 2) {
				alert("任务调配只能是俩组人");
			} else {
				document.getElementById("formOperator").submit();
			}
		}
		
		function subForm(urlStr) {
			var myform = document.getElementById("myform");
			myform.action = urlStr;
			myform.submit();
		}
		
  </script>
	</head>

	<body>
	
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="SmallnessBatchNumber!queryPrepareWork.action">任务调配</a></div>
	
		<form id="myform" method="post">
					
			<div class="saixuan">
				流程：
				<select name="flowTypeId">
					<s:iterator value="flowTypes" var="flowType">
						<s:if test="#flowType.id < 8">
							<option <s:if test="#flowType.id == flowTypeId">selected="selected"</s:if> value='<s:property value="#flowType.id"/>'><s:property value="#flowType.typeName"/></option>
						</s:if>
					</s:iterator>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input  type="button" class="button" onclick="subForm('SmallnessBatchNumber!queryPrepareOpeartor.action')" value="查询" />
				<input  type="button" class="button" onclick="subForm('TaskInstall!list.action')" value="流程默认批数" />
			</div>
		</form>
			<hr />
			
		<form id="formOperator" method="post" action="SmallnessBatchNumber!findPrepareWork.action">
		<input type="hidden" name="flowTypeId" value="<s:property value='flowTypeId'/>" />
		<div>
		<table  cellpadding="1" cellspacing="1" style="width: 500px;" >
			<tr class="th">
				<td>
					请选择
				</td>
				<td>
					员工
				</td>
			</tr>
			
			<s:iterator value="taskAllocations" var="t">
				<tr>
					<td><input style="width: 25px;height:25px;" name="operators" type="checkbox" value="<s:property value="#t.operator" />"/></td>
					<td><s:property  value="#t.names" /></td>
				</tr>
			</s:iterator>

		</table>
		</div>
		<input type="button" class="button" value="查询任务" onclick="checkOperator('operators')" />
		</form>
	</body>
</html>
