<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		*{
			margin:0;
			padding:0;
		}
		body{
			font-size: 14px;
		}
		table{
			text-align: center;
			width:100%;
		}
		tr {
		}
	</style>
	<SCRIPT type="text/javascript"> 
function printsetup(){  
 wb.execwb(8,1); // 打印页面设置
} 
function printpreview(){  
 wb.execwb(7,1);// 打印页面预览
} 
function printit() { 
  //wb.execwb(6,1);
  window.print();
  window.close();
} 
　　</SCRIPT>
<!--media=print 这个属性说明可以在打印时有效-->
<!--希望打印时不显示的内容设置class="Noprint"样式-->
<!--希望人为设置分页的位置设置class="PageNext"样式-->
<style media="print">
<!--
.Noprint{display:none;}
.PageNext{page-break-after:always;}
-->
</style>
  </head>
  <body onload="javascript:printit();">
  
 	 <table  cellpadding="0" cellspacing="0">
 	 	<tr  valign="top">
 	 		<td width="78%" style="height: 100%;">
 	 		
 	 			<table  cellpadding="0" cellspacing="0" border="1" width="65%" >
 	 			
 	 			<tr>
 	 				<td colspan="4" style="font-size: 17px;font-weight: bold;">
 	 					省厅制证中心(<s:if test="smallnessBatchNumber.orderType == 1">补制</s:if><s:elseif test="smallnessBatchNumber.orderType == 2">自选</s:elseif><s:else>号段</s:else>)号牌报制单
 	 					<s:if test="smallnessBatchNumber.bigBatchNumber==null">
 	 						<s:property value="smallnessBatchNumber.smallnessBatchNumber"/>
 	 					</s:if>
 	 					<s:property value="smallnessBatchNumber.bigBatchNumber.bigBattchNumber"/>
						(<s:date name="smallnessBatchNumber.dateTime" format="yyyy年MM月dd日" />)
 	 				</td>
 	 			</tr>
 	 			<tr style="font-size: 15px;font-weight: bold;background: #DDDDDD;" >
 	 				<td>序号</td>
 	 				<td>业务部门</td>
 	 				<td>车牌种类</td>
 	 				<td>车牌号码</td>
 	 			</tr>
 	 			
 	 			<s:iterator value="numberPlates" var="numberPlate">
		  			<tr>
		  				<td width="10%"><s:property value="#numberPlate.orderNumber"/></td>
		  				<td width="20%"><s:property value="#numberPlate.businessDepartment.department"/></td>
		  				<td width="30%">
		  					<s:property value="#numberPlate.numberPlateType.typeName"/>
		  					(
			  					<s:if test="smallnessBatchNumber.orderType == 1">补制</s:if>
								<s:elseif test="smallnessBatchNumber.orderType == 2">自选</s:elseif>
								<s:else>号段</s:else>
		  					)
		  				</td>
		  				<td>
		  					<s:property value="#numberPlate.licensePlateNumber"/>
		  				</td>
		  			</tr>
	 			</s:iterator>
 	 			</table>
 	 			
 	 			
 	 		</td>
 	 		
 	 		
 	 		
 	 		
 	 		<td width="20%" style="height: 100%;">
 	 			
 	 			
 	 			<table  cellpadding="0" cellspacing="0" border="1" width="30%" >
 	 				<tr>
 	 					<td colspan="2" style="font-size: 14px;font-weight: bold;">岗位签名</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;width: 40%;">反压</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">洗牌</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">滚油</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">正压</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">质检</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">打标</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">总质检</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">包装</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 				<tr>
 	 					<td style="line-height: 705%;">确认</td>
 	 					<td style="line-height: 705%;">&nbsp;</td>
 	 				</tr>
 	 			</table>
 	 			
 	 			
 	 		</td>
 	 	</tr>
 	</table>

<div  style="page-break-before:always;"><br /></div>



		<table cellpadding="0" cellspacing="0" border="1" >
			<tr>
				<td colspan="4" style="font-weight: bold;">
					<s:property value="smallnessBatchNumber.smallnessBatchNumber"/> 压制单(
					<s:property value="operator" />
					)
				</td>
			</tr>
			<s:iterator value="numberPlates" var="numberPlate">
				<tr style="font-size: 16px;">
					<td width="10%" >
						<s:property value="#numberPlate.orderNumber" />
					</td>
					<td width="20%">
						<s:property value="#numberPlate.businessDepartment.department" />
					</td>
					<td width="30%">
							<s:property value="#numberPlate.numberPlateType.typeName" />
						(
						<s:if test="smallnessBatchNumber.orderType == 1">补制</s:if>
						<s:elseif test="smallnessBatchNumber.orderType == 2">自选</s:elseif>
						<s:else>号段</s:else>
						)
					</td>
					<td style="font-size: 32px;">
						<s:if test="#numberPlate.numberPlateType.id == 1 || #numberPlate.numberPlateType.id==2 || #numberPlate.numberPlateType.id==3">
							<strong>
								<s:if test="#numberPlate.licensePlateNumber.indexOf('_')!=-1">
									<s:property value="#numberPlate.licensePlateNumber.replace('补制','')" />
								</s:if>
								<s:else>
									<s:property value="#numberPlate.licensePlateNumber" />
								</s:else>
							</strong>
						</s:if>
						<s:elseif test="#numberPlate.numberPlateType.typeName.indexOf('摩托车') != -1">
							<I>
								<s:if test="#numberPlate.licensePlateNumber.indexOf('_')!=-1">
									<s:property value="#numberPlate.licensePlateNumber.replace('补制','')" />
								</s:if>
								<s:else>
									<s:property value="#numberPlate.licensePlateNumber" />
								</s:else>
							</I>
						</s:elseif>
						<s:else>
								<s:if test="#numberPlate.licensePlateNumber.indexOf('_')!=-1">
									<s:property value="#numberPlate.licensePlateNumber.replace('补制','')" />
								</s:if>
								<s:else>
									<s:property value="#numberPlate.licensePlateNumber" />
								</s:else>
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</table>
  </body>
</html>
