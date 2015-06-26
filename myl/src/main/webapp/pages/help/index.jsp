<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Ayuda</title>
<jsp:text>
	<![CDATA[			 		
						
		 ]]>
</jsp:text>
<STYLE type="text/css">
table td{
vertical-align: top;
}
</STYLE>

</head>
<body>
<form style="width: 95%; height:500px; border: 0px;margin-left: auto; margin-right: auto;" id="helpform">
	<input type="hidden" name="context" id="context" value="${pageContext.request.contextPath}" />
	
	<h1>Mitos y Leyendas</h1>
	En Mitos y Leyendas adoptas el papel de un señor de la guerra, habitante de un castillo cuyo único propósito es reducir los castillos enemigos a escombros. Para lograr esto se sirve de riquezas que gastara en pagar a poderosos guerreros que se aliaran con el y tecnología que le dará ventaja en el campo de batalla.
	Todos los componentes de estas batallas épicas se encuentran representados por las cartas que componen la baraja de cada contrincante. Durante el juego los jugadores toman turnos consecutivos y con el transcurso de la partida estas cartas se juegan tanto para asediar el castillo oponente como para defender el propio.
	
		<h2>Tipos de cartas</h2>
	<table><tr><td colspan="2">
	<h3>Aliados</h3>
	</td></tr><tr><td colspan="2">
	Son cartas fundamentales en el juego, ya que son las cartas usadas mayoritariamente para atacar al oponente. Se
	juegan en la línea de defensa y pasan a la línea de ataque al momento de realizar el mismo.</td></tr>
	<tr><td colspan="2"><IMG alt="Aliado" src="${pageContext.request.contextPath}/images/myl/aliado.jpg" height="200px" align="left"/>
		<h4>Habilidades</h4>
		
		<ul>
  			<li><b>Furia:</b> puede atacar el turno que entra en juego.</li>
  			<li><b>Mercenario:</b> puedes tener más de 3 copias de esta carta en tu castillo.</li>
  			<li><b>Guardián:</b> solo puede bloquear, usualmente tienen un coste bajo y fuerza alta.</li>
  			<li><b>Absorción:</b> todo el daño de combate bloqueado por un aliado con abosrción se reduce a 0 aún si el aliado es destruido.</li>
  			<li><b>Errante:</b> solo puedes tener una de estas en tu campo de batalla pero puedes tener hasta 3 copias en tu mazo (a menos que también cuente con la habilidad Mercenario).</li>
  			<li><b>Relámpago:</b> puede ser jugada en cualquier inicio de fase o momento del juego.</li>
  			<li><b>Retorno:</b> puede ser jugada desde la zona de destierro como si estuviese en tu mano. Si es un Talismán, remuévelo del juego después de usar su Retorno.</li>
  			<li><b>Imbloqueable:</b> tu oponente no puede bloquear esta carta a menos que se diga lo contrario (por ejemplo, Una habilidad que diga "puede bloquear aliados imbloqueables").</li>
  			<li><b>Exhumar:</b> se puede jugar desde el cementerio como si estuviera en tu mano. Si es un talismán, destiérralo del juego después de usar su Exhumar.</li>
  			<li><b>Réplica X:</b> propia de talismanes, al jugar el talismán puedes pagar el coste de réplica (X) para usar el efecto del talismán dos veces.</li>
  			<li><b>Barrera:</b> Si una carta con barrera es enviada al cementerio por daño a tu mazo castillo, remuevela para dejar de botar cartas (Botar cartas cuenta tanto como daño de combate como daño directo). Ejemplo: Si botas 4 cartas pero la segunda en botar tiene barrera, puedes removerla y así solo botar las 2 primeras.</li>
  			<li><b>Indestructible:</b> no puede ser destruida por un aliado enemigo solo por efectos de cartas y sí puede ser desterrada.</li>
  			<li><b>Oro inicial:</b> Oro con habilidad, pero puedes usarlo al comienzo del juego.</li>
  			<li><b>Única:</b> solo se tiene una copia en el mazo castillo.</li>
  			<li><b>Inmortal:</b> Más que una habilidad se puede considerar como una raza adicional. Hay habilidades que usan este requisito para poder ejecutarse (por ejemplo, "busca una carta Inmortal en tu mazo castillo").</li>
  			<li><b>Indesterrable:</b> no puede ser desterrada del juego pero si mandada al cementerio</li>
  			<li><b>Maquinaria:</b> arma que se usa en la línea de apoyo, necesita al menos un aliado para usar sus habilidades</li>
  			<li><b>Embarcación:</b> arma que se usa en la línea de apoyo, no necesita aliados para usar sus habilidades.</li>
  			<li><b>Tamaño X:</b> habilidad requisito de las maquinarias, donde X es la cantidad de aliados que debes tener en juego para usar las habilidades del arma maquinaria como tal.</li>
  			<li><b>Orbe:</b> carta que solo puede ser afectada por una carta que afecte a orbe</li>
  			<li><b>Lealtad:</b> carta que no puede cambiar de dueño</li>
  			<li><b>Inmunidad:</b>carta que no puede ser afectada por efectos de otras cartas</li>
  			<li><b>Traición:</b>esta habilidad es un coste que cualquier jugador puede pagar para ganar el control de la carta con traición</li>  			
		</ul>
	</td></tr>
	<tr><td style="width: 50%">
	<h3>Talismán</h3></td>
	<td colspan="2" style="width: 50%">
	<h3>Armas</h3></td>
	</tr>
	<tr><td ><IMG alt="Talismán" src="${pageContext.request.contextPath}/images/myl/talisman.jpg" height="200px" align="left"/>
	Son cartas de efecto inmediato que al ser jugadas son enviadas al cementerio (excepto por un efecto que no lo permita), estas facilitan el juego dándole más fuerza a tus aliados, destruyendo y desterrando los aliados oponentes,
	anulándolos, entre muchas cosas más. El efecto que causan los talismanes es, normalmente, hasta la fase final, a menos que la carta lo aclare, diciendo que es de efecto permanente, por cierta cantidad de turnos o hasta que cierto evento ocurra.
	</td>
	<td ><IMG alt="Arma" src="${pageContext.request.contextPath}/images/myl/arma.jpg" height="200px" align="left"/>
	Cartas que se sitúan bajo las cartas de Aliados dándoles más fuerza u otra habilidad (ejemplo:Guantes de Hierro) o en la Línea de Apoyo en caso de tener la habilidad Maquinaria o Embarcación. Las armas con habilidad
	Maquinaria necesitan cierta cantidad de Aliados para usar sus habilidades, que aparece junto a la habilidad Tamaño. En caso de no aparecer la habilidad Tamaño, se entiende que tiene Tamaño 1. En el caso en que el arma
	tenga en su habilidad la palabra Embarcación, puede jugarse en la Línea de Apoyo, sin necesidad de tener aliados.
	</td>
	</tr>	
	<tr><td>
	<h3>Tótem</h3></td>
	<td>
	<h3>Oro</h3></td>
	</tr>
	<tr><td><IMG alt="Tótem" src="${pageContext.request.contextPath}/images/myl/totem.jpg" height="200px" align="left"/>
	Esta carta se pone en la línea de apoyo y tiene habilidades que apoyan o refuerzan las habilidades de otras cartas. Fueron las primeras en obtener la habilidad Orbe(Solo puede ser afectado por cartas que afecten puntualmente a
	orbes, es decir, si el arma Orbe es un Tótem, no será afectada por cartas que afecten Tótems, o cartas que afecten cualquier otro tipo de cartas, al menos que esta lo aclare). Los más relevantes son: Tótem del Guanaco (La ira del
	Nahual), Humm-SP y Trípoli-SP, entre otros.
	</td>
	<td ><IMG alt="Oro" src="${pageContext.request.contextPath}/images/myl/oro.jpg" height="200px" align="left"/>
	Este tipo de carta es fundamental para cualquier castillo y sirve para pagar el coste de oro de las cartas y las habilidades que lo requieran. Existen 3 tipos de oros:
	<UL>
		<li><b>Oros sin habilidad:</b> Cartas de Oro que pueden ser usados para pagar costes o habilidades, pueden ser usados como Oro Inicial. Puedes tener más de 3 copias de estos oros en tu castillo.</li>
		<li><b>Oros con habilidad:</b> Cartas de Oro que pueden ser usados para pagar costes o habilidades, no pueden ser usados como Oro Inicial a menos que tengan la Habilidad Oro Inicial, esta habilidad salió en Bestiario.</li>
		<li><b>Oros virtuales:</b> Oros generados por habilidades de cartas, pueden ser usados para pagar costes o habilidades, no pueden ser usados como Oro Inicial, no pueden ser destruidos, y generalmente duran hasta la Fase Final (fin del turno de un jugador).</li>
	</UL>
	</td>
	</tr>		
	</table>
	
	<h2>Características específicas de la baraja y el juego</h2>
	<H3>Campo de batalla</H3>
	Es el lugar donde ocurren los combates contra tu oponente. Cada carta de tu Mazo Castillo debe ser ubicada en un lugar específico dentro del Campo de Batalla. Esta área está dividida en varias partes, cada una de las cuales cumple una función específica.
	
	<table>
	<tr><td colspan="2" style="width: 50%"></td><td colspan="2" style="width: 50%"></td></tr>
	<tr><td style="text-align: center"><IMG alt="Línea de defensa" src="${pageContext.request.contextPath}/Estilos/field/wingedshield.png" height="150px" /></td><td>
	<b>Línea de defensa:</b> Cuando juegas un Aliado o pasas por una fase de agrupación, los Aliados permanecen en ésta
		zona para defender. Se debe pasar por al menos una agrupación para atacar con el Aliado, al menos que este
		disponga de la habilidad Furia, que le permite atacar en la siguiente batalla mitológica, sin ser necesario pasar por
		una agrupación.</td>
	
	<td style="text-align: center"><IMG alt="Línea de ataque" src="${pageContext.request.contextPath}/Estilos/field/wingedsword.png" height="150px" /></td><td>
	<b>Línea de ataque:</b> Cuando declaras ataque, mueves el Aliado a la línea de ataque, que está ubicada arriba(o
		delante) de la de defensa.
	</td></tr>
	
	<tr><td style="text-align: center"><IMG alt="Línea de apoyo" src="${pageContext.request.contextPath}/Estilos/field/fieldcastle.png" height="150px" /></td><td>
	<b>Línea de apoyo:</b> Aquí se colocan las cartas de totem, y armas maquinaria y embarcación, que está ubicada debajo
		(o atrás) de la de defensa
	</td>
	
	<td style="text-align: center"><IMG alt="Reserva de oros" src="${pageContext.request.contextPath}/Estilos/field/treasure.jpg" height="100px" /></td><td>
	<b>Reserva de oros:</b> Aquí van los oros que aún no se han utilizado y los que acabas de jugar, está ubicada debajo(o
		atrás) del mazo castillo(a su vez a la izquierda de la línea de apollo), cual está (el mazo castillo) a la izquierda de	
		la línea de defensa)
	</td></tr>
	
	<tr><td style="text-align: center"><IMG alt="Zona de oro pagado" src="${pageContext.request.contextPath}/Estilos/field/treasure.jpg" height="100px" /></td><td>
	<b>Zona de oro pagado:</b> Como lo dice su nombre aquí van los oros utilizados, está a la izquierda de la línea de ataque.
	</td>
	
	<td style="text-align: center"><IMG alt="Castillo" src="${pageContext.request.contextPath}/images/myl/myldeck.jpg" height="150px" /></td><td>
	<b>Castillo:</b>Es tu mazo de 50 cartas. Para vencer, no olvides incluir Aliados para atacar a tu oponente junto con sus Armas; Talismanes para obtener poderosos efecto; 
	Tótem para apoyar tus partidas; y Oros para pagar las costes de todas tus demás cartas.
	</td></tr>
	
	<tr><td style="text-align: center"><IMG alt="Cementerio" src="${pageContext.request.contextPath}/images/myl/cementerio1.jpg" height="150px" /></td><td>
	<b>Cementerio:</b>Aquí van las cartas que salen del juego cuando son destruidas o cuando lo ordena una habilidad de
		una carta, está a la izquierda del mazo castillo.
	</td>
	
	<td style="text-align: center"><IMG alt="Destierro" src="${pageContext.request.contextPath}/images/myl/destierro1.jpg" height="150px" /></td><td>
	<b>Destierro:</b> Aquí las cartas se ubican cuando por habilidad oponente o tuya se envían a esta zona, está debajo ( o
		atrás ) del cementerio, a la izquierda de la reserva de oro.
	</td></tr>
	
	<tr><td style="text-align: center"><IMG alt="Zona de remoción" src="${pageContext.request.contextPath}/images/myl/remocion1.jpg" height="150px" /></td><td colspan="3">
	<b>Zona de remoción:</b> Aquí van cartas que no pueden entrar en juego de nuevo, no hay ninguna forma de recuperar
		las cartas que están en esta zona hasta que termine la partida, aunque a medida que avancen las ediciones,
		probablemente la haya. Está ubicada a la izquierda de los oros pagados, arriba (o adelante) del cementerio. Fue
		creada para reemplazar al destierro, ya que con las habilidades de las nuevas cartas era muy fácil jugar cartas
		desde ahí
	</td></tr>
	
	</table>

	
	<H3>Remover del juego</H3>
	Las cartas removidas del juego no pueden ser vueltas a usar en el juego correspondiente. Son enviadas a una zona
	llamada Zona de Remoción del juego. Cualquier carta cuya habilidad diga "no puede salir del juego" no puede ser
	destruida, desterrada, devuelta a la mano o barajada, sin embargo, todavía puede ser removida, puesto que la zona de
	remoción se considera una zona dentro del juego.

	<h2>Reglas generales</h2>
	En el juego de MyL cada jugador sigue las siguientes reglas para comenzar: 
	
	<p>Debe buscar un oro inicial y ponerlo en su reserva y cada jugador baraja su mazo castillo;
