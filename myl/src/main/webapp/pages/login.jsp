<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Acceso al sistema</title>
<jsp:text>
	<![CDATA[ 
			
		 ]]>
</jsp:text>
</head>
<body>
	<center>
		<s:form id="doLoginFrm" name="doLoginFrm"
			action="%{#request.contextPath}/login" method="POST"
			cssStyle="border: 0px;" namespace="/">
			
			<table>
				<div class="title">Iniciar sesión</div>
				<tr>
					<td><s:textfield name="userId" label="Usuario" /></td>
				</tr>
				<tr>
					<td><s:password name="password" label="Contraseña" /></td>
				</tr>
				<tr>
					<td>
					<sj:submit id="btnAceptar" value="Entrar" button="true" buttonIcon="ui-icon-star" />
					</td>
				</tr>
			</table>
			
			<a href="${pageContext.request.contextPath}/registro/new">¿Nuevo usuario? Registrate!</a>
			<br />
			<a href="${pageContext.request.contextPath}/recuperar-pass/new">¿Olvidaste tu contraseña?</a>
			<s:fielderror id="sfe" theme="jquery"/>
			<s:actionerror id="sae" theme="jquery"/>
			<s:actionmessage id="sam" theme="jquery" />
		</s:form>
	</center>

</body>

	</html>
</jsp:root>