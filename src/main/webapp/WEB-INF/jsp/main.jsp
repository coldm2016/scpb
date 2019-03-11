<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户个人信息主页</title>
</head>
<body>

	根据ID查询用户信息成功<br/>
	用户信息如下：<br/>
	用户ID: ${id}<br/>
	用户名：${account}<br/>
	企业银行账户：${bank}<br/>
	企业名称：${name}<br/>
	UCC：${UCC}<br/>
	LPC：${LPC}<br/>
	企业类型：${type}<br/>
	<form action="<%=request.getContextPath()%>/chainTickets" method="get">
	    <input type="hidden" name="id" value="${id }"/>
		<table width="100%" border=1>
			<tr>
			<td><input type="submit" value="链票查询" /></td>
			</tr>
		</table>
		链票列表：
		<table width="100%" border=1>
		<tr>
			<td>链票ID</td>
			<td>金额</td>
			<td>生成时间</td>
			<td>开票企业</td>
			<td>过期时间</td>
			<td>链票状态</td>
		</tr>
		<c:forEach items="${chainTicketList }" var="chainTicket">
		<tr>
			<td>${chainTicket.id }</td>
			<td>${chainTicket.amount }</td>
			<td>${chainTicket.drawTime}</td>
			<td>${chainTicket.drawEnterprise }</td>
			<td>${chainTicket.deadline }</td>
			<td>${chainTicket.state }</td>
		</tr>
		</c:forEach>
		
		</table>
	</form>
</body>
</html>
