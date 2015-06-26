function takecontrol(id){
	var context=$("#hidden").val();
	var imgObject=$("#"+id);
	var parent=imgObject.parent().attr("id");	
	
	if(parent=="dialog"){
		parent=$("#dialog").attr("name");
	}
		
	var newid=id.substring(2);
	newid="tc"+newid;
	if(id.indexOf("optc")>=0){
		newid=id.substring(4);	
	}
	
	var newparent=parent.replace("2","1");	

	for(var c=0;c<objOp[newparent].length;c++){
		if(objOp[newparent][c].idTemp==id){
			
			objOp[newparent][c].idTemp=newid;			
			obj["defensa1"].unshift(objOp[newparent].splice(c,1)[0]);
			var img=createCard(0, context, "defensa1");			
			$("#defensa1").append($(img));
			$("#"+id).remove();
			
			if(newparent=="cementerio1" || newparent=="destierro1" || newparent=="remocion1"){				
				var d = document.getElementById(parent);
				if (objOp[newparent].length != 0) {
					d.src = context + "/images/myl/"+objOp[newparent][0].siglas+"/" + objOp[newparent][0].numero+ ".jpg";
				} else {
					d.src = context + "/images/myl/" + newparent + ".jpg";
				}
			}else if(newparent=="mano1"){
				var divMano=document.getElementById(parent);
				var n=divMano.childNodes.length-1;
				$("#card"+n).remove();
			}
		
			var from=document.getElementById("userName").value;
		    var to=document.getElementById("user2").value;    
		    var msg="Tomando control de carta";  
		    movedCard["origen"]=parent;
		    movedCard["destino"]="defensa1";
		    wsclient.toChatCard(from, to, msg, obj["defensa1"][0], movedCard["origen"], movedCard["destino"]);
		}				
	}
}