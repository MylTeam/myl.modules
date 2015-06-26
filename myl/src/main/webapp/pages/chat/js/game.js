var origen;
var destino;
var origenP;

var movedCard={
		carta:"",
		origen: "",
		destino: "",		
};

var obj = {
	deck1 : [],
	mano1 : [],
	apoyo1 : [],
	defensa1 : [],
	ataque1 : [],
	reserva1 : [],
	oropagado1 : [],
	cementerio1 : [],
	destierro1 : [],
	remocion1 : [],
	aux1:[]
};

var objOp={
		mano1: [],
		deck1 : [],
		apoyo1 : [],
		defensa1 : [],
		ataque1 : [],
		reserva1 : [],
		oropagado1 : [],
		cementerio1 : [],
		destierro1 : [],
		remocion1 : [],
		aux1:[]
};

$(document).ready(function() {			
	
	var context = $('#hidden').val();
	$.ajax({
		url : context + "/chat!prueba",
		type : "POST",
		error : function() {
			alert('Error');
		},
		success : function(data) {
			obj["deck1"] = data.deck1;
			obj["deck1"].sort(function() {
				return Math.random() - 0.5;
			});
		}
	});

});

function drawHand(context) {
	var divMano = document.getElementById("mano1");
	for ( var c = 0; c < 8; c++) {
		drawCard();
	}
}

function drawCard() {
	if(obj.deck1.length!=0){
		var context = $('#hidden').val();
		var divMano = document.getElementById("mano1");
		var img = createCard(0, context, "deck1");
		divMano.appendChild(img);
		obj["mano1"].unshift(obj["deck1"].splice(0, 1)[0]);		
		msgLogCard(null, "deck1", "mano1", "Robando carta");
	}else{
		msgLog("El castillo no tiene mas cartas");
//		lose=true;
	}
}

function createCard(c, context, origenPila) {
	var img = document.createElement('img');
	img.id = obj[origenPila][c].idTemp;
	img.name = obj[origenPila][c].numero;	
	img.src = context + "/images/thumbs/"+obj[origenPila][c].siglas+"/" + obj[origenPila][c].numero + ".jpg";
	
	if(img.id.indexOf("tc")>=0){
		img.className=obj[origenPila][c].tipo+" cardright opcard";
	}else{
		img.className=obj[origenPila][c].tipo+" cardright owcard";
	}
	img.draggable = "true";
	img.height = "70";
	img.alt=obj[origenPila][c].siglas;
	img.addEventListener('dragstart', function drag(ev) {		
		origen = ev.target.parentNode.id;
		if(origen=="dialog"){
			origen=origenPila;
			origenP="dialog";
		}else{
			origenP="";
		}
		ev.dataTransfer.setData("Text", ev.target.id);
	}, false);
	img.onmouseover = function showImage(ev) {
		var viewCard = document.getElementById("viewCard");
		viewCard.src = context + "/images/myl/"+ev.target.alt+"/" + ev.target.name + ".jpg";
		
		var lista=ev.target.parentNode.id;
		if(lista=="dialog"){
			lista=$("#dialog").attr("name");
		}
		
		for(var q=0;q<obj[lista].length;q++){
			if(obj[lista][q].idTemp==ev.target.id){
				$("#spnb").text(obj[lista][q].nombre);
				$("#sptp").text(obj[lista][q].tipo);
				$("#spfr").text(obj[lista][q].frecuencia);
				$("#spct").text(obj[lista][q].coste);
				$("#spfz").text(obj[lista][q].fuerza);
				$("#sprz").text(obj[lista][q].raza);
				$("#sphb").text(obj[lista][q].efecto);
			}
		}
	};
	return img;
}

function createCardOp(c, context, origenPila) {
	var img = document.createElement('img');
	img.id = objOp[origenPila][c].idTemp;
	img.name = objOp[origenPila][c].numero;	
	img.src = context + "/images/thumbs/"+objOp[origenPila][c].siglas+"/" + objOp[origenPila][c].numero + ".jpg";
	if(img.id.indexOf("optc")>=0){
		img.className=objOp[origenPila][c].tipo+" oponentcard owcard";	
	}else{
		img.className=objOp[origenPila][c].tipo+" oponentcard opcard";
	}
	img.height = "70";
	img.alt=objOp[origenPila][c].siglas;
	img.onmouseover = function showImage(ev) {
		var viewCard = document.getElementById("viewCard");
		viewCard.src = context + "/images/myl/"+ev.target.alt+"/" + ev.target.name + ".jpg";
		
		var lista=ev.target.parentNode.id;
		if(lista=="dialog"){
			lista=$("#dialog").attr("name");			
		}		
		lista=lista.replace("2","1");
		
		
		for(var q=0;q<objOp[lista].length;q++){
			if(objOp[lista][q].idTemp==ev.target.id){
				$("#spnb").text(objOp[lista][q].nombre);
				$("#sptp").text(objOp[lista][q].tipo);
				$("#spfr").text(objOp[lista][q].frecuencia);
				$("#spct").text(objOp[lista][q].coste);
				$("#spfz").text(objOp[lista][q].fuerza);
				$("#sprz").text(objOp[lista][q].raza);
				$("#sphb").text(objOp[lista][q].efecto);
			}
		}
	};
	return img;
}

