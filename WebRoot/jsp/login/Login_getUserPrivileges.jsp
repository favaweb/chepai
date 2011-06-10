<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<link href="css/css.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
				background-image: url(Images/left.gif);
			}
        </style>
        
        <%
        	List<Privilege> onePrivileges = (List<Privilege>)request.getAttribute("onePrivileges");
        	List<Privilege> twoPrivileges = (List<Privilege>)request.getAttribute("twoPrivileges");
        	List<Privilege> threePrivileges = (List<Privilege>)request.getAttribute("threePrivileges");
        	List<Privilege> fourPrivileges = (List<Privilege>)request.getAttribute("fourPrivileges");
        	List<Privilege> fivePrivileges = (List<Privilege>)request.getAttribute("fivePrivileges");
        	Users user = (Users)session.getAttribute("user");
        	if(threePrivileges != null && threePrivileges.size() == 1 && onePrivileges == null && twoPrivileges == null && fourPrivileges == null && fivePrivileges == null) {
        %>
        	<SCRIPT type="text/javascript">
				var menu=window.parent.document.getElementsByTagName("frameset")[1]; 
				menu.cols="0,*";
				window.parent.document.getElementById("mainFrame").src="../<%=threePrivileges.get(0).getUrl()%>";
	        </SCRIPT>
        <%
        	}
         %>
		<SCRIPT language=JavaScript>
			function tupian(idt){
			    var nametu="xiaotu"+idt;
			    var tp = document.getElementById(nametu);
			    tp.src="Images/ico05.gif";
				
				for(var i=1;i<220;i++)
				{
				  
				  var nametu2="xiaotu"+i;
				  if(i!=idt*1)
				  {
				    var tp2=document.getElementById('xiaotu'+i);
					if(tp2!=undefined)
				    {tp2.src="Images/ico06.gif";}
				  }
				}
			}
			
			function list(idstr){
			
				var name1="subtree"+idstr;
				var name2="img"+idstr;
				var objectobj=document.all(name1);
				var imgobj=document.all(name2);
				
				
				//alert(imgobj);
				
				if(objectobj.style.display=="none"){
					for(i=1;i<10;i++){
						var name3="img"+i;
						var name="subtree"+i;
						var o=document.all(name);
						if(o!=undefined){
							o.style.display="none";
							var image=document.all(name3);
							//alert(image);
							image.src="Images/ico04.gif";
						}
					}
					objectobj.style.display="";
					imgobj.src="Images/ico03.gif";
				}
				else{
					objectobj.style.display="none";
					imgobj.src="Images/ico04.gif";
				}
			}
		</SCRIPT>
</head>

<body>
<table  cellpadding="1" cellspacing="1"  width="200" border="0" cellpadding="0" cellspacing="0" class="left-table01">
  <tr>
    <TD>
    
    	
		<table  cellpadding="1" cellspacing="1"  width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td width="207" height="55" background="Images/nav01.gif">
				<table  cellpadding="1" cellspacing="1"  width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="25%" rowspan="2"><img src="Images/ico02.gif" width="35" height="35" /></td>
					<td width="75%" height="22" class="left-font01">您好：<span class="left-font02"><%=user.getName() %></span></td>
				  </tr>
				  <tr>
					<td height="22" class="left-font01">
						[&nbsp;<a href="#" onclick="ComfirmExit(1)" target="_parent" class="left-font01">退出</a>&nbsp;]</td>
				  </tr>
				</table>
			</td>
		  </tr>
		</table>
		

		<%
			if(twoPrivileges != null && twoPrivileges.size() != 0) {
		%>
			<table  cellpadding="1" cellspacing="1"  width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table  cellpadding="1" cellspacing="1"  width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img1" id="img1" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('1');" >生产单管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table  cellpadding="1" cellspacing="1"  id="subtree1" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<%
					for(int i=0;i<twoPrivileges.size();i++) {
						Privilege p = twoPrivileges.get(i);
				 %>
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu1<%=i %>" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<%=p.getUrl() %>" target="mainFrame" class="left-font03" onClick="tupian('1<%=i %>');"><%=p.getModel() %></a></td>
					</tr>
				<%
					}
				 %>
   			</table>
		<%	
			}
		 %>


		<%
			if(threePrivileges != null && threePrivileges.size() != 0) {
		%>
			<table  cellpadding="1" cellspacing="1"  width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table  cellpadding="1" cellspacing="1"  width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img2" id="img2" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('2');" >生产流程管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table  cellpadding="1" cellspacing="1"  id="subtree2" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<%
					for(int i=0;i<threePrivileges.size();i++) {
						Privilege p = threePrivileges.get(i);
				 %>
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu2<%=i %>" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<%=p.getUrl() %>" target="mainFrame" class="left-font03" onClick="tupian('2<%=i %>');"><%=p.getModel() %></a></td>
					</tr>
				<%
					}
				 %>
   			</table>
		<%	
			}
		 %>
		
		
		<%
			if(fourPrivileges != null && fourPrivileges.size() != 0) {
		%>
			<table  cellpadding="1" cellspacing="1"  width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table  cellpadding="1" cellspacing="1"  width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img3" id="img3" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('3');" >仓库物资管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table  cellpadding="1" cellspacing="1"  id="subtree3" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<%
					for(int i=0;i<fourPrivileges.size();i++) {
						Privilege p = fourPrivileges.get(i);
				 %>
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu3<%=i %>" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<%=p.getUrl() %>" target="mainFrame" class="left-font03" onClick="tupian('3<%=i %>');"><%=p.getModel() %></a></td>
					</tr>
				<%
					}
				 %>
   			</table>
		<%	
			}
		 %>
		
		
		
		<%
			if(fivePrivileges != null && fivePrivileges.size() != 0) {
		%>
			<table  cellpadding="1" cellspacing="1"  width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table  cellpadding="1" cellspacing="1"  width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img4" id="img4" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('4');" >查询统计</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table  cellpadding="1" cellspacing="1"  id="subtree4" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<%
					for(int i=0;i<fivePrivileges.size();i++) {
						Privilege p = fivePrivileges.get(i);
				 %>
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu4<%=i %>" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<%=p.getUrl() %>" target="mainFrame" class="left-font03" onClick="tupian('4<%=i %>');"><%=p.getModel() %></a></td>
					</tr>
				<%
					}
				 %>
   			</table>
		<%	
			}
		 %>
		
		
		
		<%
			if(onePrivileges != null && onePrivileges.size() != 0) {
		%>
			<table  cellpadding="1" cellspacing="1"  width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table  cellpadding="1" cellspacing="1"  width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img5" id="img5" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('5');" >系统管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table  cellpadding="1" cellspacing="1"  id="subtree5" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<%
					for(int i=0;i<onePrivileges.size();i++) {
						Privilege p = onePrivileges.get(i);
				 %>
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu5<%=i %>" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<%=p.getUrl() %>" target="mainFrame" class="left-font03" onClick="tupian('5<%=i %>');"><%=p.getModel() %></a></td>
					</tr>
				<%
					}
				 %>
   			</table>
		<%	
			}
		 %>

		

		
		
		

	  </TD>
  </tr>
  
</table>
</body>
</html>
