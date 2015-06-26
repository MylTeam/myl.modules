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
		 	
			<script src="${pageContext.request.contextPath}/pages/usuario/js/deckselected.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/usuario/js/index-show.js" type="text/javascript"></script>			
			
								
		 ]]>
</jsp:text>
</head>
<body>

	<form style="width: 50%; border: 0px">
		<input type="hidden" name="context" id="context"
			value="${pageContext.request.contextPath}" />
		<center>
			<h1>Estadísticas de ${usuario.login}</h1>
			<h2>
				<table>
					<tr>
						<td>Victorias: ${usuario.wons}</td>
						<td>Derrotas: ${usuario.lost}</td>
					</tr>
				</table>
			</h2>
		</center>

		<table style="width: 100%">
			<tr>
				<td style="width: 50%">
					<table id="tblWon" style="width: 100%;">
						<thead>
							<tr>
								<th>Nombre</th>
								<th>Victorias</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="duelosGanados" var="duelo">
								<tr>
									<td>${duelo.loser.login}</td>
									<td>${duelo.dueloQt}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>
				<td style="width: 50%">
					<table id="tblLost" style="width: 100%;">
						<thead>
							<tr>
								<th>Nombre</th>
								<th>Derrotas</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="duelosPerdidos" var="duelo">
								<tr>
									<td>${duelo.winner.login}</td>
									<td>${duelo.dueloQt}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>


			</tr>
		</table>
		<!-- <div id="btnNew">
			<a href="${pageContext.request.contextPath}/deck/new"><input
				type="button" value="Nuevo Mazo"></input></a>
		</div> -->
	</form>
</body>
	</html>
</jsp:root>

