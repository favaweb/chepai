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
  		int orderType = Integer.parseInt(request.getAttribute("orderType").toString());
  		List<StatisticsFinishedProducts> finisheds = (List<StatisticsFinishedProducts>)request.getAttribute("statisticsFinisheds");
  		List<String> types =(List<String>) request.getAttribute("types");
  		Date startDate = (Date)request.getAttribute("startDate");
  		Date endDate = (Date)request.getAttribute("endDate");
  		List<String> places = (List<String>)request.getAttribute("places");
  		int count[] = null;
  		if(types!=null){
  			count = new int[types.size()];
  		}
   %>
  
 <div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt; 发货管理&nbsp;&gt; 成品发货统计<br></div><br/>
	
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
<div style="margin-top:20px;margin-bottom:20px;text-align:center;">
  <form id="myform" action="StatisticsInput!findFinishedList.action" method="post" onsubmit="return checkForm();">

类型：
<select name="orderType">
		<option value="1" <%if(orderType==1){ out.print("selected='selected'"); } %> >自选/补制</option>
		<option value="2" <%if(orderType==2){ out.print("selected='selected'"); } %> >号段</option>
	</select>
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
					车牌成品月统计表 <% if(orderType==1) out.print("自选/补制"); %>
 						<%if(orderType==2) out.print("号段"); %>
				</td>
			</tr>
			<% if(startDate!=null){ %>
			<tr>
				<td colspan="7">
					<%=TimeUtil.formatDate(startDate) %>至<%if(endDate==null){out.print("今天");}else out.print(TimeUtil.formatDate(endDate)); %>
				</td>
			</tr>
			<% } %>
			<tr style="text-align:right;">
				<td colspan="7">
					单位：副
				</td>
			</tr>
			<tr class="th">
				<td>地区\类别</td>
				<%	
					if(types!=null){
						int length = types.size();
						if(length>0){
							for(String one:types){
								out.print("<td>" + one+"</td>");
							}
						}
					}
				 %>
			</tr>
			<% if(finisheds!=null && finisheds.size()>0){
				int k = 0;
				for(int i=0;i<places.size();i++){
						out.print("<tr>");
						out.print("<td>"+places.get(i)+"</td>");
					for(int j=0;j<types.size();j++){
						out.print("<td>");
						if(finisheds.get(k).getPlace().equals(places.get(i)) && types.get(j).equals(finisheds.get(k).getTypename())){
							count[j] = finisheds.get(k).getAmount();
							out.print(finisheds.get(k).getAmount());
							if(k<finisheds.size()-1)
								k++;
						}
						else
							out.print(0);						
						out.print("</td>");
					}
						out.print("</tr>");
				}
				}
			%>
			<% 
				if(types!=null){
				out.print("<tr>");
				out.print("<td>合计</td>");
				
					for(int k=0;k<types.size();k++){
						out.print("<td>" + count[k]+ "</td>");
					}
				out.print("</tr>");
				}
				 %>
		</table>
		</div>
  </body>
</html>
