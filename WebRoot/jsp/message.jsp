<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<base href="<%=basePath%>">
		<TITLE>提示信息</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<META content="MSHTML 6.00.2900.6003" name=GENERATOR>
		<META content="" name=description>
		<META content="" name=keywords>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

A {
	COLOR: #61BBCB;
	TEXT-DECORATION: none
}

A:hover {
	TEXT-DECORATION: underline
}

OL {
	MARGIN-LEFT: 24px;
	COLOR: #888;
	LIST-STYLE-TYPE: decimal
}

.mt10 {
	MARGIN-TOP: 10px
}

.lh2 {
	LINE-HEIGHT: 2;
	font-size: 16px;
	color: red;
}

.tip-page {
	MARGIN: 100px auto 30px;
	WIDTH: 600px
}

.tip-table {
	BACKGROUND: #ffffff;
	MARGIN: 0px 1px;
	WIDTH: 598px
}

.tip-top {
	BACKGROUND: url(Images/tip-top.png) no-repeat center 50%;
	HEIGHT: 50px
}

.tip-bgA {
	BACKGROUND: #61BBCB
}

.tip-bgB {
	BACKGROUND: #61BBCB
}

.tip-bgC {
	BACKGROUND: #61BBCB
}

.tip-bgC {
	BACKGROUND: #61BBCB
}

.tip-bgA {
	OVERFLOW: hidden;
	HEIGHT: 1px
}

.tip-bgB {
	OVERFLOW: hidden;
	HEIGHT: 1px
}

.tip-bgA {
	MARGIN: 0px 2px
}

.tip-bgB {
	MARGIN: 0px 1px
}

.tip-bgC {
	PADDING-TOP: 1px
}

.tip-content {
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 67px;
	PADDING-BOTTOM: 0px;
	PADDING-TOP: 0px
}

.tip-content TR TD {
	PADDING-RIGHT: 10px;
	PADDING-LEFT: 0px;
	PADDING-BOTTOM: 5px;
	LINE-HEIGHT: 25px;
	PADDING-TOP: 5px
}

.show-back {
	PADDING-RIGHT: 25px;
	DISPLAY: block;
	BACKGROUND: url(Images/tips-back.gif) no-repeat right 0px;
	OVERFLOW: hidden;
	LINE-HEIGHT: 22px;
	HEIGHT: 20px;
	TEXT-DECORATION: underline
}

.show-back:hover {
	BACKGROUND-POSITION: right -20px
}

.btn {
	BORDER-LEFT-COLOR: #61BBCB;
	BACKGROUND: #61BBCB;
	BORDER-BOTTOM-COLOR: #61BBCB;
	COLOR: #fff;
	BORDER-TOP-COLOR: #61BBCB;
	MARGIN-RIGHT: 1em;
	BORDER-RIGHT-COLOR: #61BBCB
}

.btn {
	BORDER-TOP-WIDTH: 1px;
	PADDING-RIGHT: 1em;
	PADDING-LEFT: 1em;
	BORDER-LEFT-WIDTH: 1px;
	FONT-SIZE: 9pt;
	BORDER-BOTTOM-WIDTH: 1px;
	PADDING-BOTTOM: 0px;
	OVERFLOW: visible;
	CURSOR: pointer;
	LINE-HEIGHT: 130%;
	PADDING-TOP: 0px;
	BORDER-RIGHT-WIDTH: 1px
}

.bt {
	BORDER-TOP-WIDTH: 1px;
	PADDING-RIGHT: 1em;
	PADDING-LEFT: 1em;
	BORDER-LEFT-WIDTH: 1px;
	FONT-SIZE: 9pt;
	BORDER-BOTTOM-WIDTH: 1px;
	PADDING-BOTTOM: 0px;
	OVERFLOW: visible;
	CURSOR: pointer;
	LINE-HEIGHT: 130%;
	PADDING-TOP: 0px;
	BORDER-RIGHT-WIDTH: 1px
}

.bt {
	BORDER-LEFT-COLOR: #e4e4e4;
	BACKGROUND: url(Images/wind/btn.png) #f7f7f7 repeat-x 0px -52px;
	BORDER-BOTTOM-COLOR: #cccccc;
	VERTICAL-ALIGN: middle;
	CURSOR: pointer;
	BORDER-TOP-COLOR: #e4e4e4;
	BORDER-RIGHT-COLOR: #cccccc
}

INPUT {
	PADDING-RIGHT: 3px;
	PADDING-LEFT: 3px;
	MARGIN-BOTTOM: 1px;
	PADDING-BOTTOM: 0px;
	FONT: 12px Arial;
	VERTICAL-ALIGN: middle;
	PADDING-TOP: 1px
}

.input {
	BORDER-RIGHT: #ededed 1px solid;
	PADDING-RIGHT: 0px;
	BORDER-TOP: #c0c0c0 1px solid;
	PADDING-LEFT: 1px;
	FONT-SIZE: 1em;
	PADDING-BOTTOM: 2px;
	VERTICAL-ALIGN: middle;
	BORDER-LEFT: #c0c0c0 1px solid;
	COLOR: #000;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #ededed 1px solid
}
</STYLE>
	</HEAD>
	<BODY>
		<DIV class=tip-page>
			<DIV class=tip-bgA></DIV>
			<DIV class=tip-bgB></DIV>
			<DIV class=tip-bgC>
				<DIV class=tip-top></DIV>
				<TABLE class=tip-table cellSpacing=0 cellPadding=0>
					<TBODY>
						<TR>
							<TD height=170>
								<DIV class=tip-content>
									<DIV class=lh2
										style="PADDING-RIGHT: 40px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px">
										<SPAN class=f14><s:property value="#message" escape="false"/></SPAN>
									</DIV>
								</DIV>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
			<DIV class=tip-bgB></DIV>
		</DIV>
	</BODY>
</HTML>