function phaseChange(ev){
	disablePhases();
	ev.target.className="faseActive";
	change(ev.target.id);
}

function change(id){
	switch(id){
	case "fagrup":
		agrupar();
	break;
	case "fvigil":
		vigilia();
	break;
	case "fataqu":
		ataque();
		break;
	case "fbloqu":
		bloqueo();
		break;
	case "ftalis":
		guerra();
		break;
	case "fdanio":
		danio();
		break;
	case "ffinal":
		fin();
		break;
	case "fturno":
		turno();
		break;
}
}

function agrupar(){
		reagrupar("oropagado1", "reserva1");
		reagrupar("ataque1", "defensa1");
		msgPhase("Fase de agrupación", "fagrup");
}

function vigilia(){
	msgPhase("Fase de vigilia", "fvigil");
}

function ataque(){
	msgPhase("Declaración de ataque", "fataqu");
}

function bloqueo(){
	msgPhase("Declaración de bloqueo", "fbloqu");
}

function guerra(){
	msgPhase("Guerra de talismánes", "ftalis");
}

function danio(){
	msgPhase("Asignación de daño", "fdanio");
}

function fin(){
	msgPhase("Fase final", "ffinal");
}

function turno(){
	msgPhase("Tu turno!!", "fturno");
}

function disablePhases(){
	$(".faseActive").attr("class","fase");
}