function createReverseCard(c, context) {
	var img = document.createElement('img');
	img.id = "card"+c;
	img.name = "card"+c;	
	img.src = context + "/images/myl/myldeck.jpg";
	img.className="reverse";	
	img.height = "70";	
	return img;
}

function dropCard(ev) {
	ev.preventDefault();
	var context = $('#hidden').val();
	var data = ev.dataTransfer.getData("Text");
		
	/**
	 * Verifica si la carta se esta moviendo hacia un div o hacia el deck, cementerio, destierro o remocion	
	 */
	if ((ev.target == "[object HTMLDivElement]" && ev.target.id.indexOf("2")==-1) || ev.target.id == "deck1"
			|| ev.target.id == "cementerio1" || ev.target.id == "destierro1"
			|| ev.target.id == "remocion1") {

		/**
		 * si la carta arrastrada no es el deck relocaliza la carta
		 */ 
		destino = ev.target.id;
		if (data != "deck1" && data != "cementerio1" && data != "destierro1" && data != "remocion1") {
			if (ev.target.id != "deck1" && ev.target.id != "cementerio1"
					&& ev.target.id != "destierro1"
					&& ev.target.id != "remocion1") {
				ev.target.appendChild(document.getElementById(data));
				changeZone(obj[origen], obj[destino], data);
				movedCard["carta"]=obj[destino][0];//
			} else if (ev.target.id == "deck1") {
				move(obj[origen], obj[destino], data);
				movedCard["carta"]=obj[destino][0];//
			} else {
				move(obj[origen], obj[destino], data);
				var c = document.getElementById(ev.target.id);
				c.src = context + "/images/myl/"+obj[destino][0].siglas+"/"+ obj[destino][0].numero + ".jpg";
				movedCard["carta"]=obj[destino][0];//
			}
			
			if(origenP=="dialog" && origen!="deck1"){
				var c = document.getElementById(origen);
				if (obj[origen].length != 0) {					
					c.src = context + "/images/myl/"+obj[origen][0].siglas+"/" + obj[origen][0].numero+ ".jpg";				
				} else {
					c.src = context + "/images/myl/" + origen + ".jpg";
				}
			}			
			
		} else if (data == "deck1") {
			/**
			 * si se arrastra desde el deck se crea una carta en la zona seleccionada
			 * en caso de cementerio, destierro o remocion coloca la imagen en la zona correspondiente
			 */
			if(obj.deck1.length!=0){
				if (destino != "cementerio1" && destino != "destierro1" && destino != "remocion1") {
					var img = createCard(0, context, "deck1");
					ev.target.appendChild(img);
				} else {
					var dest = document.getElementById(destino);
					dest.src = context + "/images/myl/"+obj["deck1"][0].siglas+"/"+ obj["deck1"][0].numero + ".jpg";
				}
				obj[destino].unshift(obj["deck1"].splice(0, 1)[0]);
				movedCard["carta"]=obj[destino][0];//
				origen="deck1";
			}else{
				msgLog("El castillo no tiene mas cartas");
				return null;				
			}
		} else {
			/**
			 * si se arrastra desde el cementerio, destierro o remocion
			 */ 
			if (obj[data].length != 0) {
				var c = document.getElementById(data);
				destino = ev.target.id;
				
				/**
				 * verifica si la carta se mueve a alguna zona que no sea pila
				 * si es alguna pila mueve la carta a la lista y dibuja la imagen
				 * si es al deck solo mueve la carta a la lista
				 */

				if (destino != "deck1" && destino != "cementerio1" && destino != "destierro1" && destino != "remocion1") {
					var img = createCard(0, context, data);
					ev.target.appendChild(img);
				} else if (destino == "cementerio1" || destino == "destierro1" || destino == "remocion1") {
					var d = document.getElementById(destino);
					d.src = c.src = context + "/images/myl/"+obj[data][0].siglas+"/"+ obj[data][0].numero + ".jpg";
				}

				obj[destino].unshift(obj[data].splice(0, 1)[0]);
				movedCard["carta"]=obj[destino][0];//
				/**
				 * actualiza la imagen a la primera carta, si está vacía coloca por default
				 */
				if (obj[data].length != 0 && data!="deck1") {
					c.src = context + "/images/myl/"+obj[data][0].siglas+"/" + obj[data][0].numero+ ".jpg";
				} else {
					c.src = context + "/images/myl/" + data + ".jpg";
				}
			}else{								
				return null;
			}
		}
		movedCard["origen"]=origen;
		movedCard["destino"]=destino;
		return movedCard;
	}else{
		if(ev.target.id.indexOf("tar")==-1 && ev.target.id!=data && ev.target != "[object HTMLDivElement]"){
			target($("#"+data), $(ev.target));
			
			var dataAux;
			var targetAux;
			
			if(data.indexOf("op")!=-1){
				dataAux=data.substring(2);
			}else{
				dataAux="op"+data;
			}
			if(ev.target.id.indexOf("op")!=-1){
				targetAux=ev.target.id.substring(2);
			}else{
				targetAux="op"+ev.target.id;
			}
			msgTarget(dataAux, targetAux);
		}
		return null;
	}
}

