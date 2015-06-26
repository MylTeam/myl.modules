$(document).ready(function() {
    $('#tblWon').dataTable({    	
        "bJQueryUI" : true,
        "bPaginate" : true,
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
 $("#tblWon").css("border-spacing","0");
 $("#tblWon_filter").removeClass("dataTables_filter");
 
 
 $('#tblLost').dataTable({    	
     "bJQueryUI" : true,
     "bPaginate" : true,
     "bInfo":false,
     
//     "sPaginationType" : "full_numbers",
//     "sDom" : '<"H"Tfr>t<"F"ip>',
//     "oTableTools" : {
//         "aButtons" : [ "copy", "csv", "xls", "pdf", {
//             "sExtends" : "collection",
//             "sButtonText" : "Save",
//             "aButtons" : [ "csv", "xls", "pdf" ]
//         } ]
//     }
 });
$("#tblLost").css("border-spacing","0");
$("#tblLost_filter").removeClass("dataTables_filter");
});