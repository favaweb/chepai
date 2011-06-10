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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
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
		//提交给下一流程
		function save(){
				document.getElementById("myForm").action="NumberPlate!updateByStuts.action";
				document.getElementById("myForm").submit();
		}
		//记录废牌
		function addDisuse(){
			var marType = document.getElementById("marType").value;
			if(marType!="0"){
				document.getElementById("myForm").action="DisuseMaterial!save.action";
				document.getElementById("myForm").submit();
			}else{
				alert("请选择废牌原因！！！！");
			}
		}
		//重制
		function addRemakes(tableId){
			var selRow = document.getElementsByName(tableId);
			var str=false;
			for(var i=0;i<selRow.length;i++){
				if(selRow[i].checked==true)str=true;
			}
			if(str){
				document.getElementById("myForm").action="Remakes!save.action";
				document.getElementById("myForm").submit();
			}else{
				alert("请选择重制车牌！！！");
			}
		}
		function remakesFinish(tableId){
			var selRow = document.getElementsByName(tableId);
			var str=false;
			for(var i=0;i<selRow.length;i++){
				if(selRow[i].checked==true)str=true;
			}
			if(str){
				document.getElementById("myForm").action="Remakes!remakesFinish.action";
				document.getElementById("myForm").submit();
			}else{
				alert("请选择重制完成车牌！！！");
			}
		}
  </script>
  <body>
  <form name="myForm" id="myForm" action="#" method="post">
  <input type="hidden" name="smallnessBatchNumberId" value='<s:property value="smallnessBatchNumberId"/>'/>
  	<input type="hidden" name="isRemakes" value='<s:property value="isRemakes"/>'/>
  	<table  cellpadding="1" cellspacing="1"  border="1">
  		<tr>
  			<td><input  style="width: 25px;height:25px;" type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('numberPlateIds')"/></td>
  			<td>序号</td>
  			<td>小批号</td>
  			<td>业务部门</td>
  			<td>车牌种类</td>
  			<td>车牌号码</td>
  		</tr>
  
	<s:iterator value="numberPlates" var="numberPlate">
		<tr>
			<td>
				<input name="numberPlateIds" type="checkbox"  style="width: 25px;height:25px;"
									value="<s:property value="#numberPlate.id"/>"
									id="chkid<s:property value="#numberPlate.id"/>" />
			</td>
			<td>
				<s:property value="#numberPlate.orderNumber"/>
			</td>
			<td>
				<s:property value="#numberPlate.smallnessBatchNumber.smallnessBatchNumber"/>
			</td>
			<td>
				<s:property value="#numberPlate.businessDepartment.department"/>
			</td>
			<td>
				<s:property value="#numberPlate.numberPlateType.typeName"/>
			</td>
			<td>
				<s:property value="#numberPlate.licensePlateNumber"/>
			</td>
		</tr>		
	</s:iterator>

  	</table>
  	<s:if test="isDistribute == 2">
  		  <s:if test="flowTypeId==5">
	  			<input type="button" value="重制" onclick="addRemakes('numberPlateIds')"/>
		  </s:if>
		  <s:elseif test="flowTypeId==1">
			  	<select name="marType" id="marType">
					<option value="1">压错</option>
					<option value="2">半成品</option>
					<option value="3">机器模具</option>
				</select>
			  	<input type="button" value="废牌" onclick="addDisuse()"/>
		  </s:elseif>
		  <s:if test="flowTypeId==5 and isRemakes==1">
		  	<input type="button" value="重制完成" onclick="remakesFinish('numberPlateIds')"/>
		  </s:if>
		  <s:else>
		  	<input type="button" value="提交下一流程" onclick="save()"/>
		  </s:else>
  	</s:if>
</form>











  </body>
</html>
