$(window).load(function() {	
        $('#joyRideTipContent').joyride({
          autoStart : true,
          postStepCallback : function (index, tip) {
          if (index == 10) {
            $(this).joyride('set_li', false, 1);
          }
        },
        modal:false,
        expose: true
        });
      });

function showHelp(){	
	$('#dialog-help').dialog({ 
		width: "80%",
		height: "600",
		resizable: true,
		title: "Ayuda y Reglas"	
	});
}

