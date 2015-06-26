<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0" xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link type="text/css" href="${pageContext.request.contextPath}/scripts/jquery.contextMenu.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/Estilos/smoothness/jquery-ui-1.8.22.custom.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/Estilos/defaultchat.css?123" rel="stylesheet" />

<jsp:text>
	<![CDATA[
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.min.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.22.custom.min.js"></script>
			<script src="${pageContext.request.contextPath}/scripts/jquery.contextMenu.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/wsclient.js?2032" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/game.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/castillo.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/menu.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/oponente.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/mousetrap.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/shortcuts.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/phases.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/pages/chat/js/gral.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/cdt-util.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/blockUI.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
 
<title>Duel Room</title>
 <script type="text/javascript">
	$(function() {
		$('#conversations').tabs();
		$('#userName').val(document.getElementById("user1").value);
		$('#userNameTwo').val(document.getElementById("user2").value);
		wsclient.connect(document.getElementById("user1").value,document.getElementById("user2").value);
	});		
	</script>	
<style type="text/css">
#aux1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#aux2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#mano1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#mano2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#apoyo1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#apoyo2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#defensa1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#defensa2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#ataque1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#ataque2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}

#oropagado2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#reserva2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#remocion2d {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}
#castillo2 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}
#cementerio2d {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}
#destierro2d {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}

#oropagado1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#reserva1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:auto;}
#remocion1d {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}
#castillo1 {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}
#cementerio1d {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}
#destierro1d {width:100%;height:100%;padding:0px;border:1px solid black;overflow:hidden;}

.campo{height: 25%}

</style>

</head>
<body>	

<input type="hidden" name="hidden" id="hidden" value="${pageContext.request.contextPath}"/>
<s:hidden id="key" name="key" />

<div id="bar">
<input type="button" value="Nuevo Juego" onclick="newGame()"/>
<input type="button" value="Reglas" onclick="showHelp()"/>
<input type="button" value="Regresar" onclick="returnLobby()"/>
</div>	
<table id="Table1" class="Table" style="HEIGHT: 97%; WIDTH: 100%">
  <tbody>
    <tr style="HEIGHT: 48%;">
      <td style="WIDTH: 25%;">
        <table id="Ctrl1" class="Table" style="HEIGHT: 100%; WIDTH: 100%">
          <tbody>
          	<tr style="height: 33%">
              <td colspan="4"><div id="reserva2" class="divredgold" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>              
            </tr>
            <tr style="height: 33%">
              <td colspan="4"><div id="oropagado2" class="divredgoldpayed" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>              
            </tr>
            <tr>
              <td style="WIDTH: 25%"><div id="remocion2d" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="remocion2" name="remocion2" class="opremocion" src="${pageContext.request.contextPath}/images/myl/remocion1.jpg" width="100%" />
              </div></td>
              <td style="WIDTH: 25%"><div id="destierro2d" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="destierro2" name="destierro2" class="opdestierro" src="${pageContext.request.contextPath}/images/myl/destierro1.jpg" width="100%" />
              </div></td>
              <td style="WIDTH: 25%"><div id="cementerio2d" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="cementerio2" name="cementerio2" class="opcementerio" src="${pageContext.request.contextPath}/images/myl/cementerio1.jpg" width="100%"/>
              </div></td>
              <td style="WIDTH: 25%"><div id="castillo2" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="deck2" name="deck2" src="${pageContext.request.contextPath}/images/myl/myldeck.jpg" width="100%"/>
              </div></td>
            </tr>
          </tbody>
        </table></td>
      <td style="WIDTH: 50%;">
        <table id="P1" class="Table" style="HEIGHT: 100%; WIDTH: 100%">
          <tbody>
            <tr style="HEIGHT: 70px;">
            <td style="width: 20%"><div id="aux2" class="divred" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
              <td style="width: 80%"><div id="mano2" align="center" class="divred" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>              
            </tr>
            <tr class="campo">
              <td colspan="2"><div id="apoyo2" class="divredcastle" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
            </tr>
            <tr class="campo">
              <td colspan="2"><div id="defensa2" class="divredshield" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
            </tr>
            <tr class="campo">
              <td colspan="2"><div id="ataque2" class="divredsword" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
            </tr>
          </tbody>
        </table></td>
      <td id="info" style="WIDTH: 25%;">
      
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
	
      <!-- <center><img id="viewCard" src="" draggable="true" height="100%"/></center> -->
      </td>
    </tr>
    <tr style="HEIGHT: 4%;">
    <td style="text-align: right;">Mazo: <span id="totalp2"></span></td>
    <td>
    <img id="fagrup" name="fagrup" class="fase" src="${pageContext.request.contextPath}/images/fases/agrupacion.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="fvigil" name="fvigil" class="fase" src="${pageContext.request.contextPath}/images/fases/vigilia.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="fataqu" name="fataqu" class="fase" src="${pageContext.request.contextPath}/images/fases/ataque.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="fbloqu" name="fbloqu" class="fase" src="${pageContext.request.contextPath}/images/fases/bloqueo.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="ftalis" name="ftalis" class="fase" src="${pageContext.request.contextPath}/images/fases/guerratalismanes.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="fdanio" name="fdanio" class="fase" src="${pageContext.request.contextPath}/images/fases/danio.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="ffinal" name="ffinal" class="fase" src="${pageContext.request.contextPath}/images/fases/final.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    <img id="fturno" name="fturno" class="fase" src="${pageContext.request.contextPath}/images/fases/turno.png" ondblclick="phaseChange(event)" height="50%" width="12.5%"/>
    </td>
    <td>Mazo: <span id="totalp1"></span></td>
    </tr>
    <tr style="HEIGHT: 48%;">
      <td id="chat" style="WIDTH: 25%;">
      	<table style="width: 100%">
			<tr><td>
 				<div id="container">
					<div class="leftPanel">
							<input type="hidden" id="userName" /><span class="onLineUserName" ></span>
							<input type="hidden" id="userNameTwo" />
					 </div>
										
					<div id="conversations">
						<ul>
						</ul>
					</div>
					 </div>
					</td></tr></table>
					 
      </td>
      <td style="WIDTH: 50%;">
        <table id="P2" class="Table" style="HEIGHT: 100%; WIDTH: 100%">
          <tbody>
            <tr class="campo">
              <td colspan="2"><div id="ataque1" class="divbluesword" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>              
            </tr>
            <tr class="campo">
              <td colspan="2"><div id="defensa1" class="divblueshield" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
            </tr>
            <tr class="campo">
             <td colspan="2"><div id="apoyo1" class="divbluecastle" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
            </tr>
            <tr style="HEIGHT: 70px;">
              <td style="width: 80%"><div id="mano1" class="divblue" align="center" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div>
				<td style="width: 20%"><div id="aux1" class="divblue" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>
              </td>
            </tr>
          </tbody>
        </table></td>
      <td style="WIDTH: 25%;">
        <table id="Ctrl2" class="Table" style="HEIGHT: 100%; WIDTH: 100%">
          <tbody>            
            <tr>
              <td style="WIDTH: 25%"><div id="castillo1" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="deck1" name="deck1" class="deck" src="${pageContext.request.contextPath}/images/myl/myldeck.jpg" ondblclick="drawCard()" draggable="true" width="100%" ondragstart="wsclient.drag(event)"/>
              </div></td>
              <td style="WIDTH: 25%"><div id="cementerio1d" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="cementerio1" name="cementerio1" class="cementerio" src="${pageContext.request.contextPath}/images/myl/cementerio1.jpg" draggable="true" width="100%" ondragstart="wsclient.drag(event)"/>
              </div></td>
              <td style="WIDTH: 25%"><div id="destierro1d" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="destierro1" name="destierro1" class="destierro" src="${pageContext.request.contextPath}/images/myl/destierro1.jpg" draggable="true" width="100%" ondragstart="wsclient.drag(event)"/>
              </div></td>
              <td style="WIDTH: 25%"><div id="remocion1d" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)">
              <img id="remocion1" name="remocion1" class="remocion" src="${pageContext.request.contextPath}/images/myl/remocion1.jpg" draggable="true" width="100%" ondragstart="wsclient.drag(event)"/>
              </div></td>
            </tr>
            <tr style="height: 33%">
              <td colspan="4"><div id="oropagado1" class="divbluegoldpayed" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>              
            </tr>
            <tr style="height: 33%">
             <td colspan="4"><div id="reserva1" class="divbluegold" ondrop="wsclient.drop(event)" ondragover="wsclient.allowDrop(event)"></div></td>              
            </tr>
          </tbody>
        </table></td>
    </tr>
  </tbody>
</table>
	
	 
	 <input type="hidden" name="user1" id="user1" value="${user1}"/>
	<input type="hidden" name="user2" id="user2" value="${user2}"/>

	 <div style="display:none;">
	 <div id="dialog" >
	</div>
	</div>
 
 	<div style="display:none;">
	 <div id="dialog-cant" >
	 	<input type="text" name="cantidad" id="cantidad" />
	</div>
	</div>
	
	<div style="display:none;">
	 <div id="dialog-udis" >
	 	<div id="content-udis"></div>	 	
	</div>
	</div>
	
	<div style="display:none;">
	 <div id="dialog-newg" >
	 	<div id="content-newg"></div>
	</div>
	</div>
	
	<div style="display:none;">
	 <div id="dialog-help" style="background-color: black;">
	 	
	 		<center>
			<table style="margin-top: 0px;background-color: gray;">
				<tr>
					<td width="20%" align="center"><a class="selected"
						href="help/index.jsp" target="iframe">Reglas generales
					</a></td>
					<td width="20%" align="center"><a class="selected" 
						href="help/index-howto.jsp" target="iframe">Como jugar
					</a></td>
					<td width="20%" align="center"><a class="selected" 
						href="help/index-formatos.jsp" target="iframe">Formatos
					</a></td>
				</tr>
			</table>
			</center>
		
	 	<iframe src="${pageContext.request.contextPath}/help/index.jsp" width="100%" height="70%" id="iframe" name="iframe"/>
	 	
	</div>
	</div>
	
	
</body>
</html>
</jsp:root>

