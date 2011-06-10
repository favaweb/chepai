<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>

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
  <body>
	  	<%
			List<NumberPlate> numberPlates = (List<NumberPlate>)request.getAttribute("numberPlates");
		%>
  		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;小批号详细信息</div>
		<hr />
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
	  <input type="button" class="button" value="返回" onclick="javascript:window.history.go(-1);"/><br/>	
	  </div>
  </body>
</html>
