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
			function print(id){
				document.getElementById("wait").style.display="block";
				window.location.href="BigBatchNumber!printSmall.action?smallBatch.id="+id;
			}
			function modifyDate(id){
				var cid = 'c'+id;
				var tid = 't'+id;
				var uid = 'u'+id;
				document.getElementById(tid).style.display="none";
				document.getElementById(uid).style.display="block";
				WdatePicker({el:cid});
			}
			function modifyone(id){
				var cid = 'c'+id;
				var ctext = document.getElementById(cid).value;
				if(ctext==null||ctext=='')
				{
					alert("请选择时间！");
					WdatePicker({el:cid});
				}
				else
				{
					window.location.href="SmallnessBatchNumber!modifySendtime.action?smallnessBatchNumber.id="+id+"&sendTime="+ctext;
				}
			}
			function cel(id){
				var uid = 'u'+id;
				var tid = 't'+id;
				document.getElementById(tid).style.display="block";
				document.getElementById(uid).style.display="none";
			}
	</script>
	</head>

	<body>
<div style="position: absolute;width: 100%;height: 100%;background:white;display: none;" id="wait" >
  	<div style="width:100%;height:100%;text-align: center;background: #dddddd;">
  		打印中...
  	</div>
  </div>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;查询统计&nbsp;>&nbsp;发货查询&nbsp;>&nbsp;发货详情</div>
		<hr/>
		<br />
	<div>
	<%
		String countbox = request.getAttribute("countbox").toString();
		String notsendbox = request.getAttribute("notsendbox").toString();
		String notfinishedbox =request.getAttribute("notfinishedbox").toString();
	 %>
	总箱数：<%=countbox %>&nbsp;&nbsp;未完成：<%=notfinishedbox %>&nbsp;&nbsp;未发货：<%=notsendbox %>
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
					数量
				</td>
				<td>
					发货时间
				</td>
				<td>
					发货状态
				</td>
				<td>
					包装单
				</td>
				<td>
					操作人
				</td>
			</tr>
			
			<%
				List<SmallnessBatchNumber> smalls = (List<SmallnessBatchNumber>)request.getAttribute("smallnessBatchNumbers");
				int bigBatchNumberId = (Integer)request.getAttribute("bigBatchNumberId");
				for(SmallnessBatchNumber s : smalls) {
			%>
			
				<tr>
					<td>
						<%=s.getBigBatchNumber().getBigBattchNumber() %>
					</td>
					<td>
						<a href="NumberPlate!findByOrder.action?smallnessBatchNumberId=<%=s.getId() %>" target="mainFrame">
							<%=s.getSmallnessBatchNumber() %>
						</a>
					</td>
					<td>
						<%=s.getPlace() %>
					</td>
					<td>
						<%
							if(s.getOrderType() == 1) {
								out.print("补制");
							} else if(s.getOrderType() == 2) {
								out.print("自选");
							} else {
								out.print("号段");
							}
						%>
					</td>
					<td>
						<%
							if(s.getPrecedence() == 1) {
								out.print("<span style='color: red;'>高</span>");
							} else if(s.getPrecedence() == 2) {
								out.print("<span style='color: red;'>中</span>");
							} else {
								out.print("<span style='color: red;'>低</span>");
							}
						%>
					</td>
					<td>
						<%=s.getAmount() %>
					</td>
					<td>
						<div id="t<%=s.getId()%>" style="diaplay:block;" >
						<%
							if(s.getSendTime() != null) {
						%>
						<%
								out.print(TimeUtil.formatDate(s.getSendTime()));
							} else {
							}
						%>
						</div>
						<div id="u<%=s.getId() %>" style="display: none;">
						<input id="c<%=s.getId() %>" type="text" onClick="WdatePicker()" class="Wdate" readonly="readonly" style="width: 100px;" />
						<input type="button" value="确定" onclick="javascript:modifyone(<%=s.getId() %>);" />
						<input type="button" value="取消" onclick="javascript:cel(<%=s.getId() %>);" />
						</div>
					</td>
					<td>
						<%
							if(s.getIsDeliverGoods() == 1) {
								out.print("未发货");
							} else {
								out.print("<span style='color:red;'>已发</span>");
							}
						 %>
					</td>
					<td>
					<a href="NumberPlate!packPrint.action?smallnessBatchNumberId=<%=s.getId() %>" target="_blank">打印</a>
					</td>
					<td>
						<a href="SmallnessBatchNumber!findFlowOperator.action?smallnessBatchNumberId=<%=s.getId() %>">查看</a>
					</td>
				</tr>
			<%	
				}
			 %>
	
		</table>
	</div>
	
	
	<form action="SmallnessBatchNumber!findIsDeliverByBig.action" method="post" id="fenye">
		<%
			Page p = (Page)request.getAttribute("page");
		 %>
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			<input type="hidden" name="page.lastPage" value="<%=p.getLastPage() * p.getPageSize() %>" />
			<input type="hidden" name="bigBatchNumberId" value="<%=bigBatchNumberId %>" />
			<tr>
				<td style="text-align: left;">
					共&nbsp;<%=p.getLastPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;第&nbsp;<%=p.getCurrentPage() %>&nbsp;页
				</td>
				<td style="text-align: right;">
					<%
						if(p.getCurrentPage() != 1) {
					%>
							<a href="SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=<%=bigBatchNumberId %>&page.currentPage=1&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">第一页</a>
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
							<a href="SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=<%=bigBatchNumberId %>&page.currentPage=<%=p.getCurrentPage()-1 %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">上一页</a>
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
							<a href="SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=<%=bigBatchNumberId %>&page.currentPage=<%=p.getCurrentPage()+1 %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">下一页</a>
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
						<a href="SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=<%=bigBatchNumberId %>&page.currentPage=<%=p.getLastPage() %>&page.lastPage=<%=p.getLastPage() * p.getPageSize() %>">最后一页</a>
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