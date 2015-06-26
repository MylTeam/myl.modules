<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Perfil</title>
<jsp:text>
	<![CDATA[
			<script src="${pageContext.request.contextPath}/pages/gestion-usuario/js/index.js" type="text/javascript"></script>
			
								
		 ]]>
</jsp:text>
</head>
<body>
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:actionmessage id="samUsuario" theme="jquery" />


	<form style="width: 90%; border: 0px">
		<input type="hidden" name="context" id="context"
			value="${pageContext.request.contextPath}" />


		<table id="tblUsers" style="width: 100%;">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Registro</th>
					<th>Ultima Sesión</th>
					<th>Estatus</th>
					<th>Verificado</th>
					<th>Deck</th>
					<th style="text-align: center;">Acciones</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="usuarios">
					<tr>
						<td>${login} <input type="hidden" id="h${idUsuario}"
							value="${login}" />
						</td>
						<td><s:date name="fhRegistro" format="yyyy-MM-dd"/></td>
						<td><s:date name="fhLastSession" format="yyyy-MM-dd"/></td>
						<td>
						<s:if test="estatus == true">
						<img height="30" width="30"
								src="${pageContext.request.contextPath}/images/icon_active.png"
								title="Activo" />
						</s:if><s:else>
						<img height="30" width="30"
								src="${pageContext.request.contextPath}/images/icon_inactive.png"
								title="Inactivo" />
						</s:else>						
						</td>
						<td> 
						<s:if test="verificado == true">
						<img height="30" width="30"
								src="${pageContext.request.contextPath}/images/icon_verified.png"
								title="Verificado" />
						</s:if><s:else>
						<img height="30" width="30"
								src="${pageContext.request.contextPath}/images/icon_unverified.png"
								title="No verificado" />
						</s:else>
						
						</td>
						<td>${deckPred}</td>
						<td style="text-align: center; width: 18%"><a
							href="${pageContext.request.contextPath}/deck/${deckId}/edit">
								<img height="40" width="40"
								src="${pageContext.request.contextPath}/images/buttons/editfeather.png"
								title="Modificar deck" />
						</a> <a
							href="${pageContext.request.contextPath}/deck/${deckId}/deleteConfirm">
								<img height="40" width="40"
								src="${pageContext.request.contextPath}/images/buttons/elim.png"
								title="Eliminar deck" />
						</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<!-- <div id="btnNew">
			<a href="${pageContext.request.contextPath}/deck/new"><input
				type="button" value="Nuevo Usuario"></input></a>
		</div> -->
	</form>
</body>
	</html>
</jsp:root>
