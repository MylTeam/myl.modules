$(document).ready(function() {
    $('#tblDeck').dataTable({    	
        "bJQueryUI" : true,
        "bPaginate" : false,
        "bInfo":false,
        
//        "sPaginationType" : "full_numbers",
//        "sDom" : '<"H"Tfr>t<"F"ip>',
//        "oTableTools" : {
//            "aButtons" : [ "copy", "csv", "xls", "pdf", {
//                "sExtends" : "collection",
//                "sButtonText" : "Save",
//                "aButtons" : [ "csv", "xls", "pdf" ]
//            } ]
//        }
    });
 $("#tblDeck").css("border-spacing","0");
 
 var divH=$(document.getElementById("tblDeck_filter").parentNode);
 divH.append($("#btnNew"));
 
});