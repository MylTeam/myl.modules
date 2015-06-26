<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Contacto</title>
<jsp:text>


</jsp:text>
</head>
<body>

	<s:url id="urlCancelar" value="/usuario" includeContext="true" />
	<s:actionerror id="saeContacto" theme="jquery" />
	<s:fielderror id="sfeContacto" theme="jquery" />


	<s:form action="%{#request.contextPath}/contacto/" method="post" theme="simple" acceptcharset="UTF-8" cssStyle="border: 0px;">
		
		<center><h1>Contacto</h1></center>
		<table>
			<tr>
				<td><label>Asunto:</label></td>
				<td><s:textfield id="Asunto" name="asunto" size="100" maxlength="100" /></td>
			</tr>
			<tr>
				<td><label>Mensaje:</label></td>
				<td><s:textarea id="Mensaje" name="mensaje" cols="100" rows="10" /></td>							
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