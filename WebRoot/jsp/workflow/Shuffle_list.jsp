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

		<title>洗牌</title>
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
		//提交给下一流程
		function save(){
			if(window.confirm("确定提交?")) {
				if(checkValue()){
					document.getElementById("myForm").action="NumberPlate!shuffleUpdate.action";
					document.getElementById("myForm").submit();
				} else {
					alert('请选择一批需要提交到下一流程的。');
				}
			}
		}	
		//检查有没有选中复选框
		function checkValue(){
			var obj = document.getElementsByName("smallnessBatchNumberIds");
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked)
					return true;
			}
			return false;
		}
		
		function saveOperator(select){
			var selRow = document.getElementsByName(select);
			var str=false;
			for(var i=0;i<selRow.length;i++){
		   			if(selRow[i].value!="0"){
		   				str=true;
		   			}
		       }
		       if(str){ 
		       		 document.getElementById("myForm").submit();
		       }else{
		      		 alert("请选择获取任务的员工！！！！");
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
			var str=false;
			for(var i=0;i<selRow.length;i++){
		   			if(selRow[i].checked == true){
		   				str=true;
		   			}
		    }
		    if(str) {
		    	document.getElementById("myForm").action="BatchRefashion!save.action";
				document.getElementById("myForm").submit();
		    } else {
		    	alert("请选择要整批重制的批号");
		    }
		      
			
		}
		
		function isSelect(isSelect, removeSelect) {
			var users = document.getElementById(isSelect);
			var allLength = users.options.length;
			var name = "";
			for(var i = 0; i < users.options.length; i++) {
				if(users.options[i].selected == true) {
					name = users.options[i].value;
				}
			}
			
		
			var removes = document.getElementById(removeSelect);
			for(var j=0; j<removes.options.length; j++) {
				if(removes.options[j].value == name) {
					if(removes.options[j].value != 0) {
						removes.options.remove(j);
					}
				}
			}
			
			var isTrue = true;
			for(var i = 0; i < users.options.length; i++) {
				if(users.options[i].selected != true) {
					for(var j=0; j<removes.options.length; j++) {
						if(users.options[i].value == removes.options[j].value) {
							isTrue = false;
						}
					}
					if(isTrue) {
						if(users.options[i].value != 0) {
							var varItem = new Option(users.options[i].text, users.options[i].value); 
							removes.options.add(varItem);
						}
					}
					isTrue = true;
				}
			}
			
			
			
		}
  </script>
	</head>

	<body>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;<a href="WorkFlow!list.action?flowTypeId=2">洗牌流程</a></div>
		<form action="TaskAllocation!save.action" id="myForm" method="post">
		<s:token></s:token>
		<input type="hidden" name="flowTypeId" value="<%=request.getAttribute("flowTypeId") %>" />
		
		<div class="saixuan">
				员工名称：
				<select name="select" id="operator1" onchange="isSelect('operator1','operator2')">
					<option value="0">--请选择--</option>
						<%
							List<Users> users = (List<Users>)request.getAttribute("users");
							for(Users u : users) {
						%>
							<option value='<%=u.getId() %>'><%=u.getName() %></option>
						<%
							}
						 %>
				</select>
				<select name="select"  id="operator2" onchange="isSelect('operator2','operator1')" >
					<option value="0">--请选择--</option>
						<%
							for(Users u : users) {
						%>
							<option value='<%=u.getId() %>'><%=u.getName() %></option>
						<%
							}
						 %>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 获取数量（批）：<input type="text" name="sum" id="sum"/>&nbsp;&nbsp;&nbsp;&nbsp; -->
			<input type="button" class="button" value="获取" onclick="saveOperator('select')"/>
		</div>
		<hr />
		<div>
			<table  cellpadding="1" cellspacing="1"  >
				<tr class="th">
					<td>
						<input  style="width: 25px;height:25px;" type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('smallnessBatchNumberIds')"/>
					</td>
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
				</tr>
				
				<%
				
					List<WorkFlow> workFlows = (List<WorkFlow>)request.getAttribute("workFlows");
						if(workFlows == null || workFlows.size() == 0) {
				%>
							<tr>
								<td colspan="10" align="center"><span style="color:red;">没有任何记录</span></td>
							</tr>
				<%
						} else {
						for(WorkFlow w : workFlows) {
				 %>
					<tr>
						<td>
						<input name="smallnessBatchNumberIds" type="checkbox"   style="width: 25px;height:25px;"
									value="<%=w.getSmallnessBatchNumber().getId() %>"
									id="chkid<%=w.getSmallnessBatchNumber().getId() %>" />
						</td>
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
							<h2 style="color: red"> 
								<%=w.getSmallnessBatchNumber().getBoxNumber() %>(
								<%
									if(w.getSmallnessBatchNumber().getBoxNumberType() == 1) {
										out.print("白");
									} else if(w.getSmallnessBatchNumber().getBoxNumberType() == 2) {
										out.print("黄");
									} else {
										out.print("蓝");
									}
								 %>)
							</h2>
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
					</tr>
				
					 <%
					 }
					 	}
					  %>

				
			</table>
		</div>
		</form>
		
		<form action="WorkFlow!list.action" method="post" id="fenye">
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			
			<%
				Page p = (Page)request.getAttribute("page");
			 %>
			<input type="hidden" name="page.lastPage" value="<%=p.getLastPage() * p.getPageSize() %>" />
			<tr>
				<td style="text-align: left;">
					<input type="button" class="button" value="提交下一流程" onclick="save()"/>
				</td>
				<td style="text-align: right;">
					共&nbsp;<%=p.getLastPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;第&nbsp;<%=p.getCurrentPage() %>&nbsp;页
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
		
		
	</body>
</html>
