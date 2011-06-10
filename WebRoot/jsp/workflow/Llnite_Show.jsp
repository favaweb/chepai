<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>质检</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  </head>
 	 <script>
 	 	var selFlag = true;
 	 	var selOff = true;
	  	function setStatus(tableId)
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
		
		function setStatusOff(tableId)
		{
		   	var selRow = document.getElementsByName(tableId);
		   	if(selOff){
		   		for(var i=0;i<selRow.length;i++){
		   			if(selRow[i].disabled==true){
		   				selRow[i].checked = false;
		   			}else{
		       			selRow[i].checked = true;
		       		}
		   		}
		   		selOff = false;
		   	}else {
		   		for(var i=0;i<selRow.length;i++){
		       		selRow[i].checked = false;
		   		}
		  	 	selOff = true;
		   	}   
		}
			
		
		//提交给下一流程
		function save(){
				document.getElementById("myForm").action="NumberPlate!updateByStuts.action";
				document.getElementById("myForm").submit();
		}
		
		//重制
		function addRemakes(frontId, offId){
			var isCheck = false;			
			var front = document.getElementsByName(frontId);
			var off = document.getElementsByName(offId);
			
			for(var i=0; i<front.length; i++) {
				if(front[i].checked == true) {
					isCheck = true;
				}
			}
			for(var i=0; i<off.length; i++) {
				if(off[i].checked == true) {
					isCheck = true;
				}
			}
			
			
			if(!isCheck) {
				alert("请选择需要重制的车牌！！！");
			} else {
				var errorFlow = document.getElementById("errorFlow").value;//废牌流程
				var marType = document.getElementById("marType").value;//废牌原因
				if(errorFlow!=null&&errorFlow!="0"&&marType!=null&&marType!="0"){
					document.getElementById("myForm").action="Remakes!save.action";
					document.getElementById("myForm").submit();
				} else {
					alert("请选择错误流程跟错误操作！！！");
				}
			} 
		}
		
		
		function remakesFinish(frontId,offId){
			var isCheck = false;			
			var front = document.getElementsByName(frontId);
			var off = document.getElementsByName(offId);
			for(var i=0; i<front.length; i++) {
				if(front[i].checked == true) {
					isCheck = true;
				}
			}
			for(var i=0; i<off.length; i++) {
				if(off[i].checked == true) {
					isCheck = true;
				}
			}
			if(isCheck) {
				document.getElementById("myForm").action="Remakes!remakesFinish.action";
				document.getElementById("myForm").submit();
			} else {
				alert("请选择重制完成车牌！！！");
			}
		}
		
		
		/**容器加载的时候执行
		如果是 小批号是 补制中**/
		<%
			int isRemakes = (Integer)request.getAttribute("isRemakes");
			int flowTypeId = (Integer)request.getAttribute("flowTypeId");
			int smallnessBatchNumberId = (Integer)request.getAttribute("smallnessBatchNumberId");
			List<NumberPlate> numberPlates = (List<NumberPlate>)request.getAttribute("numberPlates");
			int isDistribute = (Integer)request.getAttribute("isDistribute");
			if(isDistribute == 3) {
		%>
			function hiddenPreBack(size) {
				for(var i=0; i<size; i++) {
					var lpn = document.getElementById("lpn" + i).innerHTML;
					var list = lpn.split("_");
					if(list[1] != undefined && trimString(list[1]) == "补制后牌") {
						document.getElementById("front" + i).disabled = true;
						document.getElementById("off" + i).disabled = true;
					} else if(list[1] != undefined && trimString(list[1]) == "补制前牌") {
						document.getElementById("front" + i).disabled = true;
						document.getElementById("off" + i).disabled = true;
					} else if(list[1] != undefined && trimString(list[1]) == "补制整副") {
						document.getElementById("front" + i).disabled = true;
						document.getElementById("off" + i).disabled = true;
					}
				}
			}
		<%
			} 
			if(isRemakes == 1) {
		%>
		/**如果小批号是 补制**/
			function hiddenPreBack(size) {
				for(var i=0; i<size; i++) {
					var lpn = document.getElementById("lpn" + i).innerHTML;
					var list = lpn.split("_");
					if(list[1] != undefined && trimString(list[1]) == "补制后牌") {
						document.getElementById("front" + i).disabled = true;
					} else if(list[1] != undefined && trimString(list[1]) == "补制前牌") {
						document.getElementById("off" + i).disabled = true;
					}
				}
			}
		<%
			}
		 %>
		/**去掉空格**/
		function trimString(str)
		{
		 var i,j;
		 if(str == "") return "";
		 for(i=0;i<str.length;i++)
		 if(str.charAt(i) != ' ') break;
		 if(i >= str.length) return "";
		 for(j=str.length-1;j>=0;j--)
		 if(str.charAt(j) != ' ') break;
		 return str.substring(i,j+1);
		}
		
  </script>
  <body <%if(isRemakes == 1 || isDistribute == 3) {%>onload="hiddenPreBack('<%if(numberPlates != null && numberPlates.size() > 0) {out.print(numberPlates.size());}%>')"<%}%>
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!list.action?flowTypeId=5">质检流程</a>&nbsp;>&nbsp;批号详情</div>
  <div align="right">
  		<%
			if(isDistribute == 2) {
		%>
			错误流程<select name="errorFlow" id="errorFlow">
				<option value="0">--请选择--</option>
				
				<%
					List<FlowType> flowTypes = (List<FlowType>)request.getAttribute("flowTypes");
					for(FlowType f : flowTypes) {
						if(f.getTypeName().equals("反压") || f.getTypeName().equals("洗牌") || f.getTypeName().equals("滚油") || f.getTypeName().equals("正压") || f.getTypeName().equals("质检")) {

				%>
					<option value='<%=f.getId() %>'><%=f.getTypeName() %></option>
				<%
						}
					}
				 %>
			</select>
				<select name="marType" id="marType">
					<option value="0">--请选择--</option>
					<option value="1">压错</option>
					<option value="2">半成品</option>
					<option value="3">机器模具</option>
					<option value="4">操作不当</option>
					<option value="6">重复车牌</option>
					<option value="7">漏制车牌</option>
					<option value="8">质检责任</option>
				</select>
		  	  <input type="button" class="button" value="重制" onclick="addRemakes('frontNumberIds','offsideNumberIds')"/>
		  	  <%
		  	  	if(flowTypeId == 5 && isRemakes == 1) {
		  	  %>
		  	  	<input type="button" class="button" value="重制完成" onclick="remakesFinish('frontNumberIds','offsideNumberIds')"/>
		  	  <%
		  	  	} else {
		  	  %>
		  	  	<input type="button" class="button" value="提交下一流程" onclick="save()"/>
		  	  <%
		  	    }
		  	   %>
  		<%	
  			} else if(isDistribute == 3) {
  		%>
  		
  		错误流程<select name="errorFlow" id="errorFlow">
  					<option value="0">--请选择--</option>
	  				<%
						List<FlowType> flowTypes = (List<FlowType>)request.getAttribute("flowTypes");
						for(FlowType f : flowTypes) {
													if(f.getTypeName().equals("反压") || f.getTypeName().equals("洗牌") || f.getTypeName().equals("滚油") || f.getTypeName().equals("正压") || f.getTypeName().equals("质检")) {
					%>
						<option value='<%=f.getId() %>'><%=f.getTypeName() %></option>
					<%
							}
						}
					 %>
				</select>
					<select name="marType" id="marType">
						<option value="0">--请选择--</option>
						<option value="1">压错</option>
						<option value="2">半成品</option>
						<option value="3">机器模具</option>
						<option value="6">重复车牌</option>
						<option value="7">漏制车牌</option>
						<option value="8">质检责任</option>
					</select>
  		<input type="button" class="button" value="重制" onclick="addRemakes('frontNumberIds','offsideNumberIds')"/>
  	<%
		}
	%>
	</div>
  <form name="myForm" id="myForm" action="#" method="post">
  <s:token></s:token>
  <input type="hidden" name="flowTypeId" value="<%=flowTypeId %>">
  <input type="hidden" name="smallnessBatchNumberId" value='<%=smallnessBatchNumberId %>'/>
  	<input type="hidden" name="isRemakes" value='<%=isRemakes %>'/>
  	
  	<div>
	  	<table  cellpadding="1" cellspacing="1" >
	  		<tr class="th">
	  			<td>序号</td>
	  			<td>小批号</td>
	  			<td>业务部门</td>
	  			<td>车牌种类</td>
	  			<td>车牌号码</td>
	  			<td><input style="width: 25px;height:25px;" type="checkbox" name="chkFront" id="chkFront" onclick="setStatus('frontNumberIds');"/>前牌</td>
	  			<td><input style="width: 25px;height:25px;" type="checkbox" name="chkOffside" id="chkOffside" onclick="setStatusOff('offsideNumberIds');"/>后牌</td>
	  		</tr>
	  
		<%
  			for(int i=0; i<numberPlates.size(); i++) {
  				NumberPlate n = numberPlates.get(i);
  		%>
			<tr>
				<td>
					<%=n.getOrderNumber() %>
				</td>
				<td>
					<%=n.getSmallnessBatchNumber().getSmallnessBatchNumber() %>
				</td>
				<td>
					<%=n.getBusinessDepartment().getDepartment() %>
				</td>
				<td>
					<%=n.getNumberPlateType().getTypeName() %>
				</td>
				<td id="lpn<%=i %>">
					<%=n.getLicensePlateNumber() %>
				</td>
				
				<%
					if(n.getNumberPlateType().getNumber() > 1) {
				%>
					<td>
						<input name="frontNumberIds" type="checkbox"  style="width: 25px;height:25px;"
											value="<%=n.getId() %>"
											id="front<%=i %>" />
					</td>
					<td>
						<input name="offsideNumberIds" type="checkbox"  style="width: 25px;height:25px;"
											value="<%=n.getId() %>"
											id="off<%=i %>" />
					</td>
				<%
					} else {
				%>
					<td>
						<input name="frontNumberIds" type="checkbox"  style="width: 25px;height:25px;"
											value="<%=n.getId() %>"
											id="front<%=i %>" />
					</td>
					<td>
						<input name="offsideNumberIds" type="checkbox"  disabled="disabled"   style="width: 25px;height:25px;"
											value="<%=n.getId() %>"
											id="off<%=i %>" />
					</td>
				<%
					}
				 %>
				
			</tr>		
		<%	
  			}
  		%>
	
	  	</table>
	  </div>
  	
  	

  	
</form>
  </body>
</html>
