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
    
    <title>My JSP 'Remake_List.jsp' starting page</title>
    
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
		
		function baleRemakes(){
			if(window.confirm("确定提交?")) {
				document.getElementById("myForm").action="Remakes!baleRemakes.action";
				document.getElementById("myForm").submit();
			}
		}
		function backRemakes(){
			if(window.confirm("确定提交?")) {
				document.getElementById("myForm").action="Remakes!backRemakes.action";
				document.getElementById("myForm").submit();
			}
		}
	</script>
  </head>
  
  <body>
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!list.action?flowTypeId=5">质检流程</a>&nbsp;>&nbsp;<a href="Remakes!queryList.action">待补区</a></div>
  
   <form action="#" name="myForm" id="myForm" method="post">
   	<s:token></s:token>
   	 <input type="hidden" name="flowTypeId" value="5"/>
   	 <div>
   	 
  		<table  cellpadding="1" cellspacing="1" >
  			<tr class="th">
  				<th><input style="width: 25px;height:25px;" type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('remakesIds')"/></th>
  				<th>车牌号</th>
  				<th>业务部门</th>
  				<th>车牌种类</th>
  				<th>小批号</th>
  			</tr>
  			
  			<s:if test="remakesList == null || remakesList.size() == 0">
  				<tr>
  					<td colspan="5"><span style="color:red;">没有任何数据</span></td>
  				</tr>
  			</s:if>
  			
	  		<s:iterator value="remakesList" var="remakesList">
	  			<tr>
	  				<td>
					<input name="remakesIds" type="checkbox"  style="width: 25px;height:25px;"
										value="<s:property value="#remakesList.id"/>"
										id="chkid<s:property value="#remakesList.id"/>" />
				</td>
				<td><s:property value="#remakesList.numberPlate.licensePlateNumber"/></td>
				<td><s:property value="#remakesList.numberPlate.businessDepartment.department"/></td>
				<td><s:property value="#remakesList.numberPlate.numberPlateType.typeName"/></td>
				<td><s:property value="#remakesList.numberPlate.smallnessBatchNumber.smallnessBatchNumber"/></td>
	  			</tr>
	  		</s:iterator>
	  		
  	 </table>
  	 
  	</div>
  	
  	<s:if test="remakesList != null && remakesList.size() != 0">
		<input type="button" class="button" value="打批" onclick="baleRemakes()"/>
		<input type="button" class="button" value="回退" onclick="backRemakes()"/>
	</s:if>
   </form>
  </body>
</html>
