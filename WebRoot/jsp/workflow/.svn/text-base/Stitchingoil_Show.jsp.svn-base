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
    
    <title>滚油</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  </head>
  <script type="text/javascript">
		//提交给下一流程
		function save(){
				document.getElementById("myForm").action="NumberPlate!updateByStuts.action";
				document.getElementById("myForm").submit();
		}
  </script>
  <body>
  <%
	int isRemakes = (Integer)request.getAttribute("isRemakes");
	int flowTypeId = (Integer)request.getAttribute("flowTypeId");
	int smallnessBatchNumberId = (Integer)request.getAttribute("smallnessBatchNumberId");
	List<NumberPlate> numberPlates = (List<NumberPlate>)request.getAttribute("numberPlates");
	int isDistribute = (Integer)request.getAttribute("isDistribute");
%>
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!list.action?flowTypeId=3">滚油流程</a>&nbsp;>&nbsp;批号详情</div>
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
  	<%
		if(isDistribute == 2) {
	%>
		  	<input type="button" class="button" value="提交下一流程" onclick="save()"/>
  	<%
  		}
  	 %>
</form>
  </body>
</html>
