<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Nuevo Deck</title>
<jsp:text>
	<![CDATA[
			<script src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/deck/js/deck.js" type="text/javascript"></script>			
		 ]]>
</jsp:text>
<style type="text/css">
#collection {width:100%;height:100%;padding:0px;border:1px solid #aaaaaa;overflow:auto;}
#img {width:100%;height:100%;padding:0px;border:1px solid #aaaaaa;overflow:auto;}
#deck1 {width:100%;height:100%;padding:0px;border:1px solid #aaaaaa;overflow:auto;}
</style>
<script type="text/javascript">
	$(function() {
		getDeck();
	});		
	</script>
</head>
<body>
	<s:url id="urlCancelar" value="/usuario" includeContext="true" />
	<s:actionerror id="saeMuseo" theme="jquery" />
	<s:fielderror id="sfeMuseo" theme="jquery" />
	<s:form action="%{#request.contextPath}/deck/%{deckId}"
		method="post" theme="simple" id="frmDeck" acceptcharset="UTF-8" cssStyle="border: 0px;width:90%">		
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		
	<input type="hidden" name="context" id="context" value="${pageContext.request.contextPath}"/>	
	<input type="hidden" name="idSel" id="idSel" value="${idSel}"/>
		
	
	<table style="height: 100%;width: 100%;">
	<tr>
	<!-- inicia la primer columna -->	
	<td style="width: 25%">
		
	<!-- inicio de tabla de información -->
	<table><tr><td style="width: 40%;vertical-align: top">
	
	<table style="width: 100%">
	<tr><td style="width: 100%;">
	<img id="viewCard" height="209" width="150"/>
	</td></tr>
	</table>
	
	</td><td style="width: 60%">
	
	<table>
	<tr><td style="width: 10px;height: 50px">Nombre:</td><td><span id="spnb"></span></td></tr>
	<tr><td>Tipo:</td><td><span id="sptp"></span></td></tr>
	<tr><td>Frecuencia:</td><td><span id="spfr"></span></td></tr>
	<tr><td>Coste:</td><td><span id="spct"></span></td></tr>
	<tr><td>Fuerza:</td><td><span id="spfz"></span></td></tr>
	<tr><td>Raza:</td><td><span id="sprz"></span></td></tr>
	<tr><td colspan="2" style="height: 150px;font-size: 11px;vertical-align: top"><span id="sphb"></span></td></tr>
	</table>
	
	</td></tr></table>
	<!-- fin de tabla de información -->
	</td>	
	
	<!-- inicia la tercer columna -->
	<td style="width: 45%; overflow: auto;">
	
	<table style="height: 100%;width: 100%;">
	<tr style="height: 80%">
	<td>
	<div id="deck1" ondrop="drop(event)" ondragover="allowDrop(event)">
	
	</div>
	</td>
	</tr>
	
	<tr style="height: 20%">
	<td>
	
	<label>Nombre:</label>
	<s:textfield id="Nombre" name="model.deckNombre" maxlength="50" disabled="true"/>
	
	<input type="button" id="btnEnviar" value="Eliminar" onclick="eliminar()" class="ui-button ui-widget ui-state-default ui-corner-all"/>
	</td>
	</tr>
	</table>
		
	</td>
	</tr>	
	</table>


	</s:form>
</body>
</html>
</jsp:root>


		
