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

		<title>反压</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript">
 	 var selFlag = true;
 	 
 	 function print(sid,isRemakes,ftid,isDistribute,operator)
 	 {
 	 	document.getElementById('operator').value=operator;
 	 	document.getElementById('sid').value=sid;
 	 	document.getElementById('isRemakes').value=isRemakes;
 	 	document.getElementById('ftid').value=ftid;
 	 	document.getElementById('isDistribute').value=isDistribute;
 	 	document.getElementById("myprint").action='NumberPlate!makeYazhiDan.action';
 	 	document.getElementById("myprint").submit();
 	 	document.getElementById("myprint").action='NumberPlate!makeBaozhiDan.action';
 	 	document.getElementById("myprint").submit();
 	 	//window.open('NumberPlate!makeYazhiDan.action?smallnessBatchNumberId='+sid+'&isRemakes='+isRemakes+'&flowTypeId='+ftid+'&isDistribute='+isDistribute+'&operator='+operator);
 	 	//window.open('NumberPlate!makeBaozhiDan.action?smallnessBatchNumberId='+sid+'&isRemakes='+isRemakes+'&flowTypeId='+ftid+'&isDistribute='+isDistribute);
 	 }
 	 
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
		       if(document.getElementById("sum").value!=null&&document.getElementById("sum").value!=""&&str){ 
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
		//整批重制
		function addRefashion(select){
		
			var selRow = document.getElementsByName(select);
			var check=false;
			for(var i=0;i<selRow.length;i++){
	   			if(selRow[i].checked == true){
	   				check = true;
	   			}
		     }
		     if(check) {
				document.getElementById("myForm").action="BatchRefashion!save.action";
				document.getElementById("myForm").submit();
			 }else {
			 	alert("请选择要整批重制的小批号");
			 }
		}
		//审核
		function findAudit(){
			document.getElementById("myForm").action="BatchRefashion!findByStatus.action";
			document.getElementById("myForm").submit();
		}
		
		
		
  </script>
	</head>

	<body>
	
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!list.action?flowTypeId=1">反压流程</a></div>
	
		<form action="TaskAllocation!save.action" id="myForm" method="post">
			<s:token></s:token>		
			<input type="hidden" name="plateType" value="<%=request.getAttribute("plateType") %>" />
			<div class="saixuan">
				员工名称：
				<select name="select">
					<option value="0">--请选择--</option>
						<%
							List<Users> users = (List<Users>)request.getAttribute("users");
							for(Users u : users) {
						%>
							<option value='<%=u.getId() %>'><%=u.getName() %></option>
						<%
							}
						 %>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				获取数量（批）：<select id="sum" name="sum">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5" selected="selected">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- 箱号：<input type="text" name="boxNumber" id="boxNumber"/> -->&nbsp;&nbsp;&nbsp;&nbsp;
				<input  type="button" class="button" value="获取" onclick="saveOperator('select')"/>
			</div>
			<hr />
		<div>
		<table  cellpadding="1" cellspacing="1" >
			<tr class="th">
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
				<td>
					操作人
				</td>
				<td colspan="1"> 
					打单 
				</td>
			</tr>
			
			<%
				
				List<WorkFlow> workFlows = (List<WorkFlow>)request.getAttribute("workFlows");
					if(workFlows == null || workFlows.size() == 0) {
			%>
						<tr>
							<td colspan="11" align="center"><span style="color:red;">没有任何记录</span></td>
						</tr>
			<%
					} else {
					for(WorkFlow w : workFlows) {
			 %>
				<tr>
					<td>
						
						<%
							if(w.getBigBatchNumber() == null) {
								if(w.getSmallnessBatchNumber().getIsRemakes() == 1) {
						%>
									<span style="color:red;">补牌</span>	
						<%
								} else if(w.getSmallnessBatchNumber().getIsRemakes() == 2) {
						%>
								 <a href="BatchNumberMerge!findMergerBySmallId.action?smallBatchId=<%=w.getSmallnessBatchNumber().getId() %>">
									合并批号
								 </a>
						<%
								}
							} else {
								out.print(w.getBigBatchNumber().getBigBattchNumber());
							}
						 %>
					</td>
					<td>
						<a href="NumberPlate!findBySmallnessBatchId.action?smallnessBatchNumberId=<%=w.getSmallnessBatchNumber().getId() %>&isRemakes=<%=w.getSmallnessBatchNumber().getIsRemakes() %>&flowTypeId=<%=request.getAttribute("flowTypeId") %>&isDistribute=<%=w.getSmallnessBatchNumber().getIsDistribute() %>" target="mainFrame">
							<%=w.getSmallnessBatchNumber().getSmallnessBatchNumber() %>
						</a>
					</td>
					<td>
						<%
							if(w.getSmallnessBatchNumber().getPlace() == null) {
						%>
							<span style="color:red;">混合地区</span>
						<%
							} else {
								out.print(w.getSmallnessBatchNumber().getPlace());
							}
						 %>
					</td>
					<td>
						<%
							if(w.getSmallnessBatchNumber().getOrderType() == 1) {
								out.print("补制");	
							} else if(w.getSmallnessBatchNumber().getOrderType() == 2) {
								out.print("自选");	
							} else {
								out.print("号段");	
							}
						 %>
					</td>
					<td>
						<%
							if(w.getSmallnessBatchNumber().getPrecedence() == 1) {
								out.print("<span style='color: red;'>高</span>");
							} else if(w.getSmallnessBatchNumber().getPrecedence() == 2) {
								out.print("<span style='color: red;'>中</span>");
							} else {
								out.print("<span style='color: red;'>低</span>");
							}
						 %>
					</td>
					<td>
						<h2 style="color: red"><%=w.getSmallnessBatchNumber().getBoxNumber() %></h2>
					</td>
					<td>
						<%=TimeUtil.formatDateTime(w.getSmallnessBatchNumber().getDateTime()) %>
					</td>
					<td>
						<%
							if(w.getSmallnessBatchNumber().getIsDistribute() == 1) {
								out.print("任务待发");
							} else if(w.getSmallnessBatchNumber().getIsDistribute() == 2) {
								out.print("任务已发");
							} else if(w.getSmallnessBatchNumber().getIsDistribute() == 3) {
								out.print("补制");
							} else if(w.getSmallnessBatchNumber().getIsDistribute() == 4) {
								out.print("审核中");
							}
						 %>
					</td>
					<td>
						<%=w.getSmallnessBatchNumber().getOperator() %>	
					</td>
					<td>
						<a style="text-decoration:none;" href="javascript:void(0);" onclick="javascript:print(<%=w.getSmallnessBatchNumber().getId() %>,<%=w.getSmallnessBatchNumber().getIsRemakes() %>,<%=request.getAttribute("flowTypeId") %>,<%=w.getSmallnessBatchNumber().getIsDistribute() %>,'<%=w.getSmallnessBatchNumber().getOperator() %>');" >
							打印
						</a>		
					</td>	
				</tr>
			
			 <%
			 }
			 	}
			  %>

		</table>
		</div>
		<!--  <input class="button" type="button" value="整批重制" onclick="addRefashion('smallnessBatchNumberIds')"/>
		<input class="button" type="button" value="审核" onclick="findAudit()">
		-->
		<input type="hidden" name="flowTypeId" value='<%=request.getAttribute("flowTypeId") %>'/>
		</form>
		<form action="WorkFlow!list.action" method="post" id="fenye">
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			
			<%
				Page p = (Page)request.getAttribute("page");
			 %>
			<input type="hidden" name="page.lastPage" value="<%=p.getLastPage() * p.getPageSize() %>" />
			<tr>
				<td style="text-align: left;padding-left: 10px;">
					共&nbsp;<%=p.getLastPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;第&nbsp;<%=p.getCurrentPage() %>&nbsp;页
				</td>
				<td style="text-align: right;">
				
					<%
						if(p.getCurrentPage() != 1) {
					%>
							<a href="WorkFlow!list.action?flowTypeId=<%=request.getAttribute("flowTypeId") %>&page.currentPage=1&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">第一页</a>
					<%
						} else {
					%>
							<span style="color:gray;">第一页</span>
					<%
						}
					 %>
					
					
					<%
						if(p.getCurrentPage() == 1) {
					%>
							<span style="color:gray;">上一页</span>
					<% 
						} else {
					%>
							<a href="WorkFlow!list.action?flowTypeId=<%=request.getAttribute("flowTypeId") %>&page.currentPage=<%=p.getCurrentPage()-1 %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">上一页</a>
					<%
						}
					 %>
					 
					 
					 <%
						if(p.getCurrentPage() == p.getLastPage()) {
					%>
							<span style="color:gray;">下一页</span>
					<%
						} else {
					%>
							<a href="WorkFlow!list.action?flowTypeId=<%=request.getAttribute("flowTypeId") %>&page.currentPage=<%=p.getCurrentPage()+1 %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">下一页</a>
					<%						
						}					 
					  %>
					  
					
					<%
						if(p.getCurrentPage() == p.getLastPage()) {
					%>
							<span style="color:gray;">最后一页</span>
					<%
						} else {
					%>
						<a href="WorkFlow!list.action?flowTypeId=<%=request.getAttribute("flowTypeId") %>&page.currentPage=<%=p.getLastPage() %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">最后一页</a>
					<%
						}
					 %>
					  
					<input type="hidden" name="flowTypeId" value="<%=request.getAttribute("flowTypeId") %>" />
					<input type="text" name="page.currentPage" value="<%=p.getCurrentPage() %>" style="width: 30px;" />
					<input type="button" value="转" class="button" onclick="javascript:document.getElementById('fenye').submit();" style="width: 35px;text-align: center;" />
				</td>
			</tr>
		</table>
		</form>
		<form action="" id="myprint"  target="_blank">
			<input id="operator" name="operator" type="hidden" value="test"/>
			<input id="sid" name="smallnessBatchNumberId" type="hidden" value="test"/>
			<input id="isRemakes" name="isRemakes" type="hidden" value="test"/>
			<input id="ftid" name="flowTypeId" type="hidden" value="test"/>
			<input id="isDistribute" name="isDistribute" type="hidden" value="test"/>
		</form>
	</body>
</html>
