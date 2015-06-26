<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Ranking</title>

<jsp:text>
	<![CDATA[			 			
			<script type="text/javascript" src="${pageContext.request.contextPath}/pages/ranking/js/index.js"></script>
		 ]]>
</jsp:text>
</head>
<body>

	<form style="width: 50%; border: 0px">
		<input type="hidden" name="context" id="context"
			value="${pageContext.request.contextPath}" />
		<center>
			<h1>Ranking</h1>
		</center>

		<table style="width: 100%">
			<tr>
				<td style="width: 50%">
					<table id="tblUsers" style="width: 100%;">
						<thead>
							<tr>
								<th style="text-align: center;">Posición</th>
								<th>Nombre</th>
								<th style="text-align: center;">Victorias</th>
								<th style="text-align: center;">Derrotas</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="userList" var="user" status="rowstatus">
								<tr>
									<td style="text-align: center;"><s:property value="#rowstatus.count"/></td>
									<td><b>${user.login}</b></td>
									<td style="text-align: center;">${user.wons}</td>
									<td style="text-align: center;">${user.lost}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>


			</tr>
		</table>
	</form>
</body>
	</html>
</jsp:root>

