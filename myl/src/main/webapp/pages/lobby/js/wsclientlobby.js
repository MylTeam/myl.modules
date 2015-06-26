var wsclient = (function() {

    var ws = null;
    var wsURI = 'ws://' + location.host  + '/myl/lobbyws';
    function connect(userName,format) {    	
    	
        if(!userName || userName == '') {
            return;
        }

        if ('WebSocket' in window) {
//            ws = new WebSocket(wsURI + '?userName=' + userName+"&format="+format);
            ws = new WebSocket(wsURI);
        } else if ('MozWebSocket' in window) {
//            ws = new MozWebSocket(wsURI + '?userName=' + userName+"&format="+format);
            ws = new MozWebSocket(wsURI);
        } else {
            alert('Tu navegador no soporta WebSockets');
            return;
        }
        ws.onopen = function () {
        	toChatSession(userName, format, "FORMAT");
            setConnected(true);
            showConversation("Sala_Myl");
            
        };
        ws.onmessage = function (event) {
            var message = JSON.parse(event.data);
            processMessage(message);
        };

        ws.onclose = function () {
            setConnected(false);
            document.getElementById('userName').value = '';
            closeAllConversations();
        };

        function processMessage(message) {        	
            if (message.messageInfo) {
                showConversation(message.messageInfo.from);
                if(message.messageInfo.message!="plduelojsp" && message.messageInfo.message!="plduelojspresp" && message.messageInfo.message.indexOf("gamereado")==-1 && message.messageInfo.message.indexOf("gamereadiok")==-1 && message.messageInfo.message!="gamereadyaccept" && message.messageInfo.message!="gamereadyreject"){
                	addMessage(message.messageInfo.from, message.messageInfo.message, cleanWhitespaces(message.messageInfo.from) + 'conversation');
                }else if(message.messageInfo.message=="plduelojsp"){
                	addMessageButton(message.messageInfo.from, message.messageInfo.message, cleanWhitespaces(message.messageInfo.from) + 'conversation');
                }else if(message.messageInfo.message=="plduelojspresp"){
                	var user = document.getElementById('userName').value;
                	var url="http://"+location.host+$("#hidden").val()+"/chat?user1="+user+"&user2="+message.messageInfo.from;
                	window.location=url;
//                	window.open(url,"Duelo","height=" + screen.height + "-200,width=" + screen.width+"-200");
                }
            } else if (message.statusInfo) {
                if (message.statusInfo.status == 'CONNECTED') {
                    addOnlineUser(message.statusInfo.user,message.statusInfo.format);
                } else if (message.statusInfo.status == 'DISCONNECTED') {
                    removeOnlineUser(message.statusInfo.user);
                }
            } else if (message.connectionInfo) {
                var activeUsers = message.connectionInfo.activeUsers;
                var formats = message.connectionInfo.formats;
                for (var i=0; i<activeUsers.length; i++) {
                    addOnlineUser(activeUsers[i],formats[i]);
                }
            } else if(message.chatInfo){
            	addMessage(message.chatInfo.from, message.chatInfo.message, 'Sala_Mylconversation');
            } else if (message.cardInfo){
            	processCard(message.cardInfo.from, message.cardInfo.message, message.cardInfo.carta,message.cardInfo.origen,message.cardInfo.destino);
            }
        }
    }

    function disconnect() {
        if (ws != null) {
        	alert("onDisconnect");
            ws.close();
            ws = null;
        }
        setConnected(false);
    }

    function setConnected(connected) {
//        document.getElementById('connect').disabled = connected;
//        document.getElementById('disconnect').disabled = !connected;
        cleanConnectedUsers();
        if (connected) {
            updateUserConnected();
        } else {
            updateUserDisconnected();
        }
    }

    function updateUserConnected() {
        var inputUsername = $('#userName');
        var onLineUserName = $('.onLineUserName');
        onLineUserName.html(inputUsername.val());
        inputUsername.css({display:'none'});
        onLineUserName.css({visibility:'visible'});
        $('#status').html('Conectado');
        $('#status').attr({class : 'connected'});
        $('#onLineUsersPanel').css({visibility:'visible'});
    }

    function updateUserDisconnected() {
        $('.onLineUserName').css({visibility:'hidden'});
        $('#userName').css({display:''});
        $('#status').html('Desconectado');
        $('#status').attr({class : 'disconnected'});
        $('#onLineUsersPanel').css({visibility:'hidden'});
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
        $('<p class="messageslobby"></p><textarea id="' + conversationId + 'message"></textarea>').appendTo(conversationPanel);
        var sendButton = createSendButton(name);
        sendButton.appendTo(conversationPanel);
        if(name!="Sala_Myl"){
        	var closeButton = createCloseButton(cleanWhitespaces(name));
        	closeButton.appendTo(conversationPanel);
        	var playButton = createPlayButton(cleanWhitespaces(name));
        	playButton.appendTo(conversationPanel);        	
        }
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
            var from = document.getElementById('userName').value;
            var message = document.getElementById(conversationId+'message').value;
            if(name=="Sala_Myl"){
            	toChatAll(from, message);
            }else{
            	toChat(from, name, message);
            }
            addMessage(from, message, conversationId);
            document.getElementById(conversationId+'message').value = '';
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
    
    function createPlayButton(name) {
    	var conversationId = cleanWhitespaces(name) + 'conversation';    	
        var button = $(document.createElement('button'));
        button.html('Duelo');
        button.click(function () {
        	
        	var from = document.getElementById('userName').value;
            var message = from+" quiere jugar.";
            toChat(from, name, message);
            addMessage(from, message, conversationId);            
            document.getElementById(conversationId+'message').value = '';
            message = "plduelojsp";
            toChat(from, name, message);
        });
        return button;
    }

    function addMessage (from, message, conversationPanelId) {
        var messages = $('#' + conversationPanelId + ' .messageslobby');
        if(conversationPanelId=="Sala_Mylconversation"){
        	$('<div class="message"><span><b>' + from + '</b> dice: </span>'+message+'</div>').appendTo(messages);
        }else{        
        	$('<div class="message"><span><b>' + from + '</b> dice:</span><p>' + $('<p/>').text(message).html() + '</p></div>').appendTo(messages);
        }
        messages.scrollTop(messages[0].scrollHeight);
        $('#'+conversationPanelId+' textarea').focus();
    }
    
    function addMessageButton (from, message, conversationPanelId) {
        var messages = $('#' + conversationPanelId + ' .messageslobby');
        var btn=$(document.createElement('button'));
        btn.html('Aceptar');
        btn.click(function () { 
        	var user = document.getElementById('userName').value;
        	var url="http://"+location.host+$("#hidden").val()+"/chat?user1="+user+"&user2="+from;
        	var message = "plduelojspresp";
            toChat(user, from, message);
            bloquearUI();
            setTimeout(function() {window.location=url;}, 2000);
//            window.open(url,"Duelo","height=" + screen.height + "-200,width=" + screen.width+"-200");
        });
        btn.appendTo(messages);
        messages.scrollTop(messages[0].scrollHeight);
        $('#'+conversationPanelId+' textarea').focus();
    }

    function toChat(sender, receiver, message) {
        ws.send(JSON.stringify({messageInfo : {from : sender, to : receiver, message : message}}));
    }
    
    function toChatSession(user, formatOrUserTwo, type) {
        ws.send(JSON.stringify({sessionInfo : {user : user, formatOrUserTwo : formatOrUserTwo, type : type}}));
    }
    
    function toChatAll(sender, message) {
        ws.send(JSON.stringify({chatInfo : {from : sender, message : message}}));
    }
    
    function toChatCard(sender, receiver, message, card, origen, destino) {
        ws.send(JSON.stringify({cardInfo : {from : sender, to : receiver, message : message, carta : card, origen: origen, destino : destino}}));
    }

    /********* usuarios conectados *******/
    function addOnlineUser(userName,format) {
        var newOnlineUser = createOnlineUser(userName,format);        
        newOnlineUser.appendTo($('#onlineUsers'));        
    }

    function removeOnlineUser(userName) {
    	var conversationId = cleanWhitespaces(userName) + 'conversation';
        $('#onlineUsers > li').each(function (index, elem) {
            if (elem.id == userName + 'onlineuser') {
                $(elem).remove();
            }
        });
        if ($("#"+conversationId).length > 0){
        	  removeTab(conversationId);
        	}
    }

    function createOnlineUser(userName,format) {
        var link = $(document.createElement('a'));
        link.html(userName+'<div style="color:#0000FF">F: '+format+'</div>');        
        link.click(function(){
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
    var from=document.getElementById("userName").value;
    var to=document.getElementById("user2").value;
    var data = ev.dataTransfer.getData("Text");
    var msg="Estoy moviendo "+data+" hacia "+ev.target.id;
    
    toChatCard(from, to, msg, card["carta"], card["origen"], card["destino"] );
    }
    	
    }
    
    
    // metodos publicos
    return {
        connect : connect,
        disconnect : disconnect,
        toChat: toChat,
        toChatAll: toChatAll,
        toChatCard: toChatCard,
        addMessage: addMessage,
        allowDrop : allowDrop,
        drag : drag,
        drop : drop
    };
   
    //prueba
    //Otra prueba

})();

function processCard(from,message,card,origen,destino){
	var context=$('#hidden').val();
	
	//si el destino es el campo
	if(destino!="deck1" && destino!="mano1" && destino!="cementerio1" && destino!="destierro1" && destino!="remocion1"){
		// si el origen es desde el campo		
		if(origen!="mano1" && origen!="deck1" && origen!="cementerio1" && origen!="destierro1" && origen!="remocion1"){			
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp=card.idTemp){
					objOp[origen].splice(c,1);					
					$("#"+card.idTemp).remove();
				}
			}
		}else if(origen=="cementerio1" || origen=="destierro1" || origen=="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp=card.idTemp){
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
		}
		
		objOp[destino].unshift(card);
		var img=createCardOp(0,context,destino);
		var divDest=document.getElementById(destino.replace("1","2"));
		divDest.appendChild(img);
		
	}else if(destino=="cementerio1" || destino=="destierro1" || destino=="remocion1"){
				
		if(origen!="mano1" && origen!="deck1" && origen!="cementerio1" && origen!="destierro1" && origen!="remocion1"){
			for(var c=0;c<objOp[origen].length;c++){
				if(objOp[origen][c].idTemp=card.idTemp){
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
				if(objOp[origen][c].idTemp=card.idTemp){
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
			$("#"+card.idTemp).remove()
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