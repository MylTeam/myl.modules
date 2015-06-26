$(function(){
    $.contextMenu({
        selector: '.deck', 
        callback: function(key, options) {
        	
            switch(key){
            case "draw":
            	drawCard();
            	break;
            case "drawHand":
            	drawHand($("#hidden").val());
            	break;
            case "view":
            	$("#dialog").attr("name","deck1");
            	view("deck1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Castillo",
            		close: function( event, ui ) {
            			msgLog("Dejó de ver el mazo castillo");
            		},
            		open: function(event,ui){
            			msgLog("Viendo mazo castillo");
            		}
            	});
            	break;            
            case "dropx":
            	$("#cantidad").val("0");
            	$("#dialog-cant").dialog({
            			width: 300,
            			title: "Botar",
            			buttons: {
            				"Aceptar":function(){            					
            					dropxcards($("#cantidad").val());            					
            					$( this ).dialog( "close" );            					
            				},
            				"Cancelar":function() {
            			          $( this ).dialog( "close" );
            		        }
            			}
            	});
            	break;
            case "drop":
            	dropxcards(1);
            	break;
            case "viewx":
            	$("#cantidad").val("0");
            	$("#dialog-cant").dialog({
            			width: 300,
            			title: "Ver",
            			buttons: {
            				"Aceptar":function(){
            					$( this ).dialog( "close" );
            					viewx("deck1",$('#hidden').val(),$("#cantidad").val());
            					$( "#dialog" ).dialog({             						
            						width: 500,
            						title: "Primeras "+$("#cantidad").val()+" cartas",
            						close: function( event, ui ) {
                            			msgLog("Dejó de ver el mazo castillo");
                            		},
                            		open: function(){
                            			msgLog("Viendo "+$("#cantidad").val()+" cartas del mazo castillo");
                            		}});
            				},
            				"Cancelar":function() {
            			          $( this ).dialog( "close" );
            		        }
            			}
            			
            	});
            	break;
            case "shuffle":
            		shuffle();
            		
            	break;
            case "show":
        		show("deck1", "Mostrando deck");
        	break;
            }
        },
        items: {
            "draw": {name: "Robar carta (shift+d)"},
            "drawHand": {name: "Robar mano"},
            "view": {name: "Ver castillo"},
            "viewx": {name: "Ver X"},
            "drop": {name: "Botar carta (shift+b)"},
            "dropx": {name: "Botar X"},
            "shuffle": {name: "Barajar (shift+s)"},
            "show": {name: "Mostrar al oponente"}
        }
    });
    
//    $('.deck').on('click', function(e){    	
//    })       
});

$(function(){
    $.contextMenu({
        selector: '.cementerio', 
        callback: function(key, options) {
        	
            switch(key){            
            case "view":            	
            	$("#dialog").attr("name","cementerio1");
            	view("cementerio1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Cementerio",
            		close: function( event, ui ) {},
            		open:function(){}
            		});
            	break;
            }
        },
        items: {                       
            "view": {name: "Ver cementerio"}            
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '.destierro', 
        callback: function(key, options) {
        	
            switch(key){            
            case "view":            	
            	$("#dialog").attr("name","destierro1");
            	view("destierro1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Destierro",
            		close: function( event, ui ) {},
            		open:function(){}
            		});
            	break;
            }
        },
        items: {                       
            "view": {name: "Ver destierro"}            
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '.remocion', 
        callback: function(key, options) {
        	
            switch(key){            
            case "view":
            	$("#dialog").attr("name","remocion1");
            	view("remocion1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Cartas removidas",
            		close: function( event, ui ) {},
            		open:function(){}
            		});
            	break;
            }
        },
        items: {                       
            "view": {name: "Ver cartas removidas"}            
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '.cardright',        
        callback: function(key, options) {
        	
            switch(key){            
            case "bottom":
            	var cardbottom=$("#"+options.$trigger.context.id);
            	var parent=cardbottom.parent().attr("id");            	
            	tobottom(cardbottom, parent);            	            	
            	break;
            case "clean":
            	var card=$("#"+options.$trigger.context.id);
            	if(document.getElementById(card.attr("id")+"div")!=null){
            		card.attr("class",card.attr("class")+" forclear");
            		var dataAux;    			    			
            		if(card.attr("id").indexOf("op")!=-1){
            			dataAux=card.attr("id").substring(2);
            		}else{
            			dataAux="op"+card.attr("id");
            		}
            		msgTargetClean(dataAux);
            	}
            	break;
            }
        },
        items: {                       
            "bottom": {name: "Enviar al fondo del castillo."},
        	"clean": {name: "Limpiar objetivos."}
        }        
    });              
});

