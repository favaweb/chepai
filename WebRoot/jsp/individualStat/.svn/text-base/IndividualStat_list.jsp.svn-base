<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'IndividualStat_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function find(){
			var userid=document.getElementById("userId").value;
			var biginTime=document.getElementById("biginTime").value;
			var endTime=document.getElementById("endTime").value;
			/*if(userid<1)return alert("请选择操作人");*/
			if(biginTime==null||biginTime=="")return alert("请选择开始时间");
			document.getElementById("myForm").submit();
		}
	</script>
  </head>
  
  <body>
  	<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;查询统计&nbsp;>&nbsp;个人产量明细表</div>
  	<hr/>
    <form action="IndividualOutput!outputStat.action" id="myForm" name="myForm" method="post">
    	流程状态:<select name="flowTypeId">
						<option value="0">--请选择--</option>
						<s:iterator value="flowTypes" var="flowTypes">
							<s:if test="!#flowTypes.typeName.equals('完成')">
								<option value='<s:property value="#flowTypes.id"/>' <s:if test="flowTypeId == #flowTypes.id">selected="selected"</s:if> >
									<s:property value="#flowTypes.typeName"/>
								</option>
							</s:if>
						</s:iterator>
					</select>
    	操作人：<select name="userId" id="userId">
						<option value="0">--请选择--</option>
						<s:iterator value="users" var="users">
							<option value='<s:property value="#users.id"/>' <s:if test="userId == #users.id">selected="selected"</s:if> ><s:property value="#users.name"/></option>
						</s:iterator>
					</select>
    	时间段：<input type="text" name="biginTime" id="biginTime" value="<s:property value="biginTime"/>" onClick="WdatePicker()" class="Wdate" />-
    	<input type="text" name="endTime" id="endTime" value="<s:property value="endTime"/>" onClick="WdatePicker()" class="Wdate" />
    	<input type="button" value="查询" class="button" onclick="find()"/>
    	<table  cellpadding="1" cellspacing="1" >
    		<tr class="th">
    			<td colspan="4">个人产量明细表(<s:property value="biginTime"/>至<s:if test="endTime != null && !endTime.equals('')"><s:property value="endTime"/></s:if><s:else>今天</s:else>)</td>
    		</tr>
    		<tr class="th">
    			<th>序号</th>
    			<th>操作人</th>
    			<th>流程类型</th>
    			<th>产量(单位：块)</th>
    		</tr>
			<s:iterator value="outputList" var="outputList" status="s">
				<tr>
					<td><s:property value="#s.index+1"/></td>
					<td><s:property value="#outputList.user.name"/></td>
					<td><s:property value="#outputList.flowType.typeName"/></td>
					<td><s:property value="#outputList.output"/></td>
				</tr>
			</s:iterator>
    	</table>
    </form>
  </body>
</html>
