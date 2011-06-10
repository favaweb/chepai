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
		
		function checkWorks(select) {
			var works = document.getElementsByName(select);
			var isSelected = false;
			for(var i=0; i<works.length; i++) {
				if(works[i].checked == true) {
					isSelected = true;
				}
			}
			if(isSelected) {
				document.getElementById("myform").action="BigBatchNumber!goFlows.action";
				document.getElementById("myform").submit();
			} else {
				alert("请选择要下发的任务!");
			}
		}
		
		function upGrade(url,precedence) {
			if(precedence == 1) {
				alert("已经是最高级别");
			} else {
				document.getElementById("myform").action=url;
				document.getElementById("myform").submit();
			}
		}
	</script>
	</head>
			

	<body>
		<%
			Page p = (Page)request.getAttribute("page");
		 %>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;生产单管理&nbsp;>&nbsp;<a href="BigBatchNumber!findByMakeStatus.action">待发任务</a></div>
		<hr />
		<form action="#" method="post" id="myform">
			<s:token></s:token>
		
		<div>
			<table  cellpadding="1" cellspacing="1"   cellpadding="1" cellspacing="1"  cellpadding="1" cellspacing="1">
				<tr class="th">
					<td>
						<input type="checkbox" style="width: 25px;height:25px;" onclick="setChkboxStatus('goFlows')"/>
					</td>
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
					List<BigBatchNumber> bigBatchs = (List<BigBatchNumber>)request.getAttribute("bigBatchNumbers");
					if(bigBatchs == null || bigBatchs.size() == 0) {
				
				%>
					<tr>
						<td colspan="7" align="center"><span style="color:red;">没有任何记录</span></td>
					</tr>
				<%	
					} else {
						for(BigBatchNumber b : bigBatchs) {
				%>
					<tr>
						<td>
							<input type="checkbox" style="width: 25px;height:25px;" name="goFlows" value="<%=b.getId() %>" />
						</td>
						<td>
							<a href="SmallnessBatchNumber!findByMakeStatus.action?bigBatchNumberId=<%=b.getId() %>">
								<%=b.getBigBattchNumber() %>
							</a>
						</td>
						<td>
							<%=b.getPlace() %>
						</td>
						<td>
							<%
								if(b.getOrderType() == 1) {
									out.print("补制");
								} else if(b.getOrderType() == 2) {
									out.print("自选");
								} else {
									out.print("号段");
								}
							 %>
						</td>
						<td>
							<%
								if(b.getPrecedence() == 1) {
									out.print("<span style='color: red;'>高</span>");
								} else if(b.getPrecedence() == 2) {
									out.print("<span style='color: red;'>中</span>");
								} else {
									out.print("<span style='color: red;'>低</span>");
								}
							 %>
								 <input type="button" onclick="upGrade('BigBatchNumber!upGrade.action?bid=<%=b.getId() %>','<%=b.getPrecedence() %>')" value="升级" class="button"/>
						</td>
						<td>
							<%=TimeUtil.formatDateTime(b.getCreateTime()) %>
						</td>
						<td>
							<%=b.getAmount() %>
						</td>
					</tr>
				<%
						}
					}
				
				 %>
	
			</table>
		</div>
		
	</form>
	
	<form action="BigBatchNumber!findByMakeStatus.action" method="post" id="fenye">
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			<input type="hidden" name="page.lastPage" value="<%=p.getLastPage() * p.getPageSize() %>" />
			<tr>
				<td style="text-align: left;">
					<%
						if(bigBatchs != null && bigBatchs.size() != 0) {
					%>
						<input type="button" onclick="checkWorks('goFlows')" class="button" value="下发任务" />
					<%	
						}
					 %>
				</td>
				<td style="text-align: right;">
					共&nbsp;<%=p.getLastPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;第&nbsp;<%=p.getCurrentPage() %>&nbsp;页
					<%
						if(p.getCurrentPage() != 1) {
					%>
							<a href="BigBatchNumber!findByMakeStatus.action?page.currentPage=1&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">第一页</a>
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
							<a href="BigBatchNumber!findByMakeStatus.action?page.currentPage=<%=p.getCurrentPage()-1 %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">上一页</a>
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
							<a href="BigBatchNumber!findByMakeStatus.action?page.currentPage=<%=p.getCurrentPage()+1 %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">下一页</a>
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
						<a href="BigBatchNumber!findByMakeStatus.action?page.currentPage=<%=p.getLastPage() %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">最后一页</a>
					<%
						}
					 %>
					<input type="text" value="<%=p.getCurrentPage() %>" name="page.currentPage" style="width: 30px;" />
					<input type="button" value="转"  class="button" onclick="javascript:document.getElementById('fenye').submit();" style="width: 35px;text-align: center;" />
				</td>
			</tr>
		</table>
		</form>











	</body>
</html>
