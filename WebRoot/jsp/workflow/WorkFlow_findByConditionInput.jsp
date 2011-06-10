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
					window.location.href="SmallnessBatchNumber!upgradelevle.action?precedence="+precedence+"&smallnessBatchNumberId="+sbatchNumberId;
					document.getElementById("myform").submit();
				}else{
					alert("已经是最高级别！");
				}
			}
			
			function submitForm(currentPage) {
				document.getElementById("currentPage").value=currentPage;
				document.getElementById("myform").submit();
			}
			
			function modifyDate(id){
				var cid = 'c'+id;
				var tid = 't'+id;
				var uid = 'u'+id;
				document.getElementById(tid).style.display="none";
				document.getElementById(uid).style.display="block";
			}
			
			function cel(id){
				var uid = 'u'+id;
				var tid = 't'+id;
				document.getElementById(tid).style.display="block";
				document.getElementById(uid).style.display="none";
			}
			
			function modifyone(id){
				var cid = 'c'+id;
				var yid = 'y'+id;
				var cbox = document.getElementById(cid).value;
				var ctype = document.getElementById(yid).value;
				if(cbox==null||cbox==''||cbox==0)
				{
					alert("填写箱号！");
				}
				else
				{
					document.getElementsByName("boxtype").value=ctype;
					document.getElementsByName("boxnumber").value=cbox;
					window.location.href="SmallnessBatchNumber!modifyBox.action?smallnessBatchNumberId="+id+"&boxnumber="+cbox+"&boxType="+ctype;
				}
			}
		</script>
	</head>

	<body>
		<%
			String date = (String)request.getAttribute("date");
			String place = (String)request.getAttribute("place");
			String numberPlate = (String)request.getAttribute("numberPlate");
			String bigNo = (String)request.getAttribute("bigNo");
			int flowTypeId = (Integer)request.getAttribute("flowTypeId");
			Page p = (Page)request.getAttribute("page");
		 %>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;流程查询</div>
		
		<form action="WorkFlow!findByCondition.action" id="myform" method="post">
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
		
			<table  cellpadding="1" cellspacing="1"  >
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
						流程
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
							<a href="NumberPlate!findByOrder.action?smallnessBatchNumberId=<%=w.getSmallnessBatchNumber().getId() %>" target="mainFrame">
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
						<td width="100">
							<%
								if(w.getSmallnessBatchNumber().getPrecedence() == 1) {
									out.print("<span style='color: red;'>高</span>");
								} else if(w.getSmallnessBatchNumber().getPrecedence() == 2) {
									out.print("<span style='color: red;'>中</span>");
								} else {
									out.print("<span style='color: red;'>低</span>");
								}
							 %>
							<input type="button" class="button" value="升级" onclick="upgrade(<%=w.getSmallnessBatchNumber().getPrecedence() %>,<%=w.getSmallnessBatchNumber().getId() %>)"></input>
						</td>
						<td>
						<div id="t<%=w.getSmallnessBatchNumber().getId() %>" style="display: block;">
						<a href="javascript:modifyDate(<%=w.getSmallnessBatchNumber().getId() %>);">
							<h2 style="color: red"> 
								<%=w.getSmallnessBatchNumber().getBoxNumber() %>
								(<%
									if(w.getSmallnessBatchNumber().getBoxNumberType() == 1) {
										out.print("白");
									} else if(w.getSmallnessBatchNumber().getBoxNumberType() == 2) {
										out.print("黄");
									} else if(w.getSmallnessBatchNumber().getBoxNumberType() == 3) {
										out.print("蓝");
									} else {
										out.print("空");
									}
								 %>)
							</h2>
							</a>
						</div>
						<div id="u<%=w.getSmallnessBatchNumber().getId() %>" style="display: none;" >
							<input  id="c<%=w.getSmallnessBatchNumber().getId() %>" value="<%=w.getSmallnessBatchNumber().getBoxNumber() %>" type="text" style="width: 28px;" />
						  	<select id="y<%=w.getSmallnessBatchNumber().getId() %>" value="1">
	  							<option value="1" <%if(w.getSmallnessBatchNumber().getBoxNumberType()==1) out.print("selected='selected'"); %> >白色</option>
	  							<option value="2" <%if(w.getSmallnessBatchNumber().getBoxNumberType()==2) out.print("selected='selected'"); %> >黄色</option>
	  							<option value="3" <%if(w.getSmallnessBatchNumber().getBoxNumberType()==3) out.print("selected='selected'"); %> >蓝色</option>
	  						</select>
	  						<input type="button" value="修改" onclick="javacript:modifyone(<%=w.getSmallnessBatchNumber().getId() %>)" />
	  						<input type="button" value="取消" onclick="javascript:cel(<%=w.getSmallnessBatchNumber().getId() %>)" />
						</div>
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
								} else if(w.getSmallnessBatchNumber().getIsDistribute() == 5) {
									out.print("停制中");
								}
							 %>
						</td>
						<td>
							<%
								if(w.getCurrentFlow().getId() == 1) {
									out.print("反压");
								} else if(w.getCurrentFlow().getId() == 2) {
									out.print("洗牌");
								} else if(w.getCurrentFlow().getId() == 3) {
									out.print("滚油");
								} else if(w.getCurrentFlow().getId() == 4) {
									out.print("正压");
								} else if(w.getCurrentFlow().getId() == 5) {
									out.print("质检");
								} else if(w.getCurrentFlow().getId() == 6) {
									out.print("打码");
								} else if(w.getCurrentFlow().getId() == 7) {
									out.print("总质检");
								}
							 %>
						</td>
						<td>
							<a href="SmallnessBatchNumber!findFlowOperator.action?smallnessBatchNumberId=<%=w.getSmallnessBatchNumber().getId() %>">查看</a>
						</td>	
						
					</tr>
				
				<%
					 }
					 	}
					  %>
	
			</table>
		</div>
		
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			
			<tr>
				<td style="text-align: left;padding-left: 10px;">
					第&nbsp;<%=p.getCurrentPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;共&nbsp;<%=p.getLastPage() %>&nbsp;页
				</td>
				<td style="text-align: right;">
				
					<%
						if(p.getCurrentPage() != 1) {
					%>
							<a style="cursor: pointer;" onclick="submitForm(1)">第一页</a>
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
							<a style="cursor: pointer;" onclick="submitForm(<%=p.getCurrentPage() - 1 %>)">上一页</a>
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
							<a style="cursor: pointer;" onclick="submitForm(<%=p.getCurrentPage() + 1 %>)">下一页</a>
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
						<a style="cursor: pointer;" onclick="submitForm(<%=p.getLastPage() %>)">最后一页</a>
					<%
						}
					 %>
					 <input type="text" value="<%=p.getCurrentPage() %>" name="page.currentPage" id="currentPage" style="width: 30px;" />
					<input type="button" value="转" class="button" onclick="document.getElementById('myform').submit();" style="width: 35px;text-align: center;" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="boxtype" value="0"/>
		<input type="hidden" name="boxnumber" value="0"/>
		</form>
		
	</body>
</html>