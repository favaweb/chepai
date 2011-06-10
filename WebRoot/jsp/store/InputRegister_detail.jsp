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
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		  function  checkForm2(id)
		   {  
			if(document.getElementById(id).reductionAmount.value=="" || document.getElementById(id).reductionAmount.value<=0)
			{
				alert("请填写数量");
				return  false;
			}
			if(document.getElementById(id).reductionAmount.value>=100000000){
				alert("您输入的数量太大了");
				return false;
			}
			if(document.getElementById(id).createDate.value==""){
				alert("请选择时间");
				return  false;
			}
			if(document.getElementById(id).reductionNumber.value==""){
				alert("请输入编号");
				return false;
			}
 			return  true;
		}
		</script>

	</head>

	<body>
		<div style="font-size: 14px;">
			当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt; 半成品管理&nbsp;&gt; 记录详情
			<br>
		</div>
		<br />
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
		<hr />
		<div align="center" style="margin-top: 15px; div tr: hover {">
			<s:if test="inputRegister.type==2">
				<form id="outInput" onsubmit="return checkForm2(this.id)"
					action="SemifinishedProduct!update.action?inputRegister.type=2&inputRegister.id=<s:property value='inputRegister.id' />&inputRegister.semifinishedProductType.id=<s:property value='inputRegister.semifinishedProductType.id' />"
					method="post" >
					<s:token></s:token>
					<table   cellpadding="1" cellspacing="1"  style="width: 40%;">
						<tr class="th">
							<td colspan="2"> 
								出库记录详情 
							</td>
						</tr>
						<tr>
							<td>
								类型：
							</td>
							<td>
								<s:property value="inputRegister.semifinishedProductType.typeName" /><br></td>
						</tr>
						<tr>
							<td>
								出库时间：
							</td>
							<td>
								<input id="createDate" type="text"
									name="inputRegister.createDate" id="createDate"
									onClick="WdatePicker()" class="Wdate"
									value="<s:date name="inputRegister.createDate" format="yyyy-MM-dd" />" />
							</td>
						</tr>
						<tr>
							<td>
								数量（副）：
							</td>
							<td>
								<input type="input" name="inputRegister.reductionAmount"
									id="reductionAmount"
									value="<s:property value='inputRegister.reductionAmount'/>" />
							</td>
						<tr>
							<td>
								出库编号：
							</td>
							<td>
								<input type="input" name="inputRegister.reductionNumber"
									id="reductionNumber"
									value="<s:property value='inputRegister.reductionNumber'/>" />
							</td>
						</tr>
						<tr>
							<td>
								领料用途：
							</td>
							<td>
								<select name="inputRegister.purpose" id="purpose"
									style="width: 155px">
									<option value="1"
										<s:if test="inputRegister.purpose==1">selected='selected'</s:if>>
										正常生产
									</option>
									<option value="2"
										<s:if test="inputRegister.purpose==2">selected='selected'</s:if>>
										优先制作及重新补牌
									</option>
									<option value="3"
										<s:if test="inputRegister.purpose==3">selected='selected'</s:if>>
										补牌
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								备注：
							</td>
							<td>
								<textarea id="remark" name="inputRegister.remark"
									style="width: 155px" ><s:property value='inputRegister.remark'/></textarea>
							</td>
						</tr>
					</table>
					<input type="submit" value="修改出库记录" class="button" />
					<input type="reset" value="恢复" class="button" />
				</form>
			</s:if>
			<s:if test="inputRegister.type==1">
				<form id="inInput" onsubmit="return checkForm2(this.id)"
					action="SemifinishedProduct!update.action?inputRegister.type=1&inputRegister.id=<s:property value='inputRegister.id' />&inputRegister.semifinishedProductType.id=<s:property value='inputRegister.semifinishedProductType.id' />"
					method="post">
					<s:token></s:token>
					<table style="width: 40%;">
						<tr class="th">
							<td colspan="2">入库记录详情 
							</td>
						</tr>
						<tr>
							<td>
								类型：
							</td>
							<td>
								<s:property value="inputRegister.semifinishedProductType.typeName" /><br></td>
						</tr>
						<tr>
							<td>
								入库编号：
							</td>
							<td>
								<input type="input" name="inputRegister.reductionNumber"
									id="reductionNumber"
									value="<s:property value='inputRegister.reductionNumber'/>" />
							</td>
						</tr>
						<tr>
							<td>
								数量（副）：
							</td>
							<td>
								<input type="input" name="inputRegister.reductionAmount"
									id="reductionAmount"
									value="<s:property value='inputRegister.reductionAmount' />" />
							</td>
						</tr>
						<tr>
							<td>
								入库时间：
							</td>
							<td>
								<input type="text" name="inputRegister.createDate"
									id="createDate" onClick="WdatePicker()" class="Wdate"
									value="<s:date name="inputRegister.createDate" format="yyyy-MM-dd" />" />
							</td>
						</tr>
						<tr>
							<td>
								备注：
							</td>
							<td>
								<textarea id="remark" name="inputRegister.remark"
									style="width: 155px"><s:property value='inputRegister.remark'/></textarea>
							</td>
						</tr>
					</table>
					<input type="submit" value="修改入库记录" class="button" />
					<input type="reset" value="恢复" class="button" />
				</form>
			</s:if>
		</div>
	</body>
</html>
