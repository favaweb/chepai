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
    
    <title>反压</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  </head>
  <script type="text/javascript" src="js/keyboard.js" charset='GBK'  ></script>
<script>
//定义当前需用软键盘的表单和控件的名称
 var curEditName
 curEditName="myForm.boxNumber"
</script>
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
		var boxNumber=document.getElementById("boxNumber").value;
		var boxType = document.getElementById("boxType").value;
		if(boxNumber==null || boxNumber==""){
			alert("请输入箱号");
		}else if(boxType==null || boxType=="0") {
			alert("请选择箱子颜色");
		} else {
			if(window.confirm("确认提交到下一个流程?")) {
				document.getElementById("myForm").action="NumberPlate!updateByStuts.action";
				document.getElementById("myForm").submit();
			}
		}
	}
	
	
	//记录废牌
	function addDisuse(frontIds, offIds){
		var front = document.getElementsByName(frontIds);
		var off = document.getElementsByName(offIds);
		var isCheck = false;		
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
			var marType = document.getElementById("marType").value;
			if(marType!="0"){
				document.getElementById("myForm").action="DisuseMaterial!saveFeeNumberId.action";
				document.getElementById("myForm").submit();
			}else{
				alert("请选择废牌原因！！！！");
			}
		}
		
	}
	
	<%
		int isRemakes = (Integer)request.getAttribute("isRemakes");
		int plateType = (Integer)request.getAttribute("plateType");
		int flowTypeId = (Integer)request.getAttribute("flowTypeId");
		int smallnessBatchNumberId = (Integer)request.getAttribute("smallnessBatchNumberId");
		List<NumberPlate> numberPlates = (List<NumberPlate>)request.getAttribute("numberPlates");
		int isDistribute = (Integer)request.getAttribute("isDistribute");
		if(isRemakes == 1) {
	%>
		function hiddenPreBack(size) {
			for(var i=0; i<size; i++) {
				var lpn = document.getElementById("lpn" + i).innerHTML;
				var list = lpn.split("_");
				if(list[1] != undefined && trimString(list[1]) == "补制前牌") {
					document.getElementById("off" + i).disabled = true;
				} else if(list[1] != undefined && trimString(list[1]) == "补制后牌") {
					document.getElementById("front" + i).disabled = true;
				}
			}
		}
	<%
		}
	%>
	
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
  <body <%if(isRemakes == 1) {%>onload="hiddenPreBack('<%if(numberPlates != null && numberPlates.size() > 0) {out.print(numberPlates.size());}%>')"<%}%>
  
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<%if(plateType == 0)out.print("<a href='WorkFlow!list.action?flowTypeId=1'>反压流程</a>");else if(plateType == 1)out.print("<a href='WorkFlow!list.action?flowTypeId=1&plateType=" + plateType + "'>大车反压</a>");else out.print("<a href='WorkFlow!list.action?flowTypeId=1&plateType=" + plateType + "'>摩托车反压</a>"); %>&nbsp;>&nbsp;批号详情</div>
  <form name="myForm" id="myForm" action="#" method="post">
  <s:token></s:token>
  <input type="hidden" name="flowTypeId" value="<%=flowTypeId %>">
  <input type="hidden" name="smallnessBatchNumberId" value='<%=smallnessBatchNumberId %>'/>
  <input type="hidden" name="plateType" value='<%=plateType %>'/>
  	<input type="hidden" name="isRemakes" value='<%=isRemakes %>'/>
  	<hr />
  		<%
  			if(isDistribute == 2) {
  		%>
  		<table border="0" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
  			<tr bgcolor="#ffffff">
  				<td style="text-align: left;">
  					<select name="marType" id="marType">
						<option value="0">请选择</option>
						<option value="1">压错</option>
						<option value="2">半成品</option>
						<option value="3">机器模具</option>
					</select>
		  			<input type="button" class="button" value="记录废牌" onclick="addDisuse('frontNumberIds','offsideNumberIds')"/>
  				</td>
  				<td style="text-align: right;">
  					箱号：<input type="text" name="boxNumber" id="boxNumber" style="width: 50px;" onclick="showkeyboard(curEditName)"/>
  					颜色：<select name="boxType" id="boxType">
  							<option value="0">--请选择--</option>
  							<option value="1">白色</option>
  							<option value="2">黄色</option>
  							<option value="3">蓝色</option>
  						</select>
	  				<input type="button" class="button" value="提交下一流程" onclick="save()"/>
  				</td>
  			</tr>
	  	</table>
	  	<%	
  			}
  		%>
  	<div>
	  	<table  cellpadding="1" cellspacing="1" >
	  		<tr class="th">
	  			<!--  <td><input type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('numberPlateIds')"/></td>-->
	  			<td>序号</td>
	  			<td>小批号</td>
	  			<td>业务部门</td>
	  			<td>车牌种类</td>
	  			<td>车牌号码</td>
	  			<td>前牌</td>
	  			<td>后牌</td>
	  		</tr>
	  		
	  		<%
	  			for(int i=0; i<numberPlates.size(); i++) {
	  				NumberPlate n = numberPlates.get(i);
	  		%>
				<tr>
					<!-- <td>
						<input name="numberPlateIds" type="checkbox"
											value="<s:property value="#numberPlate.id"/>"
											id="chkid<s:property value="#numberPlate.id"/>" />
					</td>
					 -->
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
							<input name="offsideNumberIds" type="checkbox"  disabled="disabled"  style="width: 25px;height:25px;"
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
