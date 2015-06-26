<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Modificar Perfil</title>

</head>
<body>
	<s:url id="urlCancelar" value="/usuario" includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:actionmessage id="samUsuario" theme="jquery" />

	<s:if test="model.verificado == false">
		<s:set var="confirmar" value="1" />
	</s:if>

	<s:url var="urlEdit" value="usuario/%{idSel}" />

	<s:form action="/%{urlEdit}" method="post" theme="simple"
		acceptcharset="UTF-8" cssStyle="border: 0px;">
		<s:hidden id="hdnMethod" name="_method" value="put" />

		<s:hidden id="confirm" name="confirm" value="%{confirmar}" />

		<center>
			<h1>Modificar Perfil</h1>
		</center>

		<table>
			<tr>
				<td><label>Login:</label></td>
				<td><s:property value="model.login" /></td>
			</tr>
			<tr>
				<td><label>E-mail:</label></td>
				<s:if test="model.verificado == true">
					<td><s:property value="model.email" /></td>
				</s:if>
				<s:else>
					<td><s:textfield id="email" name="model.email" maxlength="80" /></td>
				</s:else>
			</tr>
			<tr>
				<td><label>Pais:</label></td>
				<td><s:select id="slcPais" name="model.idPais"
						list="listPaises" listValue="nombre" listKey="id"
						headerValue="Seleccione" headerKey="0" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><s:if
						test="model.verificado == false">
						<sj:submit id="btnAceptar" value="Verificar" button="true"
							buttonIcon="ui-icon-star" />
					</s:if>
					<s:else>
						<sj:submit id="btnAceptar" value="Aceptar" button="true"
							buttonIcon="ui-icon-star" />
					</s:else> <sj:a id="btnCancelar" button="true" href="#"
						onclick="location.href='%{urlCancelar}'">Cancelar</sj:a></td>
			</tr>
		</table>
	</s:form>

</body>
	</html>
</jsp:root>