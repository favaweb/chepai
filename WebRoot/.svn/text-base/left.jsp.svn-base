<%@ page language="java" import="java.util.*,com.hovto.chepai.privilege.*" pageEncoding="UTF-8"%>
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
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>��Ŀ����ϵͳ</title>
	<style type="text/css">
	<!--
	body {
		margin-left: 0px;
		margin-top: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
		background-image: url(Images/left.gif);
	}
	-->
	</style>
	<link href="css/css.css" rel="stylesheet" type="text/css" />
	<s:if test="threePrivileges != null && threePrivileges.size() ==1 ">
        <SCRIPT type="text/javascript">
			var menu=window.parent.document.getElementsByTagName("frameset")[1]; 
			menu.cols="0,*";
			window.parent.document.getElementById("mainFrame").src="../<s:property value="threePrivileges[0].url"/>";
        </SCRIPT>
	</s:if>

	<SCRIPT language=JavaScript>
	function tupian(idt){
	    var nametu="xiaotu"+idt;
	    var tp = document.getElementById(nametu);
	    tp.src="Images/ico05.gif";
		
		for(var i=1;i<30;i++)
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
<table width="198" border="0" cellpadding="0" cellspacing="0" class="left-table01">
  <tr>
    <TD>
    
    	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td width="207" height="55" background="Images/nav01.gif">
				<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="25%" rowspan="2"><img src="Images/ico02.gif" width="35" height="35" /></td>
					<td width="75%" height="22" class="left-font01">您好：<span class="left-font02">king</span></td>
				  </tr>
				  <tr>
					<td height="22" class="left-font01">
						[&nbsp;<a href="../login.html" target="_top" class="left-font01">退出</a>&nbsp;]</td>
				  </tr>
				</table>
			</td>
		  </tr>
		</table>
		

		<s:if test="twoPrivileges != null && twoPrivileges.size() != 0">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img8" id="img8" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('1');" >生产单管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table id="subtree1" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<s:iterator value="twoPrivileges" var="privilege">
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu20" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<s:property value="#privilege.url" />" target="mainFrame" class="left-font03" onClick="tupian('20');"><s:property value="#privilege.model" /></a></td>
					</tr>
				</s:iterator>
   			</table>
		</s:if>


		<s:if test="threePrivileges != null && threePrivileges.size() != 0">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img8" id="img8" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('2');" >生产流程管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table id="subtree2" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<s:iterator value="threePrivileges" var="privilege">
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu20" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<s:property value="#privilege.url" />" target="mainFrame" class="left-font03" onClick="tupian('20');"><s:property value="#privilege.model" /></a></td>
					</tr>
				</s:iterator>
   			</table>
		</s:if>
		
		<s:if test="fourPrivileges != null && fourPrivileges.size() != 0">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img8" id="img8" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('3');" >仓库物资管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table id="subtree3" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<s:iterator value="fourPrivileges" var="privilege">
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu20" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<s:property value="#privilege.url" />" target="mainFrame" class="left-font03" onClick="tupian('20');"><s:property value="#privilege.model" /></a></td>
					</tr>
				</s:iterator>
   			</table>
		</s:if>
		
		
		
		<s:if test="fivePrivileges != null && fivePrivileges.size() != 0">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img8" id="img8" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('4');" >查询统计</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table id="subtree4" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<s:iterator value="fivePrivileges" var="privilege">
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu20" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<s:property value="#privilege.url" />" target="mainFrame" class="left-font03" onClick="tupian('20');"><s:property value="#privilege.model" /></a></td>
					</tr>
				</s:iterator>
   			</table>
		</s:if>
		
		
		
		<s:if test="onePrivileges != null && onePrivileges.size() != 0">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
	          <tr>
	            <td height="29">
					<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img8" id="img8" src="Images/ico04.gif" width="8" height="11" /></td>
							<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('5');" >系统管理</a></td>
						</tr>
					</table>
				</td>
	          </tr>		  
	        </TABLE>
	        <table id="subtree5" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" 
					cellspacing="0" class="left-table02">
				<s:iterator value="onePrivileges" var="privilege">
					<tr>
					  <td width="9%" height="20" ><img id="xiaotu20" src="Images/ico06.gif" width="8" height="12" /></td>
					  <td width="91%"><a href="<s:property value="#privilege.url" />" target="mainFrame" class="left-font03" onClick="tupian('20');"><s:property value="#privilege.model" /></a></td>
					</tr>
				</s:iterator>
   			</table>
		</s:if>

		

		
		
		

	  </TD>
  </tr>
  
</table>
</body>
</html>
