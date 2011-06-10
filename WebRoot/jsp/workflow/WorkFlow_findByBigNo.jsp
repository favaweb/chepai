<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
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

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
		
		<script>
			function upgrade(precedence,sbatchNumberId,flowTypeId){
				if(precedence>1){
					document.getElementById("myform").action="SmallnessBatchNumber!upgrade.action?precedence="+precedence+"&smallnessBatchNumberId="+sbatchNumberId;
					document.getElementById("myform").submit();
				}else{
					alert("已经是最高级别！");
				}
			}
			
			function submitForm(currentPage) {
				document.getElementById("myform").submit();
			}
		</script>
	</head>

	<body>
		<%
			BigBatchNumber bigBatch = (BigBatchNumber)request.getAttribute("bigBatchNumber");
			String date = (String)request.getAttribute("date");
			String place = (String)request.getAttribute("place");
			String numberPlate = (String)request.getAttribute("numberPlate");
			String bigNo = (String)request.getAttribute("bigNo");
			int flowTypeId = (Integer)request.getAttribute("flowTypeId");
			Page p = (Page)request.getAttribute("page");
		 %>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!findByConditionInput.action">流程查询</a></div>
		
		<form action="WorkFlow!findByCondition.action" id="myform" method="post">
		<s:token></s:token>
		<div class="saixuan">
			流程类型：
			<select name="flowTypeId">
				<option value="0">--请选择--</option>
				<option value="1" <%if(flowTypeId==1)out.print("selected='selected'"); %>>反压</option>
				<option value="2" <%if(flowTypeId==2)out.print("selected='selected'"); %>>洗牌</option>
				<option value="3" <%if(flowTypeId==3)out.print("selected='selected'"); %>>滚油</option>
				<option value="4" <%if(flowTypeId==4)out.print("selected='selected'"); %>>正压</option>
				<option value="5" <%if(flowTypeId==5)out.print("selected='selected'"); %>>质检</option>
				<option value="6" <%if(flowTypeId==6)out.print("selected='selected'"); %>>打码</option>
				<option value="7" <%if(flowTypeId==7)out.print("selected='selected'"); %>>总质检</option>
			</select>&nbsp;
			时间：<input type="text" style="width: 85px;" name="date" value="<%if(date!=null)out.print(date); %>"  onClick="WdatePicker()" class="Wdate" />&nbsp;
			地区：<input type="text" style="width: 85px;" name="place" value="<%if(place!=null)out.print(place); %>"  />&nbsp;
			大批号：<input type="text" name="bigNo" value="<%if(bigNo!=null)out.print(bigNo); %>"  />&nbsp;
			车牌号：<input type="text" name="numberPlate" value="<%if(numberPlate!=null)out.print(numberPlate); %>"  />&nbsp;
			<input type="button" class="button" onclick="submitForm(1)" value="查询"/>
		</div>
		<hr/>
		
		<div>
		
			<table  cellpadding="1" cellspacing="1"   cellpadding="1" cellspacing="1"  cellpadding="1" cellspacing="1">
				<tr class="th">
					<td>
						大批号
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
					<td>
						数量
					</td>
				</tr>
	
				<%
					if(bigBatch == null) {
				%>
					<tr>
						<td colspan="6" align="center"><span style="color:red;">没有任何记录</span></td>
					</tr>
				<%	
					} else {
				%>
					<tr>
						<td>
							<a href="SmallnessBatchNumber!findFlowByBig.action?bigBatchNumberId=<%=bigBatch.getId() %>">
								<%=bigBatch.getBigBattchNumber() %>
							</a>
						</td>
						<td>
							<%=bigBatch.getPlace() %>
						</td>
						<td>
							<%
								if(bigBatch.getOrderType() == 1) {
									out.print("补制");
								} else if(bigBatch.getOrderType() == 2) {
									out.print("自选");
								} else {
									out.print("号段");
								}
							 %>
						</td>
						<td>
							<%
								if(bigBatch.getPrecedence() == 1) {
									out.print("<span style='color: red;'>高</span>");
								} else if(bigBatch.getPrecedence() == 2) {
									out.print("<span style='color: red;'>中</span>");
								} else {
									out.print("<span style='color: red;'>低</span>");
								}
							 %>
						</td>
						<td>
							<%=TimeUtil.formatDateTime(bigBatch.getCreateTime()) %>
						</td>
						<td>
							<%=bigBatch.getAmount() %>
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