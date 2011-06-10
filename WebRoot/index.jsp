<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function show(o){
			 //重载验证码,为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳   
			 var timenow = new Date().getTime();
			 o.src="servlet/image?d="+timenow;
			 /*
			 //超时执行;
			 setTimeout(function(){
			  o.src="random.jsp?d="+timenow;
			 }
			  ,20);
			  */
			}
	</script>
  </head>
  
  <body  style="WIDTH: 100%; margin:0; height:100%;" onload="GetHeight()">

		<div class="login" align="center">
			<form action="login/Login!login.action" method="post" target="_parent">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" style="top:0; left:0; " >
    				<tr><td id="tdTop">&nbsp;</td></tr>
   					<tr>
	   					<td align="center" valign="middle">
						    <div style="width:999px; height:577px; background-image:url(Images/LoginBack.gif)">
						        <table cellpadding=0 cellspacing=0 border=0 width="280px" style=" margin-top:250px; margin-left:120px">
						        <tr>
							        <td width="100px" style="height:30px; font-family:楷体_GB2312; font-size:12pt; font-weight:bold; padding-right:5px; color:#cde9ff;" align="right">账 户：</td>
							        <td colspan="2">
							        	<input type="text"  name="user.account" style="width: 150px;" borderWidth="0">
							        </td>
						        </tr>
						        <tr>
						        	<td width="100px" style="height:30px; font-family:楷体_GB2312; font-size:12pt; font-weight:bold; padding-right:5px; color:#cde9ff;" align="right">密 码：</td>
						        	<td colspan="2">
						        		<input type="password"  name="user.password" style="width: 150px;" borderWidth="0" />
						        	</td>
						        </tr>
						        <tr>
						        <td height="60px"></td><td style="width: 95px" ><input type="submit" value="登陆" Width="82px" Height="33px" style="width:82px; height:33px;background-image:url(Images/LoginButtonBack.gif);background-color: transparent;border-style: none;color:#1b88db;" onclick="return IsEmpty()" /></td>
						        <td style="width: 95px" ><input type=button value="取 消" onclick="window.close();" style="width:82px; height:33px;background-image:url(Images/LoginButtonBack.gif);background-color: transparent;border-style: none;color:#1b88db;" /></td>
						        </tr>
						        </table>
						    </div>
	    				</td>
    				</tr>
    </table>
			</form>
		</div>
		<script type="text/javascript">
    function IsEmpty()
    { 
        var name=document.getElementById("user.name").value;
        var pass=document.getElementById("user.password").value;
        if(name=="")
        {
            alert('请输入用户名！');
            return false;
        }
        else if(pass=="")
        {
            alert("请输入密码！");
            return false;
        }
        else
        {
            return true;
        }
    }
    function GetHeight()
    {
        var h=window.screen.height;
        var tdh=h*0.1;
        if(h>800)
        {
            document.getElementById("tdTop").style.height=tdh;
        }
        else
        {
            document.getElementById("tdTop").style.height=5;
        }
    }
    </script>
	</body>
</html>