posteriormente cada jugador debe cortar el mazo castillo y después robar ocho cartas de su mazo castillo y con esto
formaran la mano. Posteriormente el jugador que quiera cambiar su mano, baraja la mano en su mazo castillo y roba
una carta menos y así sucesivamente; luego los jugadores deciden quien parte mediante un método al azar, el jugador
que parte no debe robar carta al final de su primer turno. los pasos a seguir del turno son los siguientes:</p>


<li><b>Agrupación</b></li>
<ul>
Al comienzo de la Agrupación, antes que cualquier acción de juego, el jugador activo agrupa todas las cartas que controle. Los Aliados en su Línea de Ataque pasan a su Línea de Defensa y los Oros 
en su Zona de Oro Pagado pasan a su Reserva. Luego se pueden usar habilidades que señalen su uso durante esta Fase.
</ul>
<li><b>Fase de vigilia</b></li>
<UL>
	<li>Se puede jugar cartas o utilizar habilidades que digan que pueden ser usadas al comienzo de la fase de vigilia.</li>
	<li>Se puede bajar una, y sólo una carta oro desde la mano al juego.</li>
	<li>Se puede jugar cartas y/o utilizar habilidades de cartas según alcancen los oros.</li>
</UL>

<li><b>Batalla mitológica</b></li>
Es opcional, se divide en 4 diferentes pasos:
<ul>
Declaración de ataque:
	<li>Se puede jugar cartas o utilizar habilidades que digan que pueden ser usadas al comienzo del paso de declaración de ataque.</li>
	<li>Aquí es cuando se puede decidir con cuales aliados atacar al oponente, moviéndolos a la línea de ataque.</li>