function randomCard(length){
	var sel=Math.floor((Math.random()*length));
	var context = $('#hidden').val();

	var divDef = document.getElementById("aux1");
	var img = createCard(sel, context, "mano1");
	
	var discard=obj["mano1"].splice(sel, 1)[0];
	$("#"+discard.idTemp).remove();
	
	divDef.appendChild(img);
	obj["aux1"].unshift(discard);	
	msgLogCard(discard, "mano1", "aux1", "Selección de carta al azar");
}

function move(arrayAux, arrayDest, data) {
	for ( var c = 0; c < arrayAux.length; c++) {
		if (arrayAux[c].idTemp == data) {
			arrayDest.unshift(arrayAux.splice(c, 1)[0]);
			$('#' + data).remove();
		}
	}
}

function changeZone(arrayAux, arrayDest, data) {
	for ( var c = 0; c < arrayAux.length; c++) {
		if (arrayAux[c].idTemp == data) {
			arrayDest.unshift(arrayAux.splice(c, 1)[0]);
		}
	}
}

function showHelp(){
	$( "#dialog-help" ).dialog({ 
		width: "70%",
		resizable: false,
		title: "Reglas",		
	});
}

function reagrupar(zonao, zonad){
	var cant=obj[zonao].length;
	for(var c=0;c<cant;c++){
		var cardAux=obj[zonao].splice(0,1)[0];
		$("#"+cardAux.idTemp).remove();
		obj[zonad].unshift(cardAux);
				
		var divdest=document.getElementById(zonad);
		var img=createCard(0, $("#hidden").val(), zonad);		
		divdest.appendChild(img);
		msgLogCard(obj[zonad][0], zonao, zonad, "Reagrupando carta");
	}
}

function target(source,target){	
	
	if(source[0].parentNode.id.indexOf("mano")==-1 || target[0].parentNode.id.indexOf("mano")==-1){
		
	var divauxorigen=$(document.createElement("div"));	
	var divauxdestino=$(document.createElement("div"));
	
	divauxorigen.attr("id",source.attr("id")+"div");
	divauxdestino.attr("id",target.attr("id")+"div");
	divauxorigen.attr("class","target");
	divauxdestino.attr("class","target");
	
	divauxorigen.appendTo(source[0].parentNode);
	divauxdestino.appendTo(target[0].parentNode);	
		
	divauxorigen.offset(source.offset());
	divauxdestino.offset(target.offset());
	
	var timerId = setInterval(function() {
	
		var orcheck=document.getElementById(source[0].id);
		var decheck=document.getElementById(target[0].id);
		
		if(orcheck==null || decheck==null || orcheck.parentNode.id.indexOf("mano")!=-1 || decheck.parentNode.id.indexOf("mano")!=-1 || orcheck.className.indexOf("forclear")!=-1 || decheck.className.indexOf("forclear")!=-1 || orcheck.parentNode.id.indexOf("dialog")!=-1 || decheck.parentNode.id.indexOf("dialog")!=-1){
			if(orcheck!=null && orcheck.className.indexOf("forclear")!=-1){
				$(orcheck).removeClass("forclear");
			}
			if(decheck!=null && decheck.className.indexOf("forclear")!=-1){
				$(decheck).removeClass("forclear");
			}
			divauxorigen.remove();
			divauxdestino.remove();
			
			clearInterval(timerId);
			return null;
		}
		
	divauxorigen.offset($(orcheck).offset());
	divauxdestino.offset($(decheck).offset());		
	divauxorigen.empty();
	
	var imageAux=source.clone();
	imageAux.attr("id",orcheck.id+"tar");
	imageAux.attr("draggable","false");
	imageAux.attr("class","animation");
	imageAux.appendTo(divauxorigen);
	
	var wrapper=imageAux
	    .wrap($('<div>').css('position','absolute').css('z-index',0))
	    .parent().parent();
	    
	wrapper.animate(divauxdestino.offset(), 1000, function() {
		divauxorigen.offset($(orcheck).offset());
	});

	}, 1000);
	
	}
}