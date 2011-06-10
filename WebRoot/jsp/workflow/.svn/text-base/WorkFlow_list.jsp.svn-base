<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
 	 var selFlag = true;
  	function setChkboxStatus(tableId)
		{
		   	var selRow = document.getElementsByName(tableId);
		   	if(selFlag){
		   		for(var i=0;i<selRow.length;i++){
		   			if(selRow[i].disabled==true){
		   				selRow[i].checked = false;
		   			}else{
		       			selRow[i].checked = true;
		       		}
		   		}
		   		selFlag = false;
		   	}else {
		   		for(var i=0;i<selRow.length;i++){
		       		selRow[i].checked = false;
		   		}
		   	selFlag = true;
		   	}   
		}
		
		function saveOperator(select){
			var selRow = document.getElementsByName(select);
			var str=false;
			for(var i=0;i<selRow.length;i++){
		   			if(selRow[i].value!="0"){
		   				str=true;
		   			}
		       }
		       if(document.getElementById("sum").value>5){
		       		alert("请输入小于6的批数!!!!");
		       }else if(document.getElementById("sum").value!=null&&document.getElementById("sum").value!=""&&str){ 
		       		 document.getElementById("myForm").submit();
		       }else{
		      		 alert("请选择员工或输入分发批数！！！！");
		       }
		}
		//废牌补制
		function findRemakes(){
			document.getElementById("myForm").action="Remakes!queryList.action";
			document.getElementById("myForm").submit();
		}
  </script>
	</head>

	<body>
		
		<table  cellpadding="1" cellspacing="1"  border="1">
			<tr>
				<td>
					大批号
				</td>
				<td>
					小批号
				</td>
				<td>
					地区
				</td>
				<td>
					类型
				</td>
				<td>
					优先级
				</td>
				<td>
					箱号
				</td>
				<td>
					时间
				</td>
				<td>
					任务状态
				</td>
			</tr>

			<s:iterator value="workFlows" var="workFlow">
				<tr>
					<td>
						<s:if test="#workFlow.bigBatchNumber == null">
							<s:if test="#workFlow.smallnessBatchNumber.isRemakes == 1">
								<span style="color:red;">补牌</span>
							</s:if>
							<s:elseif test="#workFlow.smallnessBatchNumber.isRemakes == 2">
								<span style="color:red;">合并</span>
							</s:elseif>
						</s:if>
						<s:else>
							<s:property value="#workFlow.bigBatchNumber.bigBattchNumber" />
						</s:else>
					</td>
					<td>
				
							<a href="NumberPlate!findBySmallnessBatchId.action?smallnessBatchNumberId=<s:property value='#workFlow.smallnessBatchNumber.id'/>&isRemakes=<s:property value='#workFlow.smallnessBatchNumber.isRemakes'/>&flowTypeId=<s:property value='flowTypeId'/>&isDistribute=<s:property value='#workFlow.smallnessBatchNumber.isDistribute'/>" target="mainFrame">
								<s:property value="#workFlow.smallnessBatchNumber.smallnessBatchNumber" />
							</a>
					</td>
					<td>
						<s:if test="#workFlow.smallnessBatchNumber.place == null">
							<span style="color:red;">混合地区</span>
						</s:if>
						<s:else>
							<s:property value="#workFlow.smallnessBatchNumber.place" />
						</s:else>
					</td>
					<td>
						<s:if test="#workFlow.smallnessBatchNumber.orderType == 1">补制</s:if>
						<s:elseif test="#workFlow.smallnessBatchNumber.orderType == 2">自选</s:elseif>
						<s:else>号段</s:else>
					</td>
					<td>
						<s:if test="#workFlow.smallnessBatchNumber.precedence == 1">
							<span style="color: red;">高</span>
						</s:if>
						<s:elseif test="#workFlow.smallnessBatchNumber.precedence == 2">
							<span style="color: red;">中</span>
						</s:elseif>
						<s:else>
							<span style="color: red;">低</span>
						</s:else>
					</td>
					<td>
						<s:property value="#workFlow.smallnessBatchNumber.boxNumber" />
					</td>
					<td>
						<s:date name="#workFlow.smallnessBatchNumber.dateTime"
							format="yyyy-MM-dd hh:mm:ss" />
					</td>
					<td>
						<s:if test="#workFlow.smallnessBatchNumber.isDistribute == 1">待发</s:if>
						<s:elseif test="#workFlow.smallnessBatchNumber.isDistribute == 2">已发</s:elseif>
						<s:elseif test="#workFlow.smallnessBatchNumber.isDistribute == 3">补制中</s:elseif>
					</td>
				</tr>
			
			</s:iterator>

		</table>
		<s:if test="flowTypeId==5">
			<input type="button" value="待补区" onclick="findRemakes()"/>
		</s:if>
		<form action="Operator!save.action" id="myForm" method="post">
		<input type="hidden" name="flowTypeId" value='<s:property value="flowTypeId"/>'/>
		<table  cellpadding="1" cellspacing="1"  border="1">
			<tr>
				<!-- <th><input type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('userIds')"/></th> -->
				<th>员工名称</th>
				<th>分发批数</th>
				<s:if test="flowTypeId == 1"><th>箱号</th></s:if>
				<th>任务分发</th>
			</tr>
			<tr>
				<td>
					<select name="select">
						<option value="0">--请选择--</option>
						<s:iterator value="users" var="users">
						<option value='<s:property value="#users.id"/>'><s:property value="#users.name"/></option>
						</s:iterator>
					</select>
				</td>
				<s:if test="flowTypeId==2||flowTypeId==3||flowTypeId==6">
					<td rowspan="4"><input type="text" name="sum" id="sum"/></td>
						<s:if test="flowTypeId == 1"><td rowspan="4"><input type="text" name="boxNumber"/></td></s:if>
					<td rowspan="4"><input type="button" value="任务分发" onclick="saveOperator('select')"/></td>
				</s:if>
				<s:else>
					<td><input type="text" name="sum" id="sum"/></td>
						<s:if test="flowTypeId == 1"><td><input type="text" name="boxNumber"/></td></s:if>
					<td><input type="button" value="任务分发" onclick="saveOperator('select')"/></td>
				</s:else>
				
			</tr>
			<s:if test="flowTypeId==2||flowTypeId==3||flowTypeId==6">
				<tr>
					<td>
						<select name="select">
							<option value="0">--请选择--</option>
							<s:iterator value="users" var="users">
							<option value='<s:property value="#users.id"/>'><s:property value="#users.name"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<select name="select">
							<option value="0">--请选择--</option>
							<s:iterator value="users" var="users">
							<option value='<s:property value="#users.id"/>'><s:property value="#users.name"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<select name="select">
							<option value="0">--请选择--</option>
							<s:iterator value="users" var="users">
							<option value='<s:property value="#users.id"/>'><s:property value="#users.name"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
			</s:if>
			
			<!--
			<s:iterator value="users" var="users">
				<tr>
					<td><input name="userIds" type="checkbox"
									value="<s:property value="#users.id"/>"
									id="chkid<s:property value="#users.id"/>" />
					</td>
					<td><s:property value="#users.name"/></td>
					<td><input type="text" name="sum" id="chkid<s:property value="#users.id"/>"/></td>
				</tr>
			</s:iterator>
			-->
		</table>
		</form>

	</body>
</html>