</ul>
<UL>
Declaración de Bloqueo:
	<li>Se puede jugar cartas o utilizar habilidades que digan que pueden ser usadas al comienzo del paso de declaración de bloqueo.</li>
	<li>Es ahora donde el oponente elige que aliados defenderán su castillo, diciendo los nombres de los aliados
		bloqueadores. Cada aliado sólo puede bloquear a un aliado a la vez, al menos que su habilidad diga que no puede
		bloquear, que pueda bloquear cierta cantidad de aliados, o que pueda bloquear más de un aliado (entiéndase,
		cualquier cantidad, la que desee el jugador que lo posee).</li>
</UL>
<ul>
Guerra de Talismanes:
Se puede jugar cartas o utilizar habilidades que digan que pueden ser usadas al comienzo del paso de guerra de
talismanes. Se pueden jugar talismanes y/o utilizar habilidades de cartas, teniendo el jugador defensor la prioridad.
Esta fase termina cuando ambos jugadores pasan con su turno de jugar talismanes o utilizar habilidades.
</ul>
<ul>
Asignación de daño:
<li>Se puede jugar cartas o utilizar habilidades que digan que pueden ser usadas al comienzo del paso de asignación de daño.</li>
<li>Todo el daño de los aliados atacantes no bloqueados se asigna al mazo castillo del jugador defensor. Cuando el
aliado defensor es más fuerte que el atacante, el aliado atacante es destruido y no pasa daño a ningún mazo
castillo, cuando el atacante es más fuerte que el defensor, la diferencia pasa como daño al mazo castillo, y el
defensor es destruido. El daño se asigna botando hacia el cementerio una carta del mazo por cada daño hecho.</li>
</ul>
<li>
<b>Fase final:</b>
Se puede jugar cartas o utilizar habilidades que digan que pueden ser usadas al comienzo de la fase final. Terminan
los efectos que indiquen que terminan en la fase final y los efectos no especificados. Se puede robar una carta del
mazo castillo, terminando el turno y comenzando el turno del oponente.
</li>
		 	
	
	
	</form>
</body>
	</html>
</jsp:root>
