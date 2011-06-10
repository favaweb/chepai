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
    
    <title>总质检</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  </head>
  <script type="text/javascript">
		//提交给下一流程
		function save(i,i2,s){
			if(i==0){
				document.getElementById("myForm").action="NumberPlate!updateByStuts.action";
			}
			else if(i==1){
				printsmall(s,i2);
				document.getElementById("myForm").action="NumberPlate!updateByStuts.action?print=1";
			}
				document.getElementById("myForm").submit();
		}
		
		function printsmall(snum,sid){
			if(snum.indexOf('HB')!=-1){
				window.open("NumberPlate!mergepackPrint.action?smallnessBatchNumberId="+sid);
			}else{
				window.open("NumberPlate!packPrint.action?smallnessBatchNumberId="+sid);
			}
		}
  </script>
  <body>
  <%
		int flowTypeId = (Integer)request.getAttribute("flowTypeId");
		int smallnessBatchNumberId = (Integer)request.getAttribute("smallnessBatchNumberId");
		int isRemakes = (Integer)request.getAttribute("isRemakes");
		int isDistribute = (Integer)request.getAttribute("isDistribute");
		List<NumberPlate> numberPlates = (List<NumberPlate>)request.getAttribute("numberPlates");
	%>
	<div style="position: absolute;width: 100%;height: 100%;background:white;display: none;" id="wait" >
  	<div style="width:100%;height:100%;text-align: center;background: #dddddd;">
  		<img src="Images/loading.gif" />打印中...
  	</div>
  </div>
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!list.action?flowTypeId=7">总质检流程</a>&nbsp;>&nbsp;批号详情</div>
  <form name="myForm" id="myForm" action="#" method="post">
  	<s:token></s:token>
  	<input type="hidden" name="flowTypeId" value="<%=flowTypeId %>">
	<input type="hidden" name="smallnessBatchNumberId" value='<%=smallnessBatchNumberId %>'/>
 	<input type="hidden" name="isRemakes" value='<%=isRemakes %>'/>
  	
  	<div>
  	  		<%
  			if(isDistribute == 2) {
  		%>
		 <div align="right"> 	<input  type="button" class="button" value="提交下一流程并打印" onclick="save(1,<%=smallnessBatchNumberId %>,'<%=numberPlates.get(0).getSmallnessBatchNumber().getSmallnessBatchNumber() %>')"/></div>
  		<%
  			}
  		 %>
	  	<table  cellpadding="1" cellspacing="1" >
	  		<tr class="th">
	  			<td>序号</td>
	  			<td>小批号</td>
	  			<td>业务部门</td>
	  			<td>车牌种类</td>
	  			<td>车牌号码</td>
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
				<td>
					<%=n.getLicensePlateNumber() %>
				</td>
			</tr>		
			<%	
	  			}
	  		%>
	
	  	</table>
	  </div>

</form>
  </body>
</html>
