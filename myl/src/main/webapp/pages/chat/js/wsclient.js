var win=false;
var lose=false;
var showmsg=true;
var id;
	
var wsclient = (function() {

	var prevGame=0;
	var gameAccept=0;
	var gameReject=0;
	var dragOrigin;
	
	
    var ws = null;
//    var wsURI = 'ws://' + location.host  + '/myl/lobbyws';
    var wsURI = 'ws://' + location.host  + '/myl/chatws';
    function connect(userName,userNameTwo) {    	
    	
        if(!userName || userName == '') {
            return;
        }

        if ('WebSocket' in window) {
//            ws = new WebSocket(wsURI + '?userName=' + userName +'&userNameTwo=' + userNameTwo);
            ws = new WebSocket(wsURI);
        } else if ('MozWebSocket' in window) {
//            ws = new MozWebSocket(wsURI + '?userName=' + userName +'&userNameTwo=' + userNameTwo);
            ws = new MozWebSocket(wsURI);
        } else {
            alert('Tu navegador no soporta WebSockets');
            return;
        }
        ws.onopen = function () {
        	bloquearUI();
        	toChatSession(userName, userNameTwo, "OPONENT");
            setConnected(true);
            
            $("#totalp1").text("50");
            $("#totalp2").text("50");
        };
        ws.onmessage = function (event) {
            var message = JSON.parse(event.data);
            processMessage(message);
        };

        ws.onerror=function(event){
        	console.log(event.data);
        };
        
        ws.onclose = function () {
            setConnected(false);
            document.getElementById('userName').value = '';
            closeAllConversations();
        };

        function processMessage(message) {
            if (message.messageInfo) {
                showConversation(message.messageInfo.from);
                if(message.messageInfo.message.indexOf("gamereado")==-1 && message.messageInfo.message.indexOf("gamereadiok")==-1 && message.messageInfo.message!="gamereadyaccept" && message.messageInfo.message!="gamereadyreject"){
                	addMessage(message.messageInfo.from, message.messageInfo.message, cleanWhitespaces(message.messageInfo.from) + 'conversation');
                }else if(message.messageInfo.message.indexOf("gamereado")>=0){
                	if(prevGame==0){
                		toChat(document.getElementById("user1").value, document.getElementById("user2").value, "gamereadiok"+$("#key").val());
                		showConversation(document.getElementById("user2").value);
                		id=message.messageInfo.message.substring(9);
                		sendKey();
                		$.unblockUI();
                	}else{
                		id=message.messageInfo.message.substring(9);
                	}
                }else if(message.messageInfo.message.indexOf("gamereadiok")>=0){
                	showConversation(document.getElementById("user2").value);
                	id=message.messageInfo.message.substring(11);
                	sendKey();                	
                	$.unblockUI();
                }else if(message.messageInfo.message=="gamereadyaccept"){
                	gameAccept=1;
                }else if(message.messageInfo.message=="gamereadyreject"){
                	gameReject=1;
                }
                
            } else if (message.statusInfo) {
                if (message.statusInfo.status == 'CONNECTED') {
                    addOnlineUser(message.statusInfo.user);                    
                    if(prevGame==1){
                    	$( "#dialog-udis" ).dialog("close");
                    	if(gameAccept==1){
                    		toChat(document.getElementById("user1").value, document.getElementById("user2").value, "gamereadiok"+$("#key").val());
                    		$("#content-udis").empty();
                    		$("#content-udis").append("El usuario "+message.statusInfo.user+" ha aceptado la nueva partida.");
                            notifyUserDisconnected();
                    		gameAccept=0;                    		
                    	}else{
                    		$("#content-newg").empty();
                    		$("#content-newg").append("El usuario "+message.statusInfo.user+" quiere iniciar una nueva partida.");                    	
                    		notifyNewGame();                    	
                    	}
                    }
                    
                } else if (message.statusInfo.status == 'DISCONNECTED') {
                	removeOnlineUser(message.statusInfo.user);
                    $("#content-udis").empty();
                    $("#content-udis").append("El usuario "+message.statusInfo.user+" ha salido de la partida");
                	if(gameReject==1){
                		$("#content-udis").empty();
                		$("#content-udis").append("El usuario "+message.statusInfo.user+" ha rechazado de la partida");
                    	showmsg=false;
                    	$.unblockUI();
                    }else if(gameReject==0 && win==false && lose==false && gameAccept==0){
                    	$("#content-udis").empty();
                		$("#content-udis").append("El usuario "+message.statusInfo.user+" ha salido de la partida. Has ganado.");
                    	test();                    	
                    }                	
                	
                    notifyUserDisconnected();                    
                    prevGame=1;
                    
                }
            } else if (message.connectionInfo) {
                var activeUsers = message.connectionInfo.activeUsers;
                for (var i=0; i<activeUsers.length; i++) {
                    addOnlineUser(activeUsers[i]);
                }
            } else if (message.cardInfo){
            	addMessageCard(message.cardInfo.from, message.cardInfo.message, cleanWhitespaces(message.cardInfo.from) + 'conversation');            	
            	if(message.cardInfo.origen!=null && message.cardInfo.destino!=null){
            		processCard(message.cardInfo.from, message.cardInfo.message, message.cardInfo.carta,message.cardInfo.origen,message.cardInfo.destino);
            		// implementar el conteo del deck oponente
            	}
            } else if (message.cardListInfo){
            	addMessageCard(message.cardListInfo.from, message.cardListInfo.message, cleanWhitespaces(message.cardListInfo.from) + 'conversation');            	
            	processCards(message.cardListInfo.from, message.cardListInfo.message, message.cardListInfo.cartas,message.cardListInfo.origen);            	
            } else if (message.targetInfo){
            	if(message.targetInfo.message=="clean"){
            		var card=$("#"+message.targetInfo.origen);
                	card.attr("class",card.attr("class")+" forclear");
            	}else{
            		target($("#"+message.targetInfo.origen), $("#"+message.targetInfo.destino));
            	}
            } else if(message.phaseInfo){
            	addMessagePhase(message.phaseInfo.from, message.phaseInfo.message, cleanWhitespaces(message.phaseInfo.from) + 'conversation');
            	if(message.phaseInfo.phase!="dado" && message.phaseInfo.phase!="fendgmeovr"){
            		disablePhases();
            		$("#"+message.phaseInfo.phase).attr("class","faseActive");
            	}else if(message.phaseInfo.phase=="fendgmeovr"){
            		$("#content-udis").empty();
            		$("#content-udis").append("¡Felicidades!, has derrotado a "+message.phaseInfo.from+".");
            		if(win==false){
            			test();
            		}
            	}
            }
        }
    }

    function disconnect() {
        if (ws != null) {
            ws.close();
            ws = null;
        }
        setConnected(false);
    }

    function setConnected(connected) {
        cleanConnectedUsers();
        if (connected) {
            updateUserConnected();
        } else {
            updateUserDisconnected();
        }
    }

    function updateUserConnected() {
        var inputUsername = $('#userName');
        $('#onLineUsersPanel').css({visibility:'visible'});
        
        toChat(document.getElementById("user1").value, document.getElementById("user2").value, "gamereado"+$("#key").val());
    }

    function updateUserDisconnected() {
//        $('.onLineUserName').css({visibility:'hidden'});
//        $('#userName').css({display:''});
//        $('#status').html('Desconectado');
//        $('#status').attr({class : 'disconnected'});
//        $('#onLineUsersPanel').css({visibility:'hidden'});
    }

    function cleanConnectedUsers() {
        $('#onlineUsers').html('');
    }

    function removeTab(conversationId) {
        $('#conversations').tabs('remove', conversationId);
    }

    function cleanWhitespaces(text) {
        return text.replace(/\s/g,"_");
    }

    function showConversation(from) {
        var conversations = $('#conversations');
        conversations.css({visibility:'visible'});
        var conversationId = cleanWhitespaces(from) + 'conversation';
        if(document.getElementById(conversationId) == null) {
            createConversationPanel(from);
            conversations.tabs('add', '#' + conversationId, from);
        }
        conversations.tabs('select', '#' + conversationId);
        $('#'+conversationId+'message').focus();
    }

    function createConversationPanel(name) {
        var conversationId = cleanWhitespaces(name) + 'conversation';
        var conversationPanel = $(document.createElement('div'));
        conversationPanel.attr({id : conversationId, class : 'conversation'});
        $('<p class="messages"></p><textarea id="' + conversationId + 'message" rows="3"></textarea>').appendTo(conversationPanel);
        var sendButton = createSendButton(name);
        sendButton.appendTo(conversationPanel);
        var dado = createDadoButton(name);
        dado.appendTo(conversationPanel);
        var wait = createWaitButton();
        wait.appendTo(conversationPanel);
        var think = createThinkButton();
        think.appendTo(conversationPanel);
        conversationPanel.appendTo($('#conversations'));
        
        $("#"+conversationId + "message").keyup(function(event){
            if(event.keyCode == 13){
            	sendButton.click();
            }
        });
    }

    function createSendButton(name) {
        var conversationId = cleanWhitespaces(name) + 'conversation';
        var button = $(document.createElement('button'));
        button.html('Enviar');
        button.click(function () {
            var from = document.getElementById('user1').value;
            var message = document.getElementById(conversationId+'message').value;
            toChat(from, name, message);
            addMessage(from, message, conversationId);
            document.getElementById(conversationId+'message').value = '';
        });        
        return button;
    }
    
    function createDadoButton(name) {
        var button = $(document.createElement('button'));
        button.html('Lanzar dado');
        button.click(function () {
        	var num=Math.floor((Math.random()*6))+1;
        	msgPhase(num+" obtenido", "dado");
        });        
        return button;
    }
    
    function createWaitButton() {
        var button = $(document.createElement('button'));
        button.html('Espera!');
        button.click(function () {
        	msgPhase("¡ESPERA!", "dado");
        });        
        return button;
    }
    
    function createThinkButton() {
        var button = $(document.createElement('button'));
        button.html('Pensando...');
        button.click(function () {
        	msgPhase("Pensando...", "dado");
        });        
        return button;
    }

    function closeAllConversations() {
        for (var i = $('#conversations').tabs('length'); i >= 0; i--) {
            $('#conversations').tabs('remove', i-1);
        }
        $('#conversations').css({visibility : 'hidden'});
    }

    function createCloseButton(conversationId) {
        var button = $(document.createElement('button'));
        button.html('Cerrar');
        button.click(function () {
            removeTab(conversationId);
        });
        return button;
    }

    function addMessage (from, message, conversationPanelId) {
        var messages = $('#' + conversationPanelId + ' .messages');
        $('<div class="message"><span><b>' + from + '</b> dice:</span><p>' + $('<p/>').text(message).html() + '</p></div>').appendTo(messages);
        messages.scrollTop(messages[0].scrollHeight);
        $('#'+conversationPanelId+' textarea').focus();
    }
    
    function addMessageCard(from, message, conversationPanelId) {
        var messages = $('#' + conversationPanelId + ' .messages');
        if(from===$("#userNameTwo").val()){
        	$('<div class="messagepl2"><b>'+from+":</b> "+message+'</div>').appendTo(messages);
        }else{
        	$('<div class="messagepl"><b>'+from+":</b> "+message+'</div>').appendTo(messages);
        }
        messages.scrollTop(messages[0].scrollHeight);
        $('#'+conversationPanelId+' textarea').focus();
    }
    
    function addMessagePhase(from, message, conversationPanelId) {
        var messages = $('#' + conversationPanelId + ' .messages');
        $('<div class="messageph"><b>'+from+":</b> "+message+'</div>').appendTo(messages);
        messages.scrollTop(messages[0].scrollHeight);
        $('#'+conversationPanelId+' textarea').focus();
    }
    

    function toChat(sender, receiver, message) {    	
        ws.send(JSON.stringify({messageInfo : {from : sender, to : receiver, message : message}}));
    }
    
    function toChatSession(user, formatOrUserTwo, type) {
        ws.send(JSON.stringify({sessionInfo : {user : user, formatOrUserTwo : formatOrUserTwo, type : type}}));
    }
    
    function toChatCard(sender, receiver, message, card, origen, destino) {
    	var conversationId = cleanWhitespaces(receiver) + 'conversation';
    	addMessageCard(sender, message, conversationId);
        ws.send(JSON.stringify({cardInfo : {from : sender, to : receiver, message : message, carta : card, origen: origen, destino : destino}}));
                
        //Implementar el conteo de cartas
        deckCounter(origen, destino, "#totalp1");
    }
    
    function toChatCards(sender, receiver, message, cards, origen) {
    	var conversationId = cleanWhitespaces(receiver) + 'conversation';
    	addMessageCard(sender, message, conversationId);
        ws.send(JSON.stringify({cardListInfo : {from : sender, to : receiver, message : message, cartas : cards, origen: origen}}));
    }
    
    function toChatTarget(sender, receiver, message, origen, destino){
    	ws.send(JSON.stringify({targetInfo : {from : sender, to : receiver, message : message, origen: origen, destino: destino}}));
    }
    
    function toChatPhase(sender, receiver, message,phase) {
    	var conversationId = cleanWhitespaces(receiver) + 'conversation';
    	addMessagePhase(sender, message, conversationId);
        ws.send(JSON.stringify({phaseInfo : {from : sender, to : receiver, message : message, phase: phase}}));
    }

    /********* usuarios conectados *******/
    function addOnlineUser(userName) {
        var newOnlineUser = createOnlineUser(userName);
        newOnlineUser.appendTo($('#onlineUsers'));
        
    }

    function removeOnlineUser(userName) {
        $('#onlineUsers > li').each(function (index, elem) {
            if (elem.id == userName + 'onlineuser') {
                $(elem).remove();
            }
        });
    }

    function createOnlineUser(userName) {
        var link = $(document.createElement('a'));
        link.html(userName);
        link.click(function(){
        	document.getElementById("user2").value=userName;
            showConversation(userName);
            
        });
        var li = $(document.createElement('li'));
        li.attr({id : (userName + 'onlineuser')});
        link.appendTo(li);
        return li;
    }
    
    function allowDrop(ev)
    {
    ev.preventDefault();
    }

    function drag(ev)
    {    	
    origen = ev.target.id;
    ev.dataTransfer.setData("Text",ev.target.id);    
    }
       
    function drop(ev)
    {       	
    var card=dropCard(ev);

    if(card!=null){    	
    	var from=document.getElementById("user1").value;
    	var to=document.getElementById("user2").value;
    	var data = ev.dataTransfer.getData("Text");
    	var origenMsg=origen.replace("1","");
    	var destinoMsg=ev.target.id.replace("1","");
    	
    	var msg="Moviendo carta desde: <b>"+origenMsg.replace("deck","Mazo")+"</b> hacia: <b>"+destinoMsg.replace("deck","Mazo")+"</b>";   
    	toChatCard(from, to, msg, card["carta"], card["origen"], card["destino"] );
    }
    	
    }
    
    
    // metodos publicos
    return {
        connect : connect,
        disconnect : disconnect,
        toChat: toChat,
        toChatCard: toChatCard,
        toChatCards: toChatCards,
        toChatTarget: toChatTarget,
        toChatPhase: toChatPhase,
        addMessage: addMessage,
        allowDrop : allowDrop,
        drag : drag,
        drop : drop
    };
   
    //prueba
    //Otra prueba

})();

