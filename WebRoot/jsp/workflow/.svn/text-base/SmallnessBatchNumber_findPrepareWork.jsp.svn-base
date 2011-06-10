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

		<title>反压</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
	<script type="text/javascript">
		
		function checkOperator(t,c) {
			var tasks =  document.getElementsByName(t);
			var cTasks =  document.getElementsByName(c);
			
			
			var isAllCheck = false;
			for(var i=0; i<tasks.length; i++) {
				if(tasks[i].checked == true) {
					isAllCheck = true;
				}
			}
			for(var i=0; i<cTasks.length; i++) {
				if(cTasks[i].checked == true) {
					isAllCheck = true;
				}
			}
			if(isAllCheck) {
				if(window.confirm("您确定更换全部任务?")) {
					document.getElementById("myForm").submit();
				}
			}
		
		}
		
  </script>
	</head>

	<body>
	
		<div style="font-size: 14px;">当前位置&nbsp;>&nbsp;任务调配</div>
	
		<form action="TaskAllocation!changeTask.action" id="myForm" method="post">
			<s:token></s:token>
			
			<div>
		<table  cellpadding="1" cellspacing="1" >
			<tr>
				<td colspan="8">
					<s:if test="taskAllocations != null && taskAllocations.size() !=0">
						<s:property value="taskAllocations[0].names"/>
						<input type="hidden" name="taskNames" value="<s:property value='taskAllocations[0].operator'/>" />
					</s:if>
				</td>
			</tr>
			<tr class="th">
				<td>
					请选择
				</td>
				<td>
					大批号
				</td>
				<td>
					小批号
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
					箱号
				</td>
				<td>
					时间
				</td>
			</tr>
			<s:if test="taskAllocations == null || taskAllocations.size() ==0">
				<tr>
					<td colspan="8" align="center"><span style="color:red;">没有任何记录</span></td>
				</tr>
			</s:if>

			<s:iterator value="taskAllocations" var="t">
				<tr>
					<td>
						<input style="width: 25px;height:25px;" type="checkbox" name="tasks" value="<s:property value='#t.id'/>" />
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.bigBatchNumber == null">
							<s:if test="#t.smallnessBatchNumber.isRemakes == 1">
								<span style="color:red;">补牌</span>
							</s:if>
							<s:elseif test="#t.smallnessBatchNumber.isRemakes == 2">
								<a href="BatchNumberMerge!findMergerBySmallId.action?smallBatchId=<s:property value='#t.smallnessBatchNumber.id'/>">
									合并批号
								</a>
							</s:elseif>
						</s:if>
						<s:else>
							<s:property value="#t.smallnessBatchNumber.bigBatchNumber.bigBattchNumber" />
						</s:else>
					</td>
					<td>
				
							<a href="NumberPlate!findBySmallnessBatchId.action?smallnessBatchNumberId=<s:property value='#t.smallnessBatchNumber.id'/>&isRemakes=<s:property value='#t.smallnessBatchNumber.isRemakes'/>&flowTypeId=<s:property value='flowTypeId'/>&isDistribute=<s:property value='#t.smallnessBatchNumber.isDistribute'/>" target="mainFrame">
								<s:property value="#t.smallnessBatchNumber.smallnessBatchNumber" />
							</a>
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.place == null">
							<span style="color:red;">混合地区</span>
						</s:if>
						<s:else>
							<s:property value="#t.smallnessBatchNumber.place" />
						</s:else>
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.orderType == 1">补制</s:if>
						<s:elseif test="#t.smallnessBatchNumber.orderType == 2">自选</s:elseif>
						<s:else>号段</s:else>
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.precedence == 1">
							<span style="color: red;">高</span>
						</s:if>
						<s:elseif test="#t.smallnessBatchNumber.precedence == 2">
							<span style="color: red;">中</span>
						</s:elseif>
						<s:else>
							<span style="color: red;">低</span>
						</s:else>
					</td>
					<td>
						<h2 style="color: red"> <s:property value="#t.smallnessBatchNumber.boxNumber" /></h2>
					</td>
					<td>
						<s:date name="#t.smallnessBatchNumber.dateTime"
							format="yyyy-MM-dd hh:mm:ss" />
					</td>
				</tr>
			
			</s:iterator>

		</table>
		</div>
		
		<div>
		<table  cellpadding="1" cellspacing="1" >
			<tr>
				<td colspan="8">
					<s:if test="changeTasks != null && changeTasks.size() !=0">
						<s:property value="changeTasks[0].names"/>
						<input type="hidden" name="cTaskNames" value="<s:property value='changeTasks[0].operator'/>" />
					</s:if>
				</td>
			</tr>
			<tr class="th">
				<td>
					请选择
				</td>
				<td>
					大批号
				</td>
				<td>
					小批号
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
					箱号
				</td>
				<td>
					时间
				</td>
			</tr>
			<s:if test="changeTasks == null || changeTasks.size() ==0">
				<tr>
					<td colspan="8" align="center"><span style="color:red;">没有任何记录</span></td>
				</tr>
			</s:if>

			<s:iterator value="changeTasks" var="t">
				<tr>
					<td>
						<input style="width: 25px;height:25px;" type="checkbox" name="cTasks" value="<s:property value='#t.id'/>" />
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.bigBatchNumber == null">
							<s:if test="#t.smallnessBatchNumber.isRemakes == 1">
								<span style="color:red;">补牌</span>
							</s:if>
							<s:elseif test="#t.smallnessBatchNumber.isRemakes == 2">
								<a href="BatchNumberMerge!findMergerBySmallId.action?smallBatchId=<s:property value='#t.smallnessBatchNumber.id'/>">
									合并批号
								</a>
							</s:elseif>
						</s:if>
						<s:else>
							<s:property value="#t.smallnessBatchNumber.bigBatchNumber.bigBattchNumber" />
						</s:else>
					</td>
					<td>
				
							<a href="NumberPlate!findBySmallnessBatchId.action?smallnessBatchNumberId=<s:property value='#t.smallnessBatchNumber.id'/>&isRemakes=<s:property value='#t.smallnessBatchNumber.isRemakes'/>&flowTypeId=<s:property value='flowTypeId'/>&isDistribute=<s:property value='#t.smallnessBatchNumber.isDistribute'/>" target="mainFrame">
								<s:property value="#t.smallnessBatchNumber.smallnessBatchNumber" />
							</a>
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.place == null">
							<span style="color:red;">混合地区</span>
						</s:if>
						<s:else>
							<s:property value="#t.smallnessBatchNumber.place" />
						</s:else>
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.orderType == 1">补制</s:if>
						<s:elseif test="#t.smallnessBatchNumber.orderType == 2">自选</s:elseif>
						<s:else>号段</s:else>
					</td>
					<td>
						<s:if test="#t.smallnessBatchNumber.precedence == 1">
							<span style="color: red;">高</span>
						</s:if>
						<s:elseif test="#t.smallnessBatchNumber.precedence == 2">
							<span style="color: red;">中</span>
						</s:elseif>
						<s:else>
							<span style="color: red;">低</span>
						</s:else>
					</td>
					<td>
						<h2 style="color: red"> <s:property value="#t.smallnessBatchNumber.boxNumber" /></h2>
					</td>
					<td>
						<s:date name="#t.smallnessBatchNumber.dateTime"
							format="yyyy-MM-dd hh:mm:ss" />
					</td>
				</tr>
			
			</s:iterator>

		</table>
		</div>
			
		<input type="button" class="button" value="交换任务" onclick="checkOperator('tasks','cTasks')" />
		</form>
	</body>
</html>
