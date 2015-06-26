<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registro</title>
<jsp:text>
	<![CDATA[
	
		 ]]>
</jsp:text>
</head>
<body>

	<s:url id="urlCancelar" value="/login" includeContext="true" />
	<s:actionerror id="saeMuseo" theme="jquery" />
	<s:fielderror id="sfeMuseo" theme="jquery" />


	<s:form action="%{#request.contextPath}/registro/" method="post" theme="simple" acceptcharset="UTF-8" cssStyle="border: 0px;">
		
		<center><h1>Registro</h1></center>
		<table>
			<tr>
				<td><label>Nombre de usuario:</label></td>
				<td><s:textfield id="Nombre" name="model.login" maxlength="50" /></td>
			</tr>
			<tr>
				<td><label>Contraseña:</label></td>			
				<td><s:password id="password" name="model.password" maxlength="50" /></td>
			</tr>
			<tr>
				<td><label>Confirmar contraseña:</label></td>			
				<td><s:password id="confirmPass" name="confirmPass" maxlength="50" /></td>
			</tr>
			<tr>
				<td><label>E-mail:</label></td>			
				<td><s:textfield id="email" name="model.email" maxlength="80" /></td>
			</tr>
			<tr>
				<td><label>Pais:</label></td>			
				<td><s:select id="slcPais" name="model.idPais"
						list="listPaises" listValue="nombre" listKey="id"
						headerValue="Seleccione" headerKey="-1" required="true"/>
						 </td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" buttonIcon="ui-icon-star" /> <sj:a id="btnCancelar"
						button="true" href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a></td>
			</tr>

		</table>
	</s:form>
	<center></center>
</body>
	</html>
</jsp:root>