<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>

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
	<script type="text/javascript">
		function submitForm(currentPage) {
			document.getElementById("currentPage").value=currentPage;
			document.getElementById("myform").action = "BigBatchNumber!findIsDeliverGoods.action";
			document.getElementById("myform").submit();
		}
		function print(id){
			document.getElementById("wait").style.display="block";
			window.location.href="BigBatchNumber!printBig.action?bigBatchNumber.id="+id;
		}
		var selFlag = true;
		function setChkboxStatus(tableId)
		{
			//var selFlag = true;
		   	var selRow = document.getElementsByName(tableId);
		   	//selRow[1] = true;
		   	if(selFlag){
		  	 	alert();
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
		function delivergoods(select){
			var selRow = document.getElementsByName(select);
			var str=false;
			for(var i=0;i<selRow.length;i++){
	   			if(selRow[i].checked == true){
   					str=true;
   				}
			}
			if(str){
				document.getElementById("myform").action = "SmallnessBatchNumber!changeIsDeliverGoodsbyBigList.action";
				document.getElementById("myform").submit();
			}else{
				alert("请选择需要发货的大批号。");
			}
		}
	</script>
	</head>

	<body>
	  <div style="position: absolute;width: 100%;height: 100%;background:white;display: none;" id="wait" >
  	<div style="width:100%;height:100%;text-align: center;background: #dddddd;">
  		打印中...
  	</div>
  </div>
		
		<%
			int isDeliverGoods = (Integer)request.getAttribute("isDeliverGoods");
			String startTime = (String)request.getAttribute("startTime");
			String endTime = (String)request.getAttribute("endTime");
			String place = (String)request.getAttribute("place");
			String plateNo = (String)request.getAttribute("plateNo");
			String bigNo = (String)request.getAttribute("bigNo");
			List<SmallnessBatchNumber> smalls = (List<SmallnessBatchNumber>)request.getAttribute("smalls");
			List<BigBatchNumber> bigs = (List<BigBatchNumber>)request.getAttribute("bigBatchNumbers");
			Page p = (Page)request.getAttribute("page");
		 %>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;仓库物资管理&nbsp;>&nbsp;发货管理&nbsp;>&nbsp;发货查询</div>
		<span  style="font-size: 14px	;padding: 10px;text-align: center;" ><h4 style="text-align: center;">
			<a href="BigBatchNumber!findIsDeliverGoods.action">发货查询</a>
&nbsp;
			<a
				href="StatisticsInput!findFinishedList.action">发货统计</a>&nbsp;
			<a
				href="PostAddress!list.action">发货地址</a>&nbsp;
			<a
				href="StatisticsInput!boxstat.action">发货箱数</a>

		</h4></span>
		<hr/>
		<form action="BigBatchNumber!findIsDeliverGoods.action" method="post" id="myform">
			<div class="saixuan">
				发货状态：
				<select name="isDeliverGoods">
					<option value="0">--请选择--</option>
					<option value="1" <%if(isDeliverGoods == 1)out.print("selected='selected'"); %>>未发货</option>
					<option value="2" <%if(isDeliverGoods == 2)out.print("selected='selected'"); %>>已发货</option>
				</select>&nbsp;
				时间段：<input type="text" style="width: 85px;" name="startTime" value="<%if(startTime != null)out.print(startTime); %>" onClick="WdatePicker()" class="Wdate" readonly="readonly" />
				-<input type="text" style="width: 85px;" name="endTime" value="<%if(endTime != null)out.print(endTime); %>" onClick="WdatePicker()" class="Wdate" readonly="readonly" />&nbsp;
				地区：<input  style="width: 85px;" type="text" name="place" value="<%if(place != null)out.print(place); %>"  />&nbsp;
				大批号：<input type="text" name="bigNo" value="<%if(bigNo != null)out.print(bigNo); %>"  />&nbsp;
				车牌号：<input style="width: 85px;" type="text" name="plateNo" value="<%if(plateNo != null)out.print(plateNo); %>"  />&nbsp;
				<input type="button" class="button" value="查询" onclick="submitForm(1)"/>
			</div>
			<hr />
		
			
			<%
				if(smalls == null && bigs != null && bigs.size() != 0) {
			%>
			
			<div>
				<table  cellpadding="1" cellspacing="1" >
					<tr class="th">
						<td>
							<input style="width: 25px;height:25px;" type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('bigBatchNumberIds')"/>
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
							数量
						</td>
						<td>
							时间
						</td>
						<td>
							是否发货
						</td>
						<td>
							操作
						</td>
						
					</tr>
					<%
						if(bigs == null || bigs.size() == 0) {
						
					%>
						<tr>
							<td colspan="9" align="center"><span style="color:red;">没有任何记录</span></td>
						</tr>
					<%
						} else {
							for(BigBatchNumber b : bigs) {
					%>
					
					
						<tr>
							<td>
								<input style="width: 25px;height:25px;" name="bigBatchNumberIds" type="checkbox"
									id="chkid<%=b.getId() %>" value="<%=b.getId() %>"  />							
							</td>						
							<td>
								<a href="SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=<%=b.getId() %>">
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
							</td>
							<td>
								<%=b.getAmount() %>
							</td>
							<td>
								<%=b.getCreateTime() %>
							</td>
							<td>
								<%
									if(b.getIsDeliverGoods() == 1) {
										out.print("未发完货");
									} else if(b.getIsDeliverGoods() == 2) {
										out.print("<span style='color:red;'>已发货</span>");
									}
								 %>
							</td>
							<td>
								<a href="javascript:print(<%=b.getId() %>);">打印</a>
							</td>
						</tr>
					<%
							}
						}
					%>
				</table>
			</div>
		
		<%
			//如果是按照车牌号码查询
			} else if(smalls != null) {
		%>
		
		<div>
				<table  cellpadding="1" cellspacing="1" >
					<tr class="th">
						<td>
							<input style="width: 25px;height:25px;" type="checkbox" name="chkAll" id="chkAll" onclick="setChkboxStatus('bigBatchNumberIds')"/>
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
							时间
						</td>
						<td>
							是否发货
						</td>
					</tr>
					
					<%
						for(SmallnessBatchNumber smallBatch : smalls) {
					%>
					
					<tr>
						<td>
							<input style="width: 25px;height:25px;" name="bigBatchNumberIds" type="checkbox" />							
						</td>
						<td>
							<a href="SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=<%=smallBatch.getBigBatchNumber().getId()%>">
								<%=smallBatch.getBigBatchNumber().getBigBattchNumber() %>
							</a>
						</td>
						<td>
							<%=smallBatch.getSmallnessBatchNumber() %>
						</td>
						<td>
							<%=smallBatch.getPlace() %>
						</td>
						<td>
							<%
								if(smallBatch.getOrderType() == 1) {
									out.print("补制");
								} else if(smallBatch.getOrderType() == 2) {
									out.print("自选");
								} else {
									out.print("号段");
								}
							%>
						</td>
						<td>
							<%
								if(smallBatch.getPrecedence() == 1) {
									out.print("<span style='color: red;'>高</span>");
								} else if(smallBatch.getPrecedence() == 2) {
									out.print("<span style='color: red;'>中</span>");
								} else {
									out.print("<span style='color: red;'>低</span>");
								}
							%>
						</td>
						<td>
							<%=smallBatch.getDateTime() %>
						</td>
						<td>
							<%
								if(smallBatch.getIsDeliverGoods() == 1) {
									out.print("该车牌还未发货");
								} else if(smallBatch.getIsDeliverGoods() == 2) {
									out.print("<span style='color:red;'>该车牌已发货</span>");
								}
							 %>
						</td>
					</tr>
					
					<%
						}
					 %>
				</table>
			</div>
			
		<%
			} else {
		%>
			<table  cellpadding="1" cellspacing="1" >
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
							数量
						</td>
						<td>
							时间
						</td>
						<td>
							是否发货
						</td>
						<td>
							操作
						</td>
					</tr>
					<tr>
						<td colspan="8" align="center"><span style="color:red;">没有任何记录</span></td>
					</tr>
		<%
			}
		 %>
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			
			<tr>
				<td style="text-align: left;padding-left: 10px;">
					<input type="button" class="button" value="发货" onclick="delivergoods('bigBatchNumberIds')" />
				</td>
				<td style="text-align: right;">
					第&nbsp;<%=p.getCurrentPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;共&nbsp;<%=p.getLastPage() %>&nbsp;页
				
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
					 <input type="text" name="page.currentPage"  value="<%=p.getCurrentPage() %>" id="currentPage" style="width: 30px;" />
					<input type="button" value="转" class="button" onclick="document.getElementById('myform').submit();" style="width: 35px;text-align: center;" />
				</td>
			</tr>
		</table>
		
		</form>
		
		
		
		
		
		
		
		

	</body>
</html>
