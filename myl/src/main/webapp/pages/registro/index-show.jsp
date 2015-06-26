<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Confirm</title>

</head>
<body>
	<s:actionerror id="saeConfirmacion" theme="jquery" />	
	<s:fielderror id="sfeConfirmacion" theme="jquery" />
	<s:actionmessage id="samConfirmacion" theme="jquery" />
	<s:if test="#session.usuario != null">
		<a href="${pageContext.request.contextPath}/usuario"><h1>Regresar</h1></a>
	</s:if><s:else>
	<a href="${pageContext.request.contextPath}/login"><h1>Regresar</h1></a>
	</s:else>
</body>
	</html>
</jsp:root>


