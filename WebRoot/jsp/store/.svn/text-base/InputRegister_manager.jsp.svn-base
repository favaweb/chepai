<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
		<script type="text/javascript">
		function  CheckForm()
		{  
			if(document.getElementById('search').startDate.value == "" && document.getElementById('search').endDate.value!="" ){
				alert("请填写开始日期");
				return false;
			}
 			return  true;;
		}
		
		function page(id){
			document.getElementById("search").action="SemifinishedProduct!findInputListbyCondition.action?page.currentPage="+id;
			if(CheckForm()){
				document.getElementById("search").submit();
			}
		}
</script>
	</head>

	<body>
	  	<div style="font-size: 14px;">当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt;&nbsp;半成品管理&nbsp;&gt;&nbsp;出入库记录明细</div><br/>
	
		<span style="font-size: 14px; padding: 10px; text-align: center;"><h4
				style="text-align: center;">
				<a href="SemifinishedProduct!findInputList.action">出入库记录</a>&nbsp;

				<a
					href="SemifinishedProduct!operateInput.action?inputRegister.type=1">入库</a>&nbsp;

				<a
					href="SemifinishedProduct!operateInput.action?inputRegister.type=2">出库</a>&nbsp;

				<a href="SemifinishedProduct!findTypeList.action">半成品类型</a>&nbsp;
				
				<a href="SemifinishedProduct!reserveList.action">剩余库存</a>

			</h4>
		</span>
		<hr/>
		<form method="post" action="SemifinishedProduct!findInputListbyCondition.action" onsubmit="return CheckForm();" id="search" >
		<div align="center" style="margin-top:20px;margin-bottom:20px;">
			类型：
			<select id="typeid" name="inputRegister.semifinishedProductType.id">
				<option value="0">
					未选择
				</option>
				<s:iterator value="semifinishedProductTypes"
					var="semifinishedProductType">
					<option value='<s:property value="#semifinishedProductType.id"/>'
						<s:if test="inputRegister.semifinishedProductType.id == #semifinishedProductType.id">selected="selected"</s:if>>
						<s:property value="#semifinishedProductType.typeName" />
					</option>
				</s:iterator>
			</select>


			编号：
			<input name="inputRegister.reductionNumber" value="<s:property value='inputRegister.reductionNumber' />" style="width:120px;" />

						出/入库：
			<select name="inputRegister.type">
				<option value="0" <s:if test="inputRegister.type==0">selected="selected"</s:if>  >
					未选择
				</option>
				<option value="1" <s:if test="inputRegister.type==1">selected="selected"</s:if> >
					入库
				</option>
				<option value="2" <s:if test="inputRegister.type==2">selected="selected"</s:if> >
					出库
				</option>
			</select>

			开始时间：
			<input id="startDate" type="text" name="startDate"
				onClick="WdatePicker()" class="Wdate"
				value="<s:date name="startDate" format="yyyy-MM-dd" />" style="width:120px;" />
			结束时间：
			<input id="endDate" type="text" name="endDate"
				onClick="WdatePicker()" class="Wdate"
				value="<s:date name="endDate" format="yyyy-MM-dd" />" style="width:120px;" />

			<input type="submit" value="查询" class="button" />
		</div>
		
		<div align="center" style="margin-top:15px;" >
		<table  cellpadding="1" cellspacing="1"  style="width:90%" >
		<s:if test="inputRegisters!=null">
			<tr class="th">
				<td colspan="8">出入库记录明细</td>
			</tr>
			
				
					<s:if test="startDate!=null">
					<tr class="th" style="text-align:right;"><td colspan="8" >
					<s:date name="startDate" format="yyyy-MM-dd" />至
					<s:if test="endDate==null">今天</s:if>
					<s:else><s:date name="endDate" format="yyyy-MM-dd" /></s:else>
					</td></tr>
					</s:if>
				
			
			<tr class="th">
				<td>
					序号
				</td>
				<td>
					出/入库
				</td>
				<td>
					编号
				</td>

				<td>
					日期
				</td>
				<td>
					类型
				</td>
				<td>
					规格
				</td>
				<td>
					数量（块）
				</td>
				<td>
					详情
				</td>
			</tr>
			<s:if test="inputRegisters.size==0">
				<tr>
					<td colspan="8" align="center">
						没有记录
					</td>
				</tr>
			</s:if>
			<s:iterator value="inputRegisters" var="inputRegister" status="status">
				<tr>
					<td>
						<s:property value="#status.index+1"/>
					</td>
					<td>
						<s:if test="#inputRegister.type==1">入库</s:if>
						<s:if test="#inputRegister.type==2">出库</s:if>
					</td>
					<td>
						<s:property value="#inputRegister.reductionNumber" />
					</td>
					<td>
						<s:date name="#inputRegister.createDate" format="yyyy-MM-dd" />
					</td>
					<td>
						<s:property
							value="#inputRegister.semifinishedProductType.typeName" />
					</td>
					<td>
						<s:property value="#inputRegister.standard" />
					</td>
					<td>
						<s:property value="#inputRegister.reductionAmount" />
					</td>
					<td>
						<a href="SemifinishedProduct!detailInput.action?inputRegister.id=<s:property value='#inputRegister.id'/>">点击查看</a>
					</td>

				</tr>
			</s:iterator>
		</table>
		</div>
		<s:if test="page.lastPage!=0">
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
					<input id="pagenum" type="text" style="width: 30px;" />
					<input type="button" value="转" onclick="page(pagenum.value)" class="button" style="width: 35px;text-align: center;" />
				</td>
			</tr>
			</s:if>
		</table>
		</s:if>
		</form>
	</body>
</html>
