var origen;
var destino;
var obj2;
var obj = {
	deckCompleto : [],
	deck : [],
	resultado : []
};

$(document).ready(function() {	
	$("#collection").height(window.innerHeight-170);	
});

function createCard(c, context, origenPila) {
	var img = document.createElement('img');
	img.alt = c;
	img.id = obj[origenPila][c].idTemp;
	img.name = obj[origenPila][c].numero;
	img.src = context + "/images/thumbs/" + obj[origenPila][c].siglas + "/" + obj[origenPila][c].numero + ".jpg";
	img.className = obj[origenPila][c].tipo;
	img.draggable = "true";
	img.height = "70";
	img.width = "50";
	img.title = obj[origenPila][c].siglas;
	img.addEventListener('dragstart', function drag(ev) {
		origen = ev.target.parentNode.id;
		ev.dataTransfer.setData("Text", ev.target.id);
	}, false);
	img.onmouseover = function showImage(ev) {
		var viewCard = document.getElementById("viewCard");
//		viewCard.src = context + "/images/myl/" + obj[origenPila][c].siglas + "/" + ev.target.name + ".jpg";
		viewCard.src = context + "/images/myl/"+ev.target.title+"/" + ev.target.name + ".jpg";
		$("#spnb").text(obj[origenPila][c].nombre);
		$("#sptp").text(obj[origenPila][c].tipo);
		$("#spfr").text(obj[origenPila][c].frecuencia);
		$("#spct").text(obj[origenPila][c].coste);
		$("#spfz").text(obj[origenPila][c].fuerza);
		$("#sprz").text(obj[origenPila][c].raza);
		$("#sphb").text(obj[origenPila][c].efecto);		
	}
	return img;
}

function drawResult(context) {
	$("#collection").empty();
	var imgs=[];
	
	for ( var c = 0; c < obj["resultado"].length; c++) {
		var img = createCard(c, context, "resultado");
		imgs.push(img);
	}
	
	var divCollection = document.getElementById("collection");
	for(var c=0;c<imgs.length;c++){		
		divCollection.appendChild(imgs[c]);
	}
}

function drop(ev) {
	var context = $('#context').val();
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	destino = ev.target.id;

	if (origen == "collection" && destino == "deck1" && ev.target == "[object HTMLDivElement]") {
		var card = {
			cartaId : 0,
			cartaQt : 0
		};

		var imgAux = document.getElementById(data);

		card.cartaId = obj["resultado"][imgAux.alt].id;

		/**
		 * Verifica si ya existe la carta en el deck si existe incrementa en
		 * uno, si no existe la agrega a la lista
		 */
		var existe = false;
		for ( var c = 0; c < obj["deck"].length; c++) {
			if (obj["deck"][c].cartaId == card.cartaId) {
				existe = true;
				obj["deck"][c].cartaQt += 1;
			}
		}
		if (existe == false) {
			card.cartaQt = 1;
			obj["deck"].push(card);
		}

		var img = createCard(imgAux.alt, context, "resultado");
		img.alt = card.cartaId;
		img.height = "70";
		img.width = "50";
		img.id = ev.target.childNodes.length;
		ev.target.appendChild(img);
		totalCartas();
		
		if(img.className=="Oro"){
			totalOros(1);
		}
	} else if (origen == "deck1" && (ev.target == "[object HTMLDivElement]" || ev.target == "[object HTMLImageElement]")) {

		for ( var c = 0; c < obj.deck.length; c++) {
			if (obj.deck[c].cartaId == $("#" + data).attr("alt")) {
				obj.deck[c].cartaQt -= 1;
				if (obj.deck[c].cartaQt == 0) {
					obj.deck.splice(c, 1);
				}
			}
		}

		totalCartas();
		if($("#" + data).attr("class")=="Oro"){
			totalOros(-1);
		}
		
		$("#" + data).remove();		
		
	}

}

function drag(ev) {
	ev.dataTransfer.setData("Text", ev.target.id);
}

