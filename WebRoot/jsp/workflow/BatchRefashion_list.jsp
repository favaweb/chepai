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

		<title>审核</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
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
		
		function addRefashion(select){
			if(window.confirm("确定重制?")) {
				var isSelected = false;
				var errorFlow = true;
				var marType = true;
				var selected = document.getElementsByName(select);
				for(var i=0; i<selected.length; i++) {
					if(selected[i].checked == true) {
						isSelected = true;
					}
				}
				if(document.getElementById('errorFlow').value == 0){
					errorFlow = false;
					alert('请选择错误流程');
				}
				else if(document.getElementById('marType').value == 0){
					marType = false;
					alert('请选择错误原因');
				}
				else if(isSelected && marType && errorFlow) {
					var myform = document.getElementById("myForm");
					myform.action = "BatchRefashion!update.action";
					myform.submit();
				} else {
					alert("请选择要重制的批号");
				}
			}
		}
		
		function backRefashion(select){
			if(window.confirm("确定回退?")) {
				var isSelected = false;
				var selected = document.getElementsByName(select);
				for(var i=0; i<selected.length; i++) {
					if(selected[i].checked == true) {
						isSelected = true;
					}
				}
				if(isSelected) {
					var myform = document.getElementById("myForm");
					myform.action = "BatchRefashion!back.action";
					myform.submit();
				} else {
					alert("请选择要回退的批号！");
				}
			}
		}
  </script>
	</head>

	<body>
	
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;待审核区</div>
		
		<form action="#" id="myForm" method="post">
		<s:token></s:token>
		<div>
			<table  cellpadding="1" cellspacing="1" >
				<tr class="th">
					<td>
						<input type="checkbox" style="width: 25px;height:25px;" name="chkAll" id="chkAll" onclick="setChkboxStatus('batchRefashionIds')"/>
					</td>
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
						时间
					</td>
				</tr>
				
				<s:if test="batchRefashionList == null || batchRefashionList.size() ==0">
					<tr>
						<td colspan="7"><span style="color:red;">没有任何数据</span></td>
					</tr>
				</s:if>
				
				<s:iterator value="batchRefashionList" var="Refashion">
					<tr>
						<td>
							<input name="batchRefashionIds" type="checkbox" style="width: 25px;height:25px;"
										value="<s:property value="#Refashion.id"/>"
										id="chkid<s:property value="#Refashion.id"/>" />
						</td>
						<td>
							<s:if test="#Refashion.bigBatchNumber == null">
								<s:if test="#Refashion.smallnessBatchNumber.isRemakes == 1">
									<span style="color:red;">补牌</span>
								</s:if>
								<s:elseif test="#Refashion.smallnessBatchNumber.isRemakes == 2">
									<span style="color:red;">合并</span>
								</s:elseif>
							</s:if>
							<s:else>
								<s:property value="#Refashion.bigBatchNumber.bigBattchNumber" />
							</s:else>
						</td>
						<td>
								<s:property value="#Refashion.smallnessBatchNumber.smallnessBatchNumber" />
						</td>
						<td>
							<s:if test="#Refashion.smallnessBatchNumber.place == null">
								<span style="color:red;">混合地区</span>
							</s:if>
							<s:else>
								<s:property value="#Refashion.smallnessBatchNumber.place" />
							</s:else>
						</td>
						<td>
							<s:if test="#Refashion.smallnessBatchNumber.orderType == 1">补制</s:if>
							<s:elseif test="#Refashion.smallnessBatchNumber.orderType == 2">自选</s:elseif>
							<s:else>号段</s:else>
						</td>
						<td>
							<s:if test="#Refashion.smallnessBatchNumber.precedence == 1">
								<span style="color: red;">高</span>
							</s:if>
							<s:elseif test="#Refashion.smallnessBatchNumber.precedence == 2">
								<span style="color: red;">中</span>
							</s:elseif>
							<s:else>
								<span style="color: red;">低</span>
							</s:else>
						</td>
						<td>
							<s:date name="#Refashion.smallnessBatchNumber.dateTime"
								format="yyyy-MM-dd hh:mm:ss" />
						</td>
					</tr>
				</s:iterator>
				</table>
			</div>
			<s:if test="batchRefashionList != null && batchRefashionList.size() !=0">
				错误流程<select name="errorFlow" id="errorFlow">
  					<option value="0">--请选择--</option>
	  				<s:iterator value="flowTypes" var="flowType">
	  					<s:if test="#flowType.id <= 4">
	  						<option value='<s:property value="#flowType.id"/>'><s:property value="#flowType.typeName"/></option>
	  					</s:if>
	  				</s:iterator>
				</select>
					<select name="marType" id="marType">
						<option value="0">--请选择--</option>
						<option value="1">压错</option>
						<option value="2">半成品</option>
						<option value="3">机器模具</option>
					</select>
				<input type="button" class="button" value="同意" onclick="addRefashion('batchRefashionIds')"/>
				<input type="button" class="button" value="回退" onclick="backRefashion('batchRefashionIds')"/>
			</s:if>
		</form>
	</body>
</html>
