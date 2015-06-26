function limpiarMensajesAjax(idContenedor) {
	var mensajes = $('#' + idContenedor);
	mensajes.html('');
	$('<span>').appendTo(mensajes);
}

function mostrarAjaxOperationMessages(mensajes, idContenedor) {
	if (mensajes.length > 0) {
		$("#" + idContenedor).html('');
		var numMensajes = mensajes.length;
		for ( var i = 0; i < numMensajes; i++) {
			var divMensaje = $('<div>');
			divMensaje.addClass('msg');
			divMensaje.text(mensajes[i]);
			divMensaje.appendTo($('#' + idContenedor))
		}
	}
}

function mostrarAjaxInformationMessages(mensajes, idContenedor) {
	if (mensajes.length > 0) {
		var numMensajes = mensajes.length;
		for ( var i = 0; i < numMensajes; i++) {
			var divMensaje = $('<div>');
			divMensaje.addClass('info');
			divMensaje.text(mensajes[i]);
			divMensaje.appendTo($('#' + idContenedor))
		}
	}
}

function mostrarAjaxErrorMessages(mensajes, idContenedor) {
	if (mensajes.length > 0) {
		$('#' + idContenedor).html('');
		var divContenedor = $('<div>');
		divContenedor.addClass("iu-widget");
		divContenedor.addClass("actionError");
		var divMensajes = $('<div>');
		divMensajes.addClass("ui-state-error");
		divMensajes.addClass("ui-corner-all");
		divMensajes.addClass("notificacion");
		divMensajes.addClass("ui-error");
		divMensajes.css("padding", "0.3em 0.7 em");
		divMensajes.css("margin-top", "20px");
		divMensajes.appendTo(divContenedor);
		var numMensajes = mensajes.length;
		for ( var i = 0; i < numMensajes; i++) {
			var p = $("<p>");
			var spanStyle = $("<span>");
			spanStyle.addClass("ui-icon");
			spanStyle.addClass("ui-icon-alert");
			spanStyle.css("float", "left");
			spanStyle.css("margin-right", "0.3em");
			var spanMensaje = $("<span>");
			spanMensaje.text(mensajes[i]);
			spanStyle.appendTo(p);
			spanMensaje.appendTo(p);
			p.appendTo(divMensajes);
		}
		divContenedor.appendTo($('#' + idContenedor));
	}
}

function mostrarAjaxAlertMessajes(mensajes, idContenedor) {
	if (mensajes.length > 0) {
		$('#' + idContenedor).html('');
		var divContenedor = $('<div>');
		divContenedor.addClass("iu-widget");
		divContenedor.addClass("actionMessage");
		var divMensajes = $('<div>');
		divMensajes.addClass("ui-state-highlight");
		divMensajes.addClass("ui-corner-all");
		divMensajes.addClass("notificacion");
		divMensajes.addClass("ui-advertencia");
		divMensajes.css("padding", "0.3em 0.7 em");
		divMensajes.css("margin-top", "20px");
		divMensajes.appendTo(divContenedor);
		var numMensajes = mensajes.length;
		for ( var i = 0; i < numMensajes; i++) {
			var p = $("<p>");
			var spanStyle = $("<span>");
			spanStyle.addClass("ui-icon");
			spanStyle.addClass("ui-icon-info");
			spanStyle.css("float", "left");
			spanStyle.css("margin-right", "0.3em");
			var spanMensaje = $("<span>");
			spanMensaje.text(mensajes[i]);
			spanStyle.appendTo(p);
			spanMensaje.appendTo(p);
			p.appendTo(divMensajes);
		}
		divContenedor.appendTo($('#' + idContenedor));
	}
}

function mostrarAjaxAlertMessajesAux(mensajes, idContenedor) {
	if (mensajes.length > 0) {
		$('#' + idContenedor).html('');
		var divContenedor = $('<div>');
		divContenedor.addClass("iu-widget");
		divContenedor.addClass("actionMessage");
		var divMensajes = $('<div>');
		divMensajes.addClass("ui-state-highlight");
		divMensajes.addClass("ui-corner-all");
		divMensajes.addClass("notificacion");
		divMensajes.addClass("ui-advertencia");
		divMensajes.css("padding", "0.3em 0.7 em");
		divMensajes.css("margin-top", "20px");
		divMensajes.appendTo(divContenedor);
		var numMensajes = mensajes.length;
		for ( var i = 0; i < numMensajes; i++) {
			var p = $("<p>");
			var spanStyle = $("<span>");
			spanStyle.addClass("ui-icon");
			spanStyle.addClass("ui-icon-info");
			spanStyle.css("float", "left");
			spanStyle.css("margin-right", "0.3em");
			var spanMensaje = $("<span>");
			spanMensaje.html(mensajes[i]);
			spanStyle.appendTo(p);
			spanMensaje.appendTo(p);
			p.appendTo(divMensajes);
		}
		divContenedor.appendTo($('#' + idContenedor));
	}
}