function allowDrop(ev) {
	ev.preventDefault();
}

function search() {
	var ediciones = document.getElementById("edicion");
	var frecuencias = document.getElementById("frecuencia");
	var tipos = document.getElementById("tipo");
	var razas = document.getElementById("raza");

	var edicionC = ediciones.options[ediciones.selectedIndex].value;
	var frecuenciaC = frecuencias.options[frecuencias.selectedIndex].value;
	var tipoC = tipos.options[tipos.selectedIndex].value;
	var razaC = razas.options[razas.selectedIndex].value;

	var criterioJson = {};

	if ($("#nombre").val() != "") {
		criterioJson['nombre'] = $("#nombre").val();
	}
	if (edicionC != "") {
		criterioJson['idEdicion'] = edicionC;
	}
	if (frecuenciaC != "") {
		criterioJson['frecuencia'] = frecuenciaC;
	}
	if (tipoC != "") {
		criterioJson['tipo'] = tipoC;
	}
	if ($("#coste").val() != "") {
		criterioJson['coste'] = parseInt($("#coste").val());
	}
	if ($("#fuerza").val() != "") {
		criterioJson['fuerza'] = parseInt($("#fuerza").val());
	}
	if (razaC != "") {
		criterioJson['raza'] = razaC;
	}
	if ($("#efecto").val() != "") {
		criterioJson['efecto'] = $("#efecto").val();
	}

	criterioJson = JSON.stringify(criterioJson);

	var context = $('#context').val();
	$.ajax({
		url : context + "/deck!search?criterioJson=" + criterioJson,
		type : "POST",
		error : function() {
			alert('Error');
		},
		success : function(data) {
			obj["resultado"] = data.resultado;
			drawResult(context);
		}
	});

}

function enviar() {
	var json = JSON.stringify(obj.deck);
	$("#lista").val(json);

	$("#frmDeck").submit();
}

function eliminar() {
	$("#frmDeck").submit();
}

function getDeck(){
	var context = $('#context').val();
	var urlMethod;
	
	if($("#idSel").length==0){
		urlMethod=context + "/deck"+"!buscarDecks";
	}else{
		urlMethod=context + "/deck/"+$("#idSel").val()+"!buscarDecks";
	}
	
	$.ajax({
		url : urlMethod,
		type : "POST",
		error : function() {
			alert('Error');
		},
		success : function(data) {
			obj["deckCompleto"]=data.deckCompleto;
			drawDeck(context);
		}
	});
}		

function drawDeck(context){
	var divDeck=document.getElementById("deck1");
	
	for(var c=0;c<obj["deckCompleto"].length;c++){
		var card={
				cartaId:0,
				cartaQt:0
		};
	
		
		card.cartaId=obj.deckCompleto[c].id;
		card.cartaQt=obj.deckCompleto[c].cantidad;
		obj.deck.push(card);
		
		for(var i=0;i<card.cartaQt;i++){
			var img = createCard(c, context, "deckCompleto");
			img.alt = obj.deck[c].cartaId;
			img.height = "70";
			img.width = "50";
			img.id = divDeck.childNodes.length;
			divDeck.appendChild(img);
			
			if(img.className=="Oro"){
				totalOros(1);
			}
		}
	}
	totalCartas();	
}

$(document).ready(function() {
	$("#btnSearch").hover(
			function(){
				$("#btnSearch").addClass("ui-state-hover")},
			function(){
				$("#btnSearch").removeClass("ui-state-hover")}
	);
	$("#btnEnviar").hover(
			function(){
				$("#btnEnviar").addClass("ui-state-hover")},
			function(){
				$("#btnEnviar").removeClass("ui-state-hover")}
	);
});


function totalCartas(){
	var total=0;
	var oros=0;
	for(var c=0;c<obj.deck.length;c++){
		total+=obj.deck[c].cartaQt;		
	}
	
	$("#total").text(total);
}

function totalOros(number){
	var oros=parseInt($("#oros").text());
		
	$("#oros").text(oros+number);
}