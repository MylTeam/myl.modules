<?xml version="1.0" encoding="UTF-8" ?>
    <jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Error</title>
<style type="text/css">
	#errorDetails
	{
		min-width:350px;
		max-width:900px;
		margin-left:auto;
		margin-right:auto;
		display:none;
		
	}
	
	#errorContainer
	{
		text-align:center;
		width:900px;
		min-width:350px;
		margin-left:auto;
		margin-right:auto;
		-webkit-border-radius: 10px;
		border-radius: 10px;
		background-color: #ECEDEF;
		margin:10px;
	}
	.btnErr
	{
		witdh:200px;
		max-width:200px;
	}
	
	.syntaxhighlighter
	{
		max-height:200px;
	}
	
	#transIndicator, #reportSuccess, #reportFailure
	{
		display:none;
	}
	
	.titulo
	{
	    background-color: #E4E6E9;
	    border-radius: 10px 10px 0 0;
	    color: #0B45A9;
	    font-size: 16pt;
	    font-weight: bold;
	    margin-bottom: 10px;
	    padding: 5px;
	    text-align: center;
	}
	
	.margin
	{
		padding:10px;
	}
	a.colorBlue {
    color: blue !important;
    text-decoration: none;
    border-bottom: 1px solid green;
}
	
</style>
<jsp:text>
	<![CDATA[
		<!--	<script src="${pageContext.request.contextPath}/scripts/control-mensajes-ajax.js" type="text/javascript"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/sh/scripts/shCore.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/sh/scripts/shBrushJScript.js"></script>
			<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/scripts/sh/styles/shCoreDefault.css"/>-->
		 ]]>
</jsp:text>

	
<script type="text/javascript">
	
	$(document).ready(function() {
		reportarError();
	});
	
	function reenviaReporteError()
	{
		$('#reportFailure').fadeOut(1500,function()
		{
			$('#sendingReport').fadeIn(1500);
		});
		reportarError();
	}
	
	function muestraError()
	{
		$("#errorContainer").animate
		({
			height: "550px"
			}, 1500
		);
		$(".waitImg").animate
		({
			width: "32px",
			height: "32px",
			opacity: 0.4,
			},
			1500,
			function()
			{
				$('#errorDetails').fadeIn("slow");
			}
		);
	}
	
	function ocultaError()
	{
		$('#errorDetails').fadeOut(1500,function()
		{
			$("#errorContainer").animate
			({
				height: "320px"
				}, 1500
			);
			$(".waitImg").animate
			({
				width: "128px",
				height: "128px",
				opacity: 1.0,
				}, 1500
			);
		});
	}
	
	function goBack()
	{
		window.history.back();
	}
	
	function reportarError()
	{
	
		var stacktrace = $('#exStack').html();
		var exception =$('#exName').html();
		var url = document.URL;
		var mensajes = new Array();
		//alert("${pageContext.request.contextPath}/reporte!generaReporte")
		$.ajax
		({
			type: 'POST',
			url : '${pageContext.request.contextPath}/reporte!generaReporte',
			data: "url="+url+"&amp;stackTrace="+stacktrace+"&amp;exceptionName="+exception,
			success : function ()
			{
				$('#sendingReport').fadeOut(1500,function()
				{
					$('#reportSuccess').fadeIn(1500);
				});
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				$('#sendingReport').fadeOut(1500,function()
				{
					$('#reportFailure').fadeIn(1500);
				});
			}
		});
	}
	
	SyntaxHighlighter.all();
</script>
</head>
<body>
	
	<div id="exStack" style="display:none;">
	<s:property value="exceptionStack" />
	</div>
	<div id="exName" style="display:none;">
	<s:property value="exception" />
	</div>
	
		<h1 class="title">Se ha producido un error en el sistema</h1>
		<div class="form">
			<div class="title">
			Error en la aplicación
			</div>
			<div id="sendingReport">
				<img class="waitImg" src="${pageContext.request.contextPath}/images/wait.gif" width="128" height="128" alt="" border="0" />
				<p>Espere, estamos recolectando algunos datos para generar un reporte automático. Este nos ayudará encontrar una solución al problema. <br/> Para ver más detalles del error de clic <a href="#" onclick="muestraError()" class="colorBlue">aquí</a>.</p>
			</div>
			<div id="reportSuccess">
				<img class="waitImg" src="${pageContext.request.contextPath}/images/mail.png" width="128" height="128" alt="" border="0" />
				<p>Se ha enviado exitosamente el reporte automático, en breve atenderemos este error para darle una solución. <br/> Para ver más detalles del error de clic <a href="#" onclick="muestraError()" class="colorBlue">aquí</a> 
				
				<!-- o para regresar a la pantalla anterior de clic <a href="#" onclick="goBack()" class="colorBlue">aquí</a>-->.</p>
			</div>
			<div id="reportFailure">
				<img class="waitImg" src="${pageContext.request.contextPath}/images/x.png" width="128" height="128" alt="" border="0" />
				<p>No se pudo enviar el reporte, de clic <a href="#" onclick="reenviaReporteError()" class="colorBlue">aqui</a> para intentar de nuevo o contacte al administrador. <br/> Para ver más detalles del error de clic <a href="#" onclick="muestraError()">aquí</a>.</p>
			</div>
			<div id="errorDetails">
			   	<p align="left"><b>Exception Name: <s:property value="exception" /></b></p>
			   	<hr/>
			   	<pre class="brush: js; toolbar: false;">
					<s:property value="exceptionStack" />
				</pre>
				<p align="right"><a href="#" onclick="ocultaError()" class="colorBlue">Ocultar detalles</a></p>
			</div>
		</div>
	
	
</body>
</html>
</jsp:root>