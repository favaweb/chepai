<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js">	</script>
		<script type="text/javascript"> 
		function  CheckForm2()
		{  
			if(document.inaa.reductionAmount.value=="" || document.inaa.reductionAmount.value<=0)
			{
				alert("请填写数量");
				return  false;
			}
			if(document.inaa.reductionAmount.value>=100000000){
				alert("您输入的数量太大了");
				return false;
			}
			if(document.inaa.createDate.value==""){
				alert("请选择入库时间");
				return  false;
			}
			if(document.inaa.reductionNumber.value==""){
				alert("请输入入库编号");
				return false;
			}
			if(submitForm()){
 				return  true;
 			}
 			return false;
		}
		
		function submitForm(){
			var count = "\n数量：" + document.inaa.reductionAmount.value;
			var time = "\n时间：" + document.inaa.createDate.value;
			var num = "\n编号：" + document.inaa.reductionNumber.value;
		    return confirm("确认提交入库信息吗？"+count+time+num);
		}
		
		
</script>
	</head>

	<body>
		<div style="font-size: 14px;">
			当前位置&nbsp;&gt;&nbsp;仓库物资管理&nbsp;&gt; 半成品管理&nbsp;&gt; 入库
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
		<div align="center" style="margin-top: 15px;">
			<form name="inaa"
				action="SemifinishedProduct!add.action?inputRegister.type=1"
				method="post" onsubmit="return CheckForm2();">
				<s:token></s:token>

				<table cellpadding="1" cellspacing="1" style="width: 40%;">
					<tr class="th">
						<td colspan="2">
							入库
						</td>
					</tr>
					<tr>
						<td>
							类型：
						</td>
						<td>
							<select id="typeid"
								name="inputRegister.semifinishedProductType.id"
								style="width: 155px">
								<s:iterator value="semifinishedProductTypes"
									var="semifinishedProductType">
									<option
										value='<s:property value="#semifinishedProductType.id"/>'>
										<s:property value="#semifinishedProductType.typeName" />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							数量(块)：
						</td>
						<td>
							<input type="input" id="reductionAmount"
								name="inputRegister.reductionAmount"
								onkeypress="var k=event.keyCode; if ((k==46)||(k<=57 && k>=48)) return true; else  return false" />
						</td>
					</tr>
					<tr>
						<td>
							入库时间：
						</td>
						<td>
							<input type="text" id="createDate" 
								name="inputRegister.createDate" onClick="WdatePicker()" class="Wdate"  />
						</td>
					</tr>
					<tr>
						<td>
							入库编号：
						</td>
						<td>
							<input id="reductionNumber" name="inputRegister.reductionNumber" />
						</td>
					</tr>
					<tr>
						<td>
							备注：
						</td>
						<td>
							<textarea id="remark" name="inputRegister.remark"
								style="width: 155px"></textarea>
						</td>
					</tr>
				</table>
				<input type="submit" value="添加入库记录" class="button" align="center" />&nbsp;
			</form>
		</div>

	</body>
</html>
