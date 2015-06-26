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
			<script src="${pageContext.request.contextPath}/pages/usuario/js/usuario.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/usuario/js/twitter.js" type="text/javascript"></script>
			
								
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

		<table style="width: 100%">
			<tr>

				<td style="width: 50%; vertical-align: top">

					<table style="width: 100%">
						<tr>
							<td><h2>Bienvenido ${usuario.login}</h2></td>
						</tr>
						<tr>
							<td><input type="hidden" name="deck" id="deck"
								value="${usuario.deckPred}" /> <b>Mazo: <span id="result">
								</span></b></td>
							<td ><b><a id="lkPerfil"
									href="${pageContext.request.contextPath}/usuario/${usuario.idUsuario}/edit">Modificar
										Perfil</a></b></td>
							<!-- <td><b><a
									href="${pageContext.request.contextPath}/usuario/${usuario.idUsuario}">Tus
										estadísticas</a></b></td> -->
						</tr>

					</table>

					<table id="tblDeck" style="width: 100%;">
						<thead>
							<tr>
								<th>Nombre</th>
								<th>Formato</th>
								<th id="opcPred" style="text-align: center;">Predeterminado</th>
								<th id="opcAcciones" style="text-align: center;">Acciones</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="lista">
								<tr>
									<td>${deckNombre} <input type="hidden" id="h${deckId}"
										value="${deckNombre}" />
									</td>
									<td style="width: 15%">${formato.nombre}</td>
									<td style="text-align: center; width: 18%"><input
										id="deckpred" type="radio" name="deckpred"
										onclick="setSelection(this.value)" value="${deckId}" /></td>
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

				</td>

				<td  style="width: 50%; height: 100%; text-align: center;"><a id="tipNews"
					class="twitter-timeline" href="https://twitter.com/MylOnlineZ"
					data-widget-id="521443826024476672">Tweets por @MylOnlineZ</a></td>
			</tr>
		</table>
		<div id="btnNew">
			<a href="${pageContext.request.contextPath}/deck/new"><input
				type="button" value="Nuevo Mazo"></input></a>
		</div>
	</form>
	
	      <!-- Tip Content -->
    <ol id="joyRideTipContent">
      <li data-id="mnPerfil" data-text="Siguiente" class="custom">
        <h2>Perfil</h2>
        <p>Aquí podrás ver tu información, los mazos que has armado y las noticias de la plataforma.</p>
      </li>
      <li data-id="mnJugar" data-button="Siguiente" data-options="tipLocation:top;tipAnimation:fade">
        <h2>Jugar</h2>
        <p>Podrás acceder a la sala de jugadores en donde podrás charlar y retar a duelo a otros jugadores. Se requiere tener un mazo predeterminado para poder entrar.</p>
      </li>
      <!-- <li data-id="mnRanking" data-button="Siguiente" data-options="tipLocation:top;tipAnimation:fade">
        <h2>Ranking</h2>
        <p>Podrás ver las estadísticas de otros jugadores. (En construcción)</p>
      </li> -->
      <li data-id="mnAyuda" data-button="Siguiente" data-options="tipLocation:top;tipAnimation:fade">
        <h2>Ayuda</h2>
        <p>Podrás ver todo lo relacionado con el mundo de Mitos y Leyendas: Reglas, modos de juego, formatos. (En construcción)</p>
      </li>
      <li data-id="mnComentario" data-button="Siguiente" data-options="tipLocation:top;tipAnimation:fade">
        <h2>Comentarios</h2>
        <p>En esta sección podrás reportar algún problema con la aplicación, sugerencias, quejas y comentarios.</p>
      </li>
      <li data-id="lkPerfil" data-button="Siguiente" data-options="tipLocation:right;tipAnimation:fade">
        <h2>Modificar perfil</h2>
        <p>Desde esta sección podrás modificar tus datos o verificar tu correo electrónico.</p>
      </li>
      <li data-id="opcPred" data-button="Siguiente" data-options="tipLocation:right;tipAnimation:fade">
        <h2>Mazo predeterminado</h2>
        <p>Para poder jugar debes colocar como predeterminado un mazo dando click sobre el que quieras utilizar.</p>
      </li>
      <li data-id="opcAcciones" data-button="Siguiente" data-options="tipLocation:right;tipAnimation:fade">
        <h2>Gestión del mazo</h2>
        <p>Podrás crear, modificar y eliminar tus mazos como desees.</p>
      </li>
      <li data-id="tipNews" data-button="Finalizar" data-options="tipLocation:right;tipAnimation:fade">
        <h2>Noticias</h2>
        <p>Se muestran todas las noticias relacionadas con la plataforma.</p>
      </li>
    </ol>
</body>
	</html>
</jsp:root>