$(function(){
    $.contextMenu({
        selector: '.opcementerio', 
        callback: function(key, options) {        
            switch(key){            
            case "view": 
            	$("#dialog").attr("name","cementerio2");
            	viewop("cementerio1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Cementerio oponente",
            		close: function( event, ui ) {},
            		open:function(){}
            		});
            	break;
            }
        },
        items: {                       
            "view": {name: "Ver cementerio oponente"}            
        }
    });              
});
$(function(){
    $.contextMenu({
        selector: '.opdestierro', 
        callback: function(key, options) {        
            switch(key){            
            case "view":      
            	$("#dialog").attr("name","destierro2");
            	viewop("destierro1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Destierro oponente",
            		close: function( event, ui ) {},
            		open:function(){}
            		});
            	break;
            }
        },
        items: {                       
            "view": {name: "Ver destierro oponente"}            
        }
    });              
});
$(function(){
    $.contextMenu({
        selector: '.opremocion', 
        callback: function(key, options) {        
            switch(key){            
            case "view": 
            	$("#dialog").attr("name","remocion2");
            	viewop("remocion1",$('#hidden').val());
            	$( "#dialog" ).dialog({ 
            		width: 500,
            		title: "Remoción oponente",
            		close: function( event, ui ) {},
            		open:function(){}
            		});
            	break;
            }
        },
        items: {                       
            "view": {name: "Ver cartas removidas"}            
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '.oponentcard', 
        callback: function(key, options) {        
            switch(key){            
            case "control":
            	var card=$("#"+options.$trigger.context.id);
            	takecontrol(options.$trigger.context.id);            	
            	break;
            case "clean":
            	var card=$("#"+options.$trigger.context.id);
            	if(document.getElementById(card.attr("id")+"div")!=null){
            		card.attr("class",card.attr("class")+" forclear");
            	
            		var dataAux;    			    			
            		if(card.attr("id").indexOf("op")!=-1){
            			dataAux=card.attr("id").substring(2);
            		}else{
            			dataAux="op"+card.attr("id");
            		}
            		msgTargetClean(dataAux);
            	}
            	break;
            }
        },
        items: {                       
            "control": {name: "Tomar control"},
            "clean": {name: "Limpiar objetivos"}
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '#mano1', 
        callback: function(key, options) {        
            switch(key){            
            case "cartaazar":
            		var length=document.getElementById("mano1").childNodes.length;
            		randomCard(length);
            	break;
            case "show":
        		show("mano1", "Mostrando mano");
        	break;
            }
        },
        items: {                       
            "cartaazar": {name: "Seleccionar carta al azar"},
            "show": {name: "Mostrar mano al oponente"}
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '#oropagado1', 
        callback: function(key, options) {        
            switch(key){            
            case "reagrupar":            	
            		reagrupar("oropagado1", "reserva1");
            	break;            
            }
        },
        items: {                       
            "reagrupar": {name: "Reagrupar oros"}
        }
    });              
});

$(function(){
    $.contextMenu({
        selector: '#ataque1', 
        callback: function(key, options) {        
            switch(key){            
            case "reagrupar":            	
            		reagrupar("ataque1", "defensa1");
            	break;            
            }
        },
        items: {                       
            "reagrupar": {name: "Reagrupar aliados"}
        }
    });              
});

