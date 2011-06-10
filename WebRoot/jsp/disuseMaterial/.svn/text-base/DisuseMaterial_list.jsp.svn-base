<%@ page language="java" import="java.util.*,com.hovto.chepai.model.*,com.hovto.chepai.privilege.*,com.hovto.chepai.tool.*;" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'IndividualStat_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function find(){
			var userid=document.getElementById("userId").value;
				//document.getElementById("myForm").action="DisuseMaterial!findByFlowType.action";
				document.getElementById("myForm").submit();
			
		}
		function  CheckForm()
		{  
			if(document.getElementById('myForm').biginTime.value == "" && document.getElementById('myForm').biginTime.value!="" ){
				alert("请填写开始日期");
				return false;
			}
 			return  true;;
		}
		function page(id){
			document.getElementById("myForm").action="DisuseMaterial!findByFlowType.action?page.currentPage="+id;
			if(CheckForm()){
				document.getElementById("myForm").submit();
			}
		}
	</script>
  </head>
  
  <body>
  	<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;查询统计&nbsp;>&nbsp;个人废牌</div><br/>
    <form action="DisuseMaterial!findByFlowType.action" id="myForm" name="myForm" method="post">
    	流程状态:<select name="flowTypeId">
						<option value="0">--请选择--</option>
						<s:iterator value="flowTypes" var="flowTypes">
							<s:if test="#flowTypes.id <= 7">
								<option <s:if test="flowTypeId == #flowTypes.id">selected="selected"</s:if> value='<s:property value="#flowTypes.id"/>'><s:property value="#flowTypes.typeName"/></option>
							</s:if>
						</s:iterator>
					</select>
    	操作人：<select name="userId" id="userId">
						<option value="0">--请选择--</option>
						<s:iterator value="users" var="users">
							<option <s:if test="userId == #users.id">selected="selected"</s:if> value='<s:property value="#users.id"/>'><s:property value="#users.name"/></option>
						</s:iterator>
					</select>
    	时间段：<input type="text" name="biginTime" id="biginTime" value="<s:property value="biginTime" />"  onClick="WdatePicker()" class="Wdate" />-
    	<input type="text" name="endTime" value="<s:property value='endTime' />" onClick="WdatePicker()" class="Wdate" />
    	<input type="button" value="查询" class="button" onclick="find()"/>
    	<hr/>
    	<div>
	    	<table  cellpadding="1" cellspacing="1" >
	    		<tr class="th">
	    			<td>序号</td>
	    			<th>车牌号</th>
	    			<th>损坏原因</th>
	    			<th>类型</th>
	    			<th>操作人</th>
	    		</tr>
				<s:iterator value="disuseMaterialList" var="disuseMaterialList" status="status">
					<tr>
							<td>
								<s:property value="#status.index + 1"/>
							</td>
						<td><s:property value="#disuseMaterialList.numberPlate.licensePlateNumber"/></td>
						<td>
							<s:if test="#disuseMaterialList.marType==1">压错</s:if>
							<s:elseif test="#disuseMaterialList.marType==2">半成品</s:elseif>
							<s:elseif test="#disuseMaterialList.marType==3">机器模具</s:elseif>
							<s:elseif test="#disuseMaterialList.marType==4">试机</s:elseif>
							<s:elseif test="#disuseMaterialList.marType==5">样牌</s:elseif>
							<s:elseif test="#disuseMaterialList.marType==6">重复车牌</s:elseif>
							<s:elseif test="#disuseMaterialList.marType==7">漏制车牌</s:elseif>
							<s:elseif test="#disuseMaterialList.marType==8">质检责任</s:elseif>
						</td>
						<td>
							<s:property value="#disuseMaterialList.SemifinishedProductType.typeName"/></td>
						<td>
							<s:property value="#disuseMaterialList.operator"/>
						</td>
					</tr>
				</s:iterator>
	    	</table>
	    </div>
	    <s:if test="page.lastPage>1">
		<table cellpadding="0" cellspacing="0" bgcolor="#ffffff" style="font-size: 12px;">
			<tr>
				<td style="text-align: left;padding-left: 10px;">
					共&nbsp;<s:property value="page.lastPage"/>&nbsp;页/第&nbsp;<s:property value="page.currentPage" />&nbsp;页
				</td>
				<td style="text-align: right;">
				<s:if test="page.currentPage==1">第一页</s:if>
				<s:else><a href="javascript:void(0);" onclick="page(1);">第一页</a></s:else>
				<s:if test="page.currentPage==1">上一页</s:if>
				<s:else><a href="javascript:void(0);" onclick="page(<s:property value='page.currentPage - 1' />);" >上一页</a></s:else>
				<s:if test="page.currentPage==page.lastPage">下一页</s:if>
				<s:else><a href="javascript:void(0);" onclick="page(<s:property value='page.currentPage + 1'/>);">下一页</a></s:else>
				<s:if test="page.currentPage==page.lastPage">最后一页</s:if>	
				<s:else><a href="javascript:void(0);" onclick="page(<s:property value='page.lastPage'/>);">最后一页</a></s:else>
					<input id="pagenum" type="text" style="width: 30px;"  />
					<input type="button" value="转" onclick="page(pagenum.value)" class="button" style="width: 35px;text-align: center;" />
				</td>
			</tr>

		</table>
		</s:if>
    </form>
  </body>
</html>
