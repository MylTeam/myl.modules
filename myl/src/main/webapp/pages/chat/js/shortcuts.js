Mousetrap.bind('shift+s', function() { shuffle(); $('input[type=text], textarea').blur();});
Mousetrap.bind('shift+d', function() { drawCard(); $('input[type=text], textarea').blur();});
Mousetrap.bind('shift+b', function() { dropxcards(1); $('input[type=text], textarea').blur();});

Mousetrap.bind('shift+space', 
		function(){
			
			var id="fagrup";
			switch($(".faseActive:first").attr("id")){
			
			case "fagrup":
				id="fvigil";
			break;
			case "fvigil":
				id="fataqu";
			break;
			case "fataqu":
				id="fbloqu";
				break;
			case "fbloqu":
				id="ftalis";
				break;
			case "ftalis":
				id="fdanio";
				break;
			case "fdanio":
				id="ffinal";
				break;
			case "ffinal":
				id="fturno";
				break;
			case "fturno":
				id="fagrup";
				break;
			}
			
			disablePhases();
			
			$("#"+id).attr("class","faseActive");
			change(id); 
			$('input[type=text], textarea').blur();}
		);