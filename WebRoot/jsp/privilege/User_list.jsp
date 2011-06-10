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
		<script type="text/javascript">
		  function find(cpage,lpage){
		  	 document.getElementById("currentPage").value = cpage;
		  	 document.getElementById("lastPage").value = lpage;
		  	 document.getElementById("myform").submit();
		  }
		</script>
	</head>

	<body>
		<%
				Page p = (Page)request.getAttribute("page");
				int roler = Integer.parseInt(request.getAttribute("roler").toString());
				int daynight = Integer.parseInt(request.getAttribute("daynight").toString());
		%>
		<div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;系统管理&nbsp;&gt; 员工管理</div>
		<hr/>
		<form action="User!list.action" id="myform">
		<input type="hidden" id="currentPage" name="page.currentPage"  />
		<input type="hidden" id="lastPage" name="page.lastPage" />
		岗位:<select name="roler">
						<option value="-1">--请选择--</option>
						<option value="0" <%if(roler==0) out.print("selected='selected'"); %>>普通员工</option>
						<option value="1" <%if(roler==1) out.print("selected='selected'"); %>>组长</option>
						<option value="2" <%if(roler==2) out.print("selected='selected'"); %>>跟单员</option>
						<option value="3" <%if(roler==3) out.print("selected='selected'"); %>>管理员</option>
					</select>
    	值班类型：<select name="daynight" id="daynight">
						<option value="-1" >--请选择--</option>
						<option value="0" <%if(daynight==0) out.print("selected='selected'"); %>>夜班</option>
						<option value="1" <%if(daynight==1) out.print("selected='selected'"); %>>白班</option>
					</select>
		姓名：<input type="text" name="username" />
    	<input type="button" value="查询" class="button" onclick="find('0','0')"/>	
    	</form>	
		<div>
			<table  cellpadding="1" cellspacing="1" >
				<tr class="th">
					<td>员工姓名
					</td>
					<td>
						账号
					</td>
					<td>
						岗位
					</td>
					<td>
						性别
					</td>
					<td>
						值班类型
					</td>
					<td>
						是否启用
					</td>
					<td>
						操作
					</td>
				</tr>
	
				<%
					List<Users> users = (List<Users>)request.getAttribute("users");
					for(Users u : users) {
				%>
				
					<tr>
						<td>
							<%=u.getName() %>
						</td>
						<td>
							<%=u.getAccount() %>
						</td>
						<td>
							<%
								if(u.getRoler() == 0) {
									out.print("普通员工");
								} else if(u.getRoler() == 1) {
									out.print("组长");
								} else if(u.getRoler() == 2) {
									out.print("跟单员");
								} else {
									out.print("管理员");
								}
							 %>
						</td>
						<td>
							<%if(u.isSex())out.print("男");else out.print("女"); %>
						</td>
						<td>
							<%if(u.getDayNight()==1)out.print("白班");else out.print("夜班"); %>
						</td>
						<td>
							<%if(u.getIsvalid()==1)out.print("已启用");else out.print("<span style='color:red;'>未启用</span>"); %>
						</td>
						<td>
							<a href="User!modifyRolerInput.action?user.id=<%=u.getId() %>">
								修改角色
							</a>
							&nbsp;&nbsp;
							<a href="User!modifyInfoInput.action?user.id=<%=u.getId() %>">
								修改基本资料
							</a>
						</td>
					</tr>
				<%
					}
				 %>
			</table>
		</div>

		<form action="User!list.action" method="post" id="fenye">
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			

			<input type="hidden" name="page.lastPage" value="<%=p.getLastPage() * p.getPageSize() %>" />
			<tr>
				<td style="text-align: left;">
					<input type="button" value="添加员工" class="button" onclick="window.location.href='User!addInput.action'" />
				</td>
				<td style="text-align: right;">
					共&nbsp;<%=p.getLastPage() %>&nbsp;页&nbsp;&nbsp;/&nbsp;&nbsp;每页显示<%=p.getPageSize() %>条数据&nbsp;&nbsp;/&nbsp;&nbsp;第&nbsp;<%=p.getCurrentPage() %>&nbsp;页
					<%
						if(p.getCurrentPage() != 1) {
					%>
							<a href="javascript:void(0);" onclick="find(1,<%=p.getLastPage() * p.getPageSize() %>)" >第一页</a>
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
							<a href="javascript:void(0);" onclick="find(<%=p.getCurrentPage()-1 %>,<%=p.getLastPage() * p.getPageSize() %>)" >上一页</a>
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
							<a  href="javascript:find(<%=p.getCurrentPage()+1 %>,<%=p.getLastPage() * p.getPageSize() %>);">下一页</a>
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
						<a href="javascript:void(0);" onclick="find(<%=p.getLastPage() %>,<%=p.getLastPage() * p.getPageSize() %>)" >最后一页</a>
					<%
						}
					 %>
					<input type="text" id="zhuan" name="page.currentPage" value="<%=p.getCurrentPage() %>" style="width: 30px;" />
					<input type="button" value="转" class="button" onclick="javascript:find(document.getElementById('zhuan').value,<%=p.getLastPage() * p.getPageSize() %>);;" style="width: 35px;text-align: center;" />
				</td>
			</tr>
		</table>
		</form>









	</body>
</html>