function processCard(from,message,card,origen,destino){
	
	deckCounter(origen, destino, "#totalp2");	
	
	var context=$('#hidden').val();
	if(origen!="deck1" && destino!="mano1"){
		card.idTemp="op"+card.idTemp;		
	}else if(origen=="deck1" && destino!="mano1"){
		card.idTemp="op"+card.idTemp;		
	}else if(origen!="deck1" && destino=="mano1"){
		card.idTemp="op"+card.idTemp;		
	}
	
	//si el destino es el campo
	if(destino!="deck1" && destino!="mano1" && destino!="cementerio1" && destino!="destierro1" && destino!="remocion1"){
		// si el origen es desde el campo		
		if(origen!="mano1" && origen!="deck1" && origen!="cementerio1" && origen!="destierro1" && origen!="remocion1" && origen.indexOf(2)==-1){			
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){
					objOp[origen].splice(c,1);					
					$("#"+card.idTemp).remove();
				}
			}
		}else if(origen=="cementerio1" || origen=="destierro1" || origen=="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){
					objOp[origen].splice(c,1);					
				}
			}
			
			var d = document.getElementById(origen.replace("1","2"));
			if (objOp[origen].length != 0) {
				d.src = context + "/images/myl/"+objOp[origen][0].siglas+"/" + objOp[origen][0].numero+ ".jpg";
			} else {
				d.src = context + "/images/myl/" + origen + ".jpg";
			}
		}else if(origen=="mano1"){
			var divMano=document.getElementById(origen.replace("1","2"));
			var n=divMano.childNodes.length-1;
			$("#card"+n).remove();
		}else if(origen.indexOf("2")!=-1){
			/**
			 * cambia la carta de propietario
			 */
			for(var c=0;c<obj[origen.replace("2","1")].length;c++){
				var idTempAux=card.idTemp.substring(4);
				if(card.idTemp.indexOf("optc")===-1){
					idTempAux="tc"+card.idTemp.substring(2);
				}				
				if(obj[origen.replace("2","1")][c].idTemp==idTempAux){
					obj[origen.replace("2","1")].splice(c,1);
					$("#"+idTempAux).remove();
				}
			}
			
			if(origen=="cementerio2" || origen=="destierro2" || origen=="remocion2"){
				origen=origen.replace("2","1");
				var d = document.getElementById(origen);
				if (obj[origen].length != 0) {
					d.src = context + "/images/myl/"+obj[origen][0].siglas+"/" + obj[origen][0].numero+ ".jpg";
				} else {
					d.src = context + "/images/myl/" + origen + ".jpg";
				}
			}
		}
		
		
		objOp[destino].unshift(card);
		var img=createCardOp(0,context,destino);
		var divDest=document.getElementById(destino.replace("1","2"));
		divDest.appendChild(img);
		
	}else if(destino=="cementerio1" || destino=="destierro1" || destino=="remocion1"){
				
		if(origen!="mano1" && origen!="deck1" && origen!="cementerio1" && origen!="destierro1" && origen!="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){
					objOp[origen].splice(c,1);					
				}
			}
			$("#"+card.idTemp).remove();
		}else if(origen=="cementerio1" || origen=="destierro1" || origen=="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){					
					objOp[origen].splice(c,1);					
				}
			}
			var ori = document.getElementById(origen.replace("1","2"));
			if (objOp[origen].length != 0) {
				ori.src = context + "/images/myl/"+objOp[origen][0].siglas+"/" + objOp[origen][0].numero+ ".jpg";
			} else {
				ori.src = context + "/images/myl/" + origen + ".jpg";
			}
		}else if(origen=="mano1"){
			var divMano=document.getElementById(origen.replace("1","2"));
			var n=divMano.childNodes.length-1;
			$("#card"+n).remove();
		}
		
		objOp[destino].unshift(card);
		var dest = document.getElementById(destino.replace("1","2"));
		dest.src = context + "/images/myl/"+objOp[destino][0].siglas+"/"+ objOp[destino][0].numero + ".jpg";
		
		
	}else if(destino=="mano1"){
		if(origen!="mano1" && origen!="deck1" && origen!="cementerio1" && origen!="destierro1" && origen!="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){
					objOp[origen].splice(c,1);					
				}
			}
			$("#"+card.idTemp).remove();
			var divMano=document.getElementById(destino.replace("1","2"));
			divMano.appendChild(createReverseCard(divMano.childNodes.length,context));
			
		}else if(origen=="cementerio1" || origen=="destierro1" || origen=="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){					
					objOp[origen].splice(c,1);					
				}
			}
			var ori = document.getElementById(origen.replace("1","2"));
			if (objOp[origen].length != 0) {
				ori.src = context + "/images/myl/"+objOp[origen][0].siglas+"/" + objOp[origen][0].numero+ ".jpg";
			} else {
				ori.src = context + "/images/myl/" + origen + ".jpg";
			}
			var divMano=document.getElementById(destino.replace("1","2"));
			divMano.appendChild(createReverseCard(divMano.childNodes.length,context));
		}else if(origen=="deck1"){
			var divMano=document.getElementById(destino.replace("1","2"));
			divMano.appendChild(createReverseCard(divMano.childNodes.length,context));
		}
		
		
	}else if(destino=="deck1"){
		if(origen!="mano1" && origen!="deck1" && origen!="cementerio1" && origen!="destierro1" && origen!="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){			
					objOp[origen].splice(c,1);					
				}
			}
			$("#"+card.idTemp).remove();
		}else if(origen=="cementerio1" || origen=="destierro1" || origen=="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp==card.idTemp){			
					objOp[origen].splice(c,1);					
				}
			}
			var ori = document.getElementById(origen.replace("1","2"));
			if (objOp[origen].length != 0) {
				ori.src = context + "/images/myl/"+objOp[origen][0].siglas+"/" + objOp[origen][0].numero+ ".jpg";
			} else {
				ori.src = context + "/images/myl/" + origen + ".jpg";
			}			
		}else if(origen=="mano1"){
			var divMano=document.getElementById(origen.replace("1","2"));
			var n=divMano.childNodes.length-1;
			$("#card"+n).remove();
		}				
	}
}

function processCards(from,message,cards,origen){
	objOp[origen]=cards;

	for(var c=0;c<objOp[origen].length;c++){
		objOp[origen][c].idTemp="op"+objOp[origen][c].idTemp;
	}
	
	viewop(origen,$('#hidden').val());
	$("#dialog").attr("name",origen.replace("1","2"));	
	$( "#dialog" ).dialog({ 
		width: 500,
		title: origen.replace("1","")+" oponente",
		close: function( event, ui ) {
			objOp[origen]=[];
			msgLog("Dejó de ver "+origen.replace("1","")+" oponente");
		}
	});	
}

function deckCounter(origen,destino,player){
	var c=parseInt($(player).text());
	if(origen=="deck1" && destino!="deck1"){
		c=c-1;		
	}else if(origen!="deck1" && destino=="deck1"){
		c=c+1;		
	}
	$(player).text(c);
	
	if(player==="#totalp1" && c===0 && lose===false && win!=true){
		msgPhase("Mi castillo no tiene mas cartas", "fendgmeovr");
		$("#content-udis").empty();
		$("#content-udis").append("Has sido derrotado por "+$("#user2").val()+".");
		notifyEndGame();
		lose=true;
	}
}