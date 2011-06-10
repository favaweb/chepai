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
	</head>

	<body>
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;小批号合并详情</div>
		
		<s:if test="batchNumberMerges == null || batchNumberMerges.size() == 0">
			<span style="color:red;">没有任何记录</span>
		</s:if>
		<s:else>
		<div>
			<table  cellpadding="1" cellspacing="1" >
				<tr class="th">
					<td colspan="5">
						<span style="color:red;">
							小批号:<B><s:property value="batchNumberMerges[0].smallnessBatchNumber.smallnessBatchNumber"/></B><br/>
						</span>
					</td>
				</tr>
				<tr class="th">
					<td>
						大批号
					</td>
					<td>
						地区
					</td>
					<td>
						类型
					</td>
					<td>
						优先级
					</td>
					<td>
						时间
					</td>
				</tr>
			
			<s:iterator value="batchNumberMerges" var="batchNumberMerge">
				<tr>
					<td><s:property value="#batchNumberMerge.bigBatchNumber.bigBattchNumber"/></td>
					<td><s:property value="#batchNumberMerge.bigBatchNumber.place"/></td>
					<td>
						<s:if test="#batchNumberMerge.bigBatchNumber.orderType == 1">补制</s:if>
						<s:elseif test="#batchNumberMerge.bigBatchNumber.orderType == 2">自选</s:elseif>
						<s:else>号段</s:else>
					</td>
					<td>
						<s:if test="#batchNumberMerge.bigBatchNumber.precedence == 1">
							<span style="color: red;">高</span>
						</s:if>
						<s:elseif test="#batchNumberMerge.bigBatchNumber.precedence == 2">
							<span style="color: red;">中</span>
						</s:elseif>
						<s:else>
							<span style="color: red;">低</span>
						</s:else>
					</td>
					<td>
						<s:date name="#batchNumberMerge.bigBatchNumber.createTime" format="yyyy-MM-dd hh:mm:ss" />
					</td>
				</tr>
				
			</s:iterator>
		</table>
		</div>
	</s:else>
	<input type="button" class="button" value="返回" onclick="javascript:window.history.go(-1);"/><br/>	

	</body>
</html>
