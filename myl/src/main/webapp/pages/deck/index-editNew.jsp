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
	<s:form action="%{#request.contextPath}/deck/" method="post" id="frmDeck" acceptcharset="UTF-8" theme="simple" cssStyle="border: 0px;height:100%;width:90%">
	<input type="hidden" name="context" id="context" value="${pageContext.request.contextPath}"/>

	<table  style="height: 100%;width: 100%;">
	<tr>
	<!-- inicia la primer columna -->	
	<td style="width: 30%">
	<table  style="height: 100%; width: 100%">
	<tr style="height: 200px"><td>
	<table>
	<tr><td>Nombre:</td><td><input type="text" id="nombre" /></td></tr>
	<tr><td>Edición:</td><td>
	<select id="edicion">		
		<s:iterator value="ediciones">		
  			<option value="${id}">${nombre}</option>
		</s:iterator>
	</select>
	</td></tr>
	<tr><td>Frecuencia:</td><td>
	<select id="frecuencia">
	</select>
		<option value="">--Seleccione--</option>
		<option value="Milenaria">Milenaria</option>
		<option value="Ultra Real">Ultra Real</option>
		<option value="Real">Real</option>
		<option value="Cortesano">Cortesano</option>
		<option value="Vasallo">Vasallo</option>		
	</td></tr>
	<tr><td>Tipo:</td><td>
	<select id="tipo">
		<option value="">--Seleccione--</option>
		<s:iterator value="tipos" var="valor">		
  			<option value="${valor}">${valor}</option>
		</s:iterator>
	</select>		
	</td></tr>
	<tr><td>Coste:</td><td><input type="text" id="coste" /></td></tr>	
	<tr><td>Fuerza:</td><td><input type="text" id="fuerza" /></td></tr>
	<tr><td>Raza:</td><td>
	<select id="raza">
		<option value="">--Seleccione--</option>
		<s:iterator value="razas" var="valor">		
  			<option value="${valor}">${valor}</option>
		</s:iterator>
	</select>
	</td></tr>
	<tr><td>Efecto:</td><td>
	<input type="text" style="width: 100%" id="efecto"/>
	</td></tr>
	
	<tr><td>
	<input type="button" id="btnSearch" onclick="search()" value="Buscar" class="ui-button ui-widget ui-state-default ui-corner-all"/></td></tr>
	</table></td></tr>	
	<tr style="height: 60%">
	<td>
	<!-- inicio de tabla de información -->
	<table><tr><td style="width: 40%;vertical-align: top;">
	
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
	</tr>
	</table>
	</td>		
		
	
	<!-- inicia la segunda columna para el resultado y la vista de la carta -->
	<td style="width: 30%;">
	<table id="tipResultado" style="height: 100%;width: 100%;">
	<tr style="height: 100%">
	<td style="vertical-align: top;">
	<div id="collection" ondrop="drop(event)" ondragover="allowDrop(event)">
	 <LABEL style="display: none;">m</LABEL>
	</div>
	</td>
	</tr>
		
	</table>
	</td>
	
	<!-- inicia la tercer columna -->
	<td style="width: 39%;">
	
	<table id="tipMazo" style="height: 100%;width: 100%;">
	<tr style="height: 80%">
	<td>
	<div id="deck1" ondrop="drop(event)" ondragover="allowDrop(event)">
	<LABEL style="display: none;">m</LABEL>
	</div>
	</td>
	</tr>
	
	<tr style="height: 20%">
	<td valign="top">
	<s:hidden id="lista" name="lista" value="" />	
	<table>
	<tr><td>Cartas en mazo: <span id="total"></span></td></tr>
	<tr><td>Oros: <span id="oros">0</span></td></tr>
	<tr>
	<td id="tipFormato">Nombre:</td>	
	<td><s:textfield id="Nombre" name="model.deckNombre" maxlength="50" label="Nombre" /></td>
	<td>
	<input type="button" id="btnEnviar" onclick="enviar()" value="Guardar" class="ui-button ui-widget ui-state-default ui-corner-all"/>
	</td>
	</tr>
	<tr>
	<td>Formato:</td>
		<td>
		<s:select id="slcFormato" name="model.formatoId"
					list="formatos" listValue="nombre" listKey="id" headerValue="Seleccione" headerKey="-1" required="true"/>
		</td>
	</tr>
	</table>	
	
	</td>
	</tr>
	</table>
		
	</td>
	</tr>	
	</table>


	</s:form>
	
	<!-- Tip Content -->
    <ol id="joyRideTipContent">
      <li data-id="tipMazo" data-text="Siguiente" data-options="tipLocation:left;tipAnimation:fade" class="custom">
        <h2>Crear Mazo</h2>
        <p>Hola, en esta sección podrás crear tu Mazo Castillo.</p>
      </li>
      <li data-id="edicion" data-button="Siguiente" data-options="tipLocation:right;tipAnimation:fade">
        <h2>Buscar cartas</h2>
        <p>Desde aquí podrás buscar las cartas necesarias para armar tu mazo, tan sólo selecciona los filtros deseados y oprime el botón buscar. Es necesario seleccionar una edición para buscar cartas.</p>
      </li>
      <li data-id="tipResultado" data-button="Siguiente" data-options="tipLocation:right;tipAnimation:fade">
        <h2>Cartas encontradas</h2>
        <p>En esta sección aparecerán todas las cartas que coincidan con los criterios de búsqueda ingresados.</p>
      </li>
      <li data-id="tipMazo" data-button="Siguiente" data-options="tipLocation:left;tipAnimation:fade">
        <h2>Tu Mazo Castillo</h2>
        <p>En esta sección armarás tu mazo castillo, tan solo arrastra con el mouse las cartas que desees una por una desde la sección anterior hacia ésta. Para quitar una carta sólo arrastrala de vuelta a la sección de cartas encontradas.</p>
      </li>
      <li data-id="tipFormato" data-button="Siguiente" data-options="tipLocation:top;tipAnimation:fade">
        <h2>Formato</h2>
        <p>Una vez que hayas armado tu mazo elige el formato y nombre del mismo. Recuerda que cada formato está compuesto por una serie de ediciones, por ejemplo "Primer bloque" abarca desde "Espada sagrada" hasta "Encrucijada"</p>
      </li>
      
    </ol>
</body>
</html>
</jsp:root>