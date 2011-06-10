<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js">	</script>
	<SCRIPT type="text/javascript">
		function checkForm(){
			if(document.getElementById("startDate").value==null || document.getElementById("startDate").value==""){
				alert("开始时间不允许为空！");
				return false;
			}
			return true;
		}
	</SCRIPT>
  </head>
  
  <body>
   <%
   		Map<String,int[]> statistic = (Map<String,int[]>) request.getAttribute("statistic");
  		Date startDate = (Date)request.getAttribute("startDate");
  		Date endDate = (Date)request.getAttribute("endDate");
  		int night = 0;
		int day = 0;
		int count = 0;
   %>
  
 <div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;查询统计&nbsp;&gt; 废品统计<br></div><br/>
	

<hr/>
<div style="margin-top:20px;margin-bottom:20px;text-align:center;">
  <form id="myform" action="StatisticsInput!statisticsMaterial.action" method="post" onsubmit="return checkForm();">
开始时间：<input type="text" id="startDate" value="<%if(startDate!=null){out.print(TimeUtil.formatDate(startDate));} %>"
			name="startDate" onClick="WdatePicker()" class="Wdate"  />
结束时间：<input type="text" id="endDate" value="<%if(endDate!=null){out.print(TimeUtil.formatDate(endDate));} %>"
			name="endDate" onClick="WdatePicker()" class="Wdate"  />

	<input type="submit" value="查询" class="button" />

</form>
</div>


<div align="center" style="margin-top:15px;" >
<h4></h4>

  <table  cellpadding="1" cellspacing="1"  style="width:80%">
  			<tr class="th">
				<td colspan="7"> 
					车牌废品统计表
				</td>
			</tr>
			<%if(startDate!=null){ %>
			<tr>
				<td colspan="7">
					<%=TimeUtil.formatDate(startDate) %>至<%if(endDate==null){out.print("今天");}else out.print(TimeUtil.formatDate(endDate)); %>
				</td>
			</tr>
			<%} %>
			<tr style="text-align:right;">
			</tr>
			<tr class="th">
				<td>车牌类型\类别</td>
				<td>晚班</td>
				<td>白班</td>
				<td>合计</td>
			</tr>
			<%
				if(statistic.size()>0){

				for(String str : statistic.keySet()) {
					out.print("<tr>");
					out.print("<td>"+str+"</td>");
					int i[] = statistic.get(str);
					out.print("<td>"+i[0]+"</td>");
					out.print("<td>"+i[1]+"</td>");
					out.print("<td>"+(i[0]+i[1])+"</td>");
					out.print("</tr>");
					
					night += i[0];
					day += i[1];
					count += (i[0]+i[1]);
				}
				}
			 %>			
			
			
			<tr>
				<td>合计</td>
				<td><%=night %></td>
				<td><%=day %></td>
				<td><%=count %></td>
			</tr>

		</table>
		</div>
  </body>
</html>