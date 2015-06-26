function shuffle(){
	obj["deck1"].sort(function() {
		return Math.random() - 0.5;
	});
	msgLog("Barajando el mazo castillo");
}

function view(lista,context){
	$('#dialog').empty();
	var dialog=document.getElementById("dialog");	
	for(var c=0;c<obj[lista].length;c++){		
		var img = createCard(c, context, lista);			
		dialog.appendChild(img);
	}		
}

function viewop(lista,context){
	$('#dialog').empty();
	var dialog=document.getElementById("dialog");	
	for(var c=0;c<objOp[lista].length;c++){		
		var img = createCardOp(c, context, lista);			
		dialog.appendChild(img);
	}	
}

function viewx(lista,context,cant){
	cant=parseInt(cant);
	$('#dialog').empty();
	var dialog=document.getElementById("dialog");
	if(obj[lista].length<cant){
		cant=obj[lista].length;
	}
	for(var c=0;c<cant;c++){		
		var img = createCard(c, context, lista);			
		dialog.appendChild(img);
	}	
}

function dropxcards(cant){
	var context=$("#hidden").val();
	cant=parseInt(cant);
	var cantAux=cant;
	var deckLength=obj.deck1.length;
	
	if(obj["deck1"].length<cant){
		cant=obj["deck1"].length;
	}
	for(var c=0;c<cant;c++){
		obj["cementerio1"].unshift(obj["deck1"].splice(0,1)[0]);
		var count=c+1;
		if(cant>1){
			msgLogCard(obj["cementerio1"][0], "deck1", "cementerio1", "Botando carta: "+count+" de "+cant);
		}else{
			msgLogCard(obj["cementerio1"][0], "deck1", "cementerio1", "Botando carta");
		}
	}	
	var c = document.getElementById("cementerio1");
	if (obj["cementerio1"].length != 0) {
		c.src = context + "/images/myl/"+obj["cementerio1"][0].siglas+"/" + obj["cementerio1"][0].numero+ ".jpg";
	} else {
		c.src = context + "/images/myl/cementerio1.jpg";
	}
	
	if(deckLength<cantAux || obj.deck1.length==0){
		msgLog("El castillo no tiene mas cartas");		
//		msgPhase("Mi castillo no tiene mas cartas", "fendgmeovr");
//		$("#content-udis").empty();
//		$("#content-udis").append("Has sido derrotado por "+$("#user2").val()+".");
//		notifyEndGame();
//		lose=true;
	}
}

function tobottom(cardbottom,parent){
	var context=$("#hidden").val();
	var cardAux;
	if(parent=="dialog"){
		parent=$("#dialog").attr("name");		
	}else{		
	}
	
	
	for(var c=0;c<obj[parent].length;c++){
		if(obj[parent][c].idTemp==cardbottom.attr("id")){
			cardAux=obj[parent].splice(c,1)[0];
			obj["deck1"].push(cardAux);			
			cardbottom.remove();
			if(parent=="cementerio1" || parent=="destierro1" || parent=="remocion1"){
				var c = document.getElementById(parent);
				if (obj[parent].length != 0) {
					c.src = context + "/images/myl/"+obj[parent][0].siglas+"/" + obj[parent][0].numero+ ".jpg";
				} else {
					c.src = context + "/images/myl/" + parent + ".jpg";
				}
			}
		}
	}	
	msgLogCard(cardAux, parent, "deck1", "Moviendo carta al fondo del castillo");	
}

function show(lista,msg){
	var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;
	wsclient.toChatCards(from, to, msg, obj[lista], lista);
}

function msgLogCard(cardAux,origen,destino,msg){
	var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;         
    movedCard["origen"]=origen;
    movedCard["destino"]=destino;
    wsclient.toChatCard(from, to, msg, cardAux, movedCard["origen"], movedCard["destino"]);
}

function msgLog(msg){
	var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;
    wsclient.toChatCard(from, to, msg, null, null, null);
}

function msgTarget(origen,destino){
	var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;
    wsclient.toChatTarget(from,to,"",origen,destino);
}

function msgTargetClean(targetclear){
	var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;
    wsclient.toChatTarget(from,to,"clean",targetclear,null);
}

function msgPhase(msg,phase){
	var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;
    wsclient.toChatPhase(from,to,msg,phase);
}