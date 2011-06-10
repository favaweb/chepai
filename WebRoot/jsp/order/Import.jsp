<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Import.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
  	<script>
		function save(){
			var url=document.getElementById("file").value;
			if(url!=""&&url!=null){
				document.getElementById("wait").style.display="block";
				document.getElementById("form1").action="NumberPlate!save.action?fileUrl="+encodeURI(url);
				document.getElementById("form1").submit();
			}else
			{
				alert("没有数据");
			}
		}
		
		function subPiliang() {
			var orderTypes = document.getElementsByName("orderTypes");
			var isSelect = false;
			for(var i=0; i<orderTypes.length; i++) {
				if(orderTypes[i].checked == true) {
					isSelect = true;
				}
			}
			
			if(isSelect) {
				document.getElementById("wait").style.display="block";
				document.getElementById('piliang').submit();
			} else {
				alert("请选择要批量上传的文件类型!");
			}
			
			
		}
	</script>
  </head>
  
  <body>
  <div style="position: absolute;width: 100%;height: 100%;background:white;display: none;" id="wait" >
  	<div style="width:100%;height:100%;text-align: center;background: #dddddd;">
  		<img src="Images/loading.gif" />导入中...
  	</div>
  </div>
  <div style="font-size: 14px;">当前位置&nbsp;>&nbsp;生产单管理&nbsp;>&nbsp;<a href="NumberPlate!importInput.action">导入数据</a></div>
   <form id="form1" action="#" method="post" enctype ="multipart/form-data" >
   		<s:token></s:token>
   		<table  cellpadding="1" cellspacing="1"  align="center" style="width: 800px;">
   			<tr class="th">
   				<td>单份文件导入</td>
   			</tr>
   			<tr>
   				<td>
   					请选择要导入的数据：
   					<input type="file" class="file" name="file" id="file" /></td>
   			</tr>
   			<tr style="text-align: left;">
   				<td><input type="button" onClick="save()" value="提交" class="button"><span style="color:red;">(*数据格式必须是excel)</span></td>
   			</tr>
   		</table>
  	</form>
  	<br />
  	<br />
  	<br />
  	<br />
  	
   <form id="piliang" action="NumberPlate!importFiles.action" method="post">
   		<s:token></s:token>
   		<table  cellpadding="1" cellspacing="1"  align="center" style="width: 800px;">
   			<tr class="th">
   				<td colspan="2">批量数据导入</td>
   			</tr>
   			<tr>
   				<td>数据存储IP地址</td>
   				<td><input type="text" name="remoteIp" value="<%=request.getRemoteAddr() %>" /></td>
   			</tr>
   			<tr>
   				<td>导入类型</td>
   				<td>
					<input  style="width: 25px;height:25px;" style="width: 25px;height:25px;" type="checkbox" value="自选" name="orderTypes" />自选&nbsp;&nbsp;
					<input style="width: 25px;height:25px;" type="checkbox" value="补制" name="orderTypes" />补制&nbsp;&nbsp;
					<input style="width: 25px;height:25px;" type="checkbox" value="号段" name="orderTypes" />号段
				</td>
   			</tr>
   			<tr style="text-align: left;">
   				<td colspan="2"><input type="button" class="button" onclick="subPiliang()"  value="批量导入" /><span style="color:red;font-size:12px;">注：数据不能重复且不能含有非excel格式文件</span></td>
   			</tr>
   		</table>
  	</form>
  	
  	
  </body>
</html>
