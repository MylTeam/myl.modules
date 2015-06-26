<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:s="/struts-tags"
	xmlns:sj="/struts-jquery-tags" xmlns:sjc="/struts-jquery-chart-tags"
	xmlns:log="http://jakarta.apache.org/taglibs/log-1.0"
	xmlns:sjg="/struts-jquery-grid-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<sj:head debug="true" jqueryui="true" jquerytheme="ui-darkness"
	locale="es" />

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_page.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_table.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_table_jui.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/Estilos/print.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/Estilos/main.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/Estilos/default.css?123" />
<!-- Attach our CSS joyride-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Estilos/joyride-2.1.css"/>
    <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/Estilos/demo-style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Estilos/mobile.css"/> -->
    
<jsp:text>
	<![CDATA[
	
			<script src="${pageContext.request.contextPath}/scripts/jquery-ui.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/struts/js/plugins/jquery.form.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/jquery.lazyload.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/dataTables/media/js/jquery.dataTables.js" type="text/javascript"></script>			
			<script src="${pageContext.request.contextPath}/scripts/jquery.feedback.js" type="text/javascript"></script>			
			<script src="${pageContext.request.contextPath}/scripts/cdt-util.js" type="text/javascript"></script>			
			<script src="${pageContext.request.contextPath}/scripts/blockUI.js" type="text/javascript"></script>
			
			
    		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.cookie.js"></script>
    		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/modernizr.mq.js"></script>
    		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.joyride-2.1.js"></script>
			<script src="${pageContext.request.contextPath}/scripts/help.js" type="text/javascript"></script>
		]]>
</jsp:text>

<decorator:head />


</head>
<body>
	<div id="pageHeader">
		<div class="banner">
			<img src="${pageContext.request.contextPath}/images/myl/myl.png" /> <label
				style="font-size: 15px;"></label>
		</div>
	</div>
	<input type="text" style="display: none;" id="hdnRutaContexto"
		value="${pageContext.request.contextPath}" />
	<s:if test="#session.usuario != null">
		<div id="pageMenu">
			<center>
				<table style="margin-top: 0px;">
					<tr>
						<td width="20%"><a class="selected"
							href="${pageContext.request.contextPath}/usuario">
								<h3 id="mnPerfil" style="width: 97%">Perfil</h3>
						</a></td>
						<td width="20%"><a class="selected"
							href="${pageContext.request.contextPath}/lobby">
								<h3 id="mnJugar" style="width: 97%">Jugar</h3>
						</a></td>
						<!-- <td width="17%"><a class="selected"
							href="${pageContext.request.contextPath}/ranking">
								<h3 id="mnRanking" style="width: 97%">Ranking</h3>
						</a></td> -->
						<td width="20%"><a class="selected" id="ahelp"
							href="javascript:showHelp();"><h3 id="mnAyuda" style="width: 97%">Ayuda
									y reglas</h3></a></td>
						<td width="20%"><a class="selected"
							href="${pageContext.request.contextPath}/contacto/new"><h3 id="mnComentario"
									style="width: 97%; font-size: 13px">Comentarios / Reportar
									un problema</h3></a></td>
						<td width="20%"><a class="selected"
							href="${pageContext.request.contextPath}/logout"><h3 id="mnCerrar"
									style="width: 97%">Cerrar Sesión</h3></a></td>
					</tr>
				</table>
			</center>
		</div>
	</s:if>
	<div id="pageContent">

		<decorator:body />
	</div>



	<div id="pageFooter">
		<p>La información mostrada en este sitio sobre las cartas,
			incluidos los elementos gráficos, son copyright de sus respectivos
			dueños. Este sitio no tiene relación alguna con los respectivos
			propietarios de dicho material.</p>
		<s:if test="%{#session.usuario}">
			<p>
				<a href="${pageContext.request.contextPath}/pages/changelog.jsp">Changelog
					v1.9</a>
			</p>
		</s:if>
	</div>

	<div style="display: none;">
		<div id="dialog-help">
			<div id="pageMenuDialog">
				<center>
					<table style="margin-top: 0px;">
						<tr>
							<td width="20%"><a class="selected" href="pages/help/index.jsp"
								target="iframe">
									<h3 style="width: 97%">Reglas generales</h3>
							</a></td>
							<td width="20%"><a class="selected"
								href="pages/help/index-howto.jsp" target="iframe">
									<h3 style="width: 97%">Como jugar</h3>
							</a></td>
							<td width="20%"><a class="selected"
								href="pages/help/index-formatos.jsp" target="iframe">
									<h3 style="width: 97%">Formatos</h3>
							</a></td>
						</tr>
					</table>
				</center>
			</div>

			<iframe src="${pageContext.request.contextPath}/pages/help/index.jsp"
				width="100%" height="90%" id="iframe" name="iframe" />

		</div>
	</div>

</body>
	</html>
</jsp:root>