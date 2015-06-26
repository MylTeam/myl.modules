$(document).ready(function() {
    $('#tblUsers').dataTable({    	
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
 $("#tblUsers").css("border-spacing","0");
 $("#tblUsers_filter").removeClass("dataTables_filter");
 